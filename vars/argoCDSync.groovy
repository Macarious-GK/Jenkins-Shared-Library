def call(Map config = [:]) {
    withCredentials([
        usernamePassword(credentialsId: config.argocd_creds, usernameVariable: 'ARGOCD_USERNAME', passwordVariable: 'ARGOCD_PASSWORD')
    ]) {
        sh """
            argocd login ${config.server} --username ${ARGOCD_USERNAME} --password ${ARGOCD_PASSWORD} --insecure
            argocd app sync ${config.appName} --wait
        """
    }
}
