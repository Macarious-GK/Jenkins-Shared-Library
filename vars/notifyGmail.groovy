def call(Map config = [:]) {
    // Validate required parameters
    if (!config.to) {
        error "The 'to' parameter is required!"
    }
    if (config.isSuccess == null) {
        error "The 'isSuccess' parameter is required!"
    }

    // Default email content
    def subject = config.isSuccess ?
            config.customSubject ?: "✅ Build Successful: ${env.JOB_NAME} #${env.BUILD_NUMBER}" :
            config.customSubject ?: "❌ Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}"

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
                <p>The build <span class="details">${env.JOB_NAME} #${env.BUILD_NUMBER}</span> ${config.isSuccess ? 'completed successfully' : 'failed'}.</p>
                <p>Please <a href="${env.BUILD_URL}">check the logs</a> for more details.</p>
            </body>
        </html>
    """

    // Send the email
    mail to: config.to,
         subject: subject,
         body: body,
         mimeType: 'text/html'
}