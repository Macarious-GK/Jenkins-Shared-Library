def call(String credentialsId, String clusterName, String reginCode){
    withCredentials([aws(accessKeyVariable: 'AWS_ACCESS_KEY_ID', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY', credentialsId: credentialsId)]) {

    sh"""
        aws eks update-kubeconfig --name ${clusterName} --region ${reginCode}

        kubectl get svc -n macarious 
        
        echo "Deleting deployment.yaml..."
        kubectl delete -f deployment.yaml -n macarious

        echo "Deleting ingress.yaml..."
        kubectl delete -f ingress.yaml -n macarious

        echo "Deleting service.yaml..."
        kubectl delete -f service.yaml -n macarious

        echo "Deleting job.yaml..."
        kubectl delete -f job.yaml -n macarious

        echo "Deleting pvc.yaml..."
        kubectl delete -f pvc.yaml -n macarious

        echo "Deleting pv.yaml..."
        kubectl delete -f pv.yaml -n macarious

        echo "Deleting namespace.yaml..."
        kubectl delete -f namespace.yaml -n macarious

        echo "Listing remaining services in namespace macarious..."
        kubectl get svc -n macarious
    """
    }
}