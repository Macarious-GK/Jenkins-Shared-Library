def call(Map config = [:]) {
    // Validate required parameters
    if (!config.to) {
        error "The 'to' parameter is required!"
    }
    if (config.isSuccess == null || !(config.isSuccess instanceof Boolean)) {
        error "The 'isSuccess' parameter must be a boolean (true/false)!"
    }

    // Default email content
    def subject = config.customSubject ?: (
        config.isSuccess 
        ? "✅ Build Successful: ${env.JOB_NAME ?: 'Unknown Job'} #${env.BUILD_NUMBER ?: 'N/A'}"
        : "❌ Build Failed: ${env.JOB_NAME ?: 'Unknown Job'} #${env.BUILD_NUMBER ?: 'N/A'}"
    )

    def color = config.isSuccess ? "green" : "red"
    def body = config.customBody ?: """
        <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    h1 { color: ${color}; text-align: center; }
                    .content { margin: 20px auto; width: 80%; border: 1px solid #ccc; border-radius: 10px; padding: 20px; background-color: #f9f9f9; }
                    .details { font-weight: bold; color: ${color}; }
                    ul { margin: 10px 0; padding-left: 20px; }
                    li { margin-bottom: 5px; }
                    a { color: #007bff; text-decoration: none; font-weight: bold; }
                    a:hover { text-decoration: underline; }
                    footer { text-align: center; margin-top: 20px; font-size: 12px; color: #777; }
                </style>
            </head>
            <body>
                <div class="content">   
                    <h1>${config.isSuccess ? '✅ Build Successful!' : '❌ Build Failed!'}</h1>
                    <p>The build <span class="details">${env.JOB_NAME ?: 'Unknown Job'} #${env.BUILD_NUMBER ?: 'N/A'}</span> ${config.isSuccess ? 'completed successfully 🎉' : 'failed 🚨'}.</p>
                    
                    <p><strong>Details:</strong></p>
                    <ul>
                        <li><strong>Job Name:</strong> ${env.JOB_NAME ?: 'N/A'}</li>
                        <li><strong>Build Number:</strong> ${env.BUILD_NUMBER ?: 'N/A'}</li>
                        <li><strong>Status:</strong> <span class="details">${config.isSuccess ? 'Success' : 'Failure'}</span></li>
                    </ul>
                    
                    <p>For further details, please visit the build logs:</p>
                    <p><a href="${env.BUILD_URL ?: '#'}">View Build Logs</a></p>
                </div>
                
                <footer>
                    This is an automated notification sent by Jenkins CI/CD pipeline.
                </footer>
            </body>
        </html>
    """

    // Send the email
    mail to: config.to,
         subject: subject,
         body: body,
         mimeType: 'text/html'
}
    