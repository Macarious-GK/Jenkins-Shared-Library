def call(String credentialsId, String clusterName, String reginCode) {
    withCredentials([aws(accessKeyVariable: 'AWS_ACCESS_KEY_ID', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY', credentialsId: credentialsId)]) {
        // Check if the EKS cluster exists
        sh """
            aws eks update-kubeconfig --name ${clusterName} --region ${reginCode}
            kubectl get svc
            echo "Applying namespace.yaml..."
            kubectl apply -f Namespace.yaml

            echo "Applying pv.yaml..."
            kubectl apply -f PV.yaml

            echo "Applying pvc.yaml..."
            kubectl apply -f PVC.yaml

            echo "Applying job.yaml..."
            kubectl apply -f Job.yaml

            echo "Applying Database.yaml..."
            kubectl apply -f Database.yaml

            echo "Waiting for resources to be created..."
            sleep 70

            echo "Applying Application.yaml..."
            kubectl apply -f Secrets.yaml
            kubectl apply -f Application.yaml

            echo "Waiting for resources to be created..."
            sleep 30
            
            kubectl get svc -n macarious
            """
    }
}
