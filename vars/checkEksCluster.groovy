def call(String credentialsId, String clusterName, String reginCode) {
    withCredentials([aws(accessKeyVariable: 'AWS_ACCESS_KEY_ID', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY', credentialsId: credentialsId)]) {
        // Check if the EKS cluster exists
        sh """
            aws eks update-kubeconfig --name ${clusterName} --region ${reginCode}
            kubectl get svc
            kubectl apply -f namespace.yaml

            echo "Applying pv.yaml..."
            kubectl apply -f pv.yaml
            """
    }
}

