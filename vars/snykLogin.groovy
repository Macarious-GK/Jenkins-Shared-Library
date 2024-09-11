def call(String credentialsId) {
    if (!credentialsId) {
        error "Credentials ID must be provided for Snyk authentication."
    }

    withCredentials([string(credentialsId: credentialsId, variable: 'SNYK_TOKEN')]) {
        echo "Authenticating with Snyk using the provided token..."

        sh '''
        snyk auth $SNYK_TOKEN
        '''

        echo "Snyk authentication successful."
    }
}
