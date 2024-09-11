def call(Map config = [:]) {
    if (!imageName) {
        error "Image name must be provided for Snyk testing."
    }
    echo "Running Snyk Container Test for image: ${config.image}"
    sh '''
        snyk container test ${config.image} --file=Dockerfile
                        
        # If vulnerabilities are found, print the issues and fail the pipeline
        if [ $? -ne 0 ]; then
            echo "Snyk found vulnerabilities in the Docker image:"
            snyk container test ${config.image} --file=Dockerfile --json | jq .
            echo "Failing the pipeline due to Snyk vulnerabilities"
            exit 1
        else
            echo "No vulnerabilities found by Snyk"
        fi
        '''
    
}
