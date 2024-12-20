def call(String url, String username, String password, String appName) {
    sh """
        argocd login ${url} --username ${username} --password ${password} --insecure
        argocd app sync ${appName} --wait
    """
}
