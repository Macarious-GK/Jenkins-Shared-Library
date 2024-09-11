def call(String credentialsId, String clusterName) {
    withCredentials([aws(accessKeyVariable: 'AWS_ACCESS_KEY_ID', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY', credentialsId: credentialsId)]) {
        // Check if the EKS cluster exists
        def clusterExists = sh(
            script: """
            aws eks describe-cluster --name ${clusterName} --region us-east-2 > /dev/null 2>&1
            echo $?
            """,
            returnStdout: true
        ).trim()

        if (clusterExists != "0") {
            error("EKS Cluster '${clusterName}' does not exist.")
        } else {
            echo "EKS Cluster '${clusterName}' exists. Proceeding to update kubeconfig..."

            // Update kubeconfig for the existing cluster
            sh """
            aws eks update-kubeconfig --name ${clusterName} --region us-east-2
            """
        }
    }
}
