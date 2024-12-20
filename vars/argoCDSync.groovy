def call(){
    sh """
                        argocd login $ARGOCD_SERVER --token $ARGOCD_AUTH_TOKEN --insecure
                    """
                    
                    // Manually sync the ArgoCD application
                    // Replace 'your-app-name' with the name of your ArgoCD application
                    sh """
                        argocd app sync your-app-name
                    """
                    
                    // Optional: Check the sync status
                    sh """
                        argocd app wait your-app-name --sync
                    """
}