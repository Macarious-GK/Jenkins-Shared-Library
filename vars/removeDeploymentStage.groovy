def call(String credentialsId){
    withCredentials([aws(accessKeyVariable: 'AWS_ACCESS_KEY_ID', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY', credentialsId: credentialsId)]) {

    sh'''
        aws eks update-kubeconfig --name ${clusterName} --region ${reginCode}

        namespace="macarious"
        kubectl get svc -n $namespace 
        
        echo "Deleting deployment.yaml..."
        kubectl delete -f deployment.yaml -n $namespace

        echo "Deleting ingress.yaml..."
        kubectl delete -f ingress.yaml -n $namespace

        echo "Deleting service.yaml..."
        kubectl delete -f service.yaml -n $namespace

        echo "Deleting job.yaml..."
        kubectl delete -f job.yaml -n $namespace

        echo "Deleting pvc.yaml..."
        kubectl delete -f pvc.yaml -n $namespace

        echo "Deleting pv.yaml..."
        kubectl delete -f pv.yaml -n $namespace

        echo "Deleting namespace.yaml..."
        kubectl delete -f namespace.yaml -n $namespace

        echo "Listing remaining services in namespace $namespace..."
        kubectl get svc -n $namespace
    '''
    }
}