def call(String imageName) {
    if (!imageName) {
        error "Image name must be provided"
    }
    // Build the Docker image
    echo "Building Docker image..."
    sh 'docker build -t macarious25siv/books:V4 .'

    // Check the result of the build and handle errors
    def result = sh(script: 'docker images | grep macarious25siv/books', returnStatus: true)
    if (result == 0) {
        echo "Docker image built successfully."
    } else {
        error("Docker image build failed.")
    }


}