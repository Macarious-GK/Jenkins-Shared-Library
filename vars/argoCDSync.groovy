def call(String server, String usernameCredId, String passwordCredId, String appName) {
    withCredentials([
        usernamePassword(credentialsId: usernameCredId, usernameVariable: 'ARGOCD_USERNAME', passwordVariable: 'ARGOCD_PASSWORD')
    ]) {
        sh """
            argocd login ${server} --username ${ARGOCD_USERNAME} --password ${ARGOCD_PASSWORD} --insecure
            argocd app sync ${appName}
        """
    }
}
