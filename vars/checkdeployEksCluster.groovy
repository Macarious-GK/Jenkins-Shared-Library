def call(String credentialsId, String clusterName, String regionCode) {
    withCredentials([aws(accessKeyVariable: 'AWS_ACCESS_KEY_ID', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY', credentialsId: credentialsId)]) {
        // Check if the EKS cluster exists
        sh """
            aws eks update-kubeconfig --name ${clusterName} --region ${regionCode}
        """

        // Check if the deployment already exists
        def deploymentExists = sh(script: "kubectl get deployments -n macarious | grep deployment-name || true", returnStatus: true) == 0

        if (deploymentExists) {
            error "Deployment already exists. Skipping deployment."
        } else {
            echo "Deployment does not exist. Applying configuration files..."

            sh """
                echo "Applying namespace.yaml..."
                kubectl apply -f namespace.yaml
                echo "Applying pv.yaml..."
                kubectl apply -f pv.yaml
                echo "Applying pvc.yaml..."
                kubectl apply -f pvc.yaml
                echo "Applying job.yaml..."
                kubectl apply -f job.yaml
                echo "Applying service.yaml..."
                kubectl apply -f service.yaml
                echo "Applying ingress.yaml..."
                kubectl apply -f ingress.yaml
                echo "Applying deployment.yaml..."
                kubectl apply -f deployment.yaml
            """
        }

        // Display the services in the namespace
        sh "kubectl get svc -n macarious"
    }
}