def call(String imageName) {
    if (!imageName) {
        error "Image name must be provided"
    }

    // Push the Docker image to Docker Hub
    echo "Pushing Docker image to Docker Hub: ${imageName}"
    sh "docker push ${imageName}"
}
