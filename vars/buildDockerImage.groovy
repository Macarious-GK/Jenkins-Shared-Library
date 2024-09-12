def call(String imageName) {
    if (!imageName) {
        error "Image name must be provided."
    }

    // Build the Docker image
    echo "Building Docker image: ${imageName}..."
    def buildResult = sh(script: "docker build -t ${imageName} .", returnStatus: true)

    // Check if the build was successful
    if (buildResult != 0) {
        error "Docker image build failed."
    }

    // Verify the image was created
    def imageExists = sh(script: "docker images -q ${imageName}", returnStatus: true) == 0
    if (!imageExists) {
        error "Docker image '${imageName}' not found after build."
    }

    echo "Docker image '${imageName}' built and verified successfully."
}
