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
                    h1 { color: ${color}; font-family: Arial, sans-serif; }
                    p { font-size: 14px; color: black; }
                    .details { color: ${color}; font-weight: bold; }
                </style>
            </head>
            <body>
                <h1>${config.isSuccess ? '✅ Build Successful!' : '❌ Build Failed!'}</h1>
                <p>The build <span class="details">${env.JOB_NAME ?: 'Unknown Job'} #${env.BUILD_NUMBER ?: 'N/A'}</span>
                ${config.isSuccess ? 'completed successfully' : 'failed'}.</p>
                <p>Please <a href="${env.BUILD_URL ?: '#'}">check the logs</a> for more details.</p>
            </body>
        </html>
    """

    // Send the email
    mail to: config.to,
         subject: subject,
         body: body,
         mimeType: 'text/html'
}
