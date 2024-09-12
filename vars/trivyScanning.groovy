def call(Map config = [:]) {
    if (!config.imageName) {
        error "Image name must be provided for Trivy testing."
    }
    echo "Running Trivy Container Test for image: ${config.imageName}"
    sh """
        trivy image --severity HIGH,CRITICAL ${config.imageName} 
        
        # If vulnerabilities are found, print the issues and fail the pipeline
        if [ \$? -ne 1 ]; then
            echo "Trivy found vulnerabilities in the Docker image:"
            trivy image --severity HIGH,CRITICAL ${config.imageName}  --json | jq .
            echo "Failing the pipeline due to Trivy vulnerabilities"
            exit 1
        else
            echo "No vulnerabilities found by Trivy"
        fi
    """
}

