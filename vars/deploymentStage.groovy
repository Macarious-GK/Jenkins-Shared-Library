def call(String credentialsId,){
    withCredentials([aws(accessKeyVariable: 'AWS_ACCESS_KEY_ID', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY', credentialsId: credentialsId)]) {

    sh'''
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
    '''
    }
}