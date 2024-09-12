def call(Map config = [:]) {
    if (!config.imageName) {
        error "Image name must be provided for Trivy testing."
    }
    echo "Running Trivy Container Test for image: ${config.imageName}"
    sh """
        trivy image ${config.imageName} 
        
        # If vulnerabilities are found, print the issues and fail the pipeline
        if [ \$? -ne 0 ]; then
            echo "Trivy found vulnerabilities in the Docker image:"
            trivy image ${config.imageName}  --json | jq .
            echo "Failing the pipeline due to Trivy vulnerabilities"
            exit 1
        else
            echo "No vulnerabilities found by Trivy"
        fi
    """
}


def call(Map config = [:]) {
    if (!config.imageName) {
        error "Image name must be provided for Trivy testing."
    }
    
    echo "Running Trivy Container Test for image: ${config.imageName}"
    
    // Run Trivy scan and store results in JSON format
    def trivyResults = sh(script: "trivy image ${config.imageName} --json", returnStdout: true).trim()

    // Print raw JSON results for debugging
    echo "Trivy scan results: ${trivyResults}"
    
    // Check if vulnerabilities with high severity exist
    def hasHighSeverityVulns = sh(script: """
        echo '${trivyResults}' | jq '.[] | select(.Severity == "HIGH")' | grep -q . && echo "true" || echo "false"
    """, returnStdout: true).trim()

    if (hasHighSeverityVulns == "true") {
        echo "Trivy found vulnerabilities with HIGH severity in the Docker image:"
        echo "${trivyResults}" | jq '.[] | select(.Severity == "HIGH")'
        echo "Failing the pipeline due to Trivy vulnerabilities with HIGH severity"
        error "Pipeline failed due to vulnerabilities with HIGH severity"
    } else {
        echo "No high severity vulnerabilities found by Trivy"
    }
}
