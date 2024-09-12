// def call(Map config = [:]) {
//     if (!config.imageName) {
//         error "Image name must be provided for Trivy testing."
//     }
//     echo "Running Trivy Container Test for image: ${config.imageName}"
//     sh """
//         trivy image --severity HIGH,CRITICAL ${config.imageName} 
        
//         # If vulnerabilities are found, print the issues and fail the pipeline
//         if [ \$? -ne 1 ]; then
//             echo "Trivy found vulnerabilities in the Docker image:"
//             trivy image --severity HIGH,CRITICAL ${config.imageName}  --json | jq .
//             echo "Failing the pipeline due to Trivy vulnerabilities"
//             exit 1
//         else
//             echo "No vulnerabilities found by Trivy"
//         fi
//     """
// }

// def call(Map config = [:]) {
//     if (!config.imageName) {
//         error "Image name must be provided for Trivy testing."
//     }
    
//     echo "Running Trivy Container Test for image: ${config.imageName}"
    
//     // Run Trivy scan and capture the output
//     def scanOutput = sh(script: "trivy image --severity HIGH,CRITICAL ${config.imageName} --format json", returnStdout: true).trim()

//     // Parse the JSON output to check for HIGH severity vulnerabilities
//     def vulnerabilities = sh(script: "echo '${scanOutput}' | jq '.[] | select(.Severity == \"HIGH\")'", returnStdout: true).trim()

//     // Check if vulnerabilities were found
//     if (vulnerabilities) {
//         echo "Trivy found HIGH severity vulnerabilities in the Docker image:"
//         echo "${scanOutput}"
//         error "Failing the pipeline due to Trivy vulnerabilities"
//     } else {
//         echo "No HIGH severity vulnerabilities found by Trivy"
//     }
// }


def call(Map config = [:]) {
    if (!config.imageName) {
        error "Image name must be provided for Trivy testing."
    }

    echo "Running Trivy scan for image: ${config.imageName}"

    // Run Trivy scan and check for HIGH severity vulnerabilities
    def scanResult = sh(script: "trivy image --severity HIGH,CRITICAL ${config.imageName} | tee", returnStatus: true)

    // If any HIGH severity vulnerabilities are found, fail the pipeline
    if (scanResult.contains("HIGH: 0")) {
        echo "No HIGH severity vulnerabilities found by Trivy."
        
    } else {
        echo "Trivy found HIGH severity vulnerabilities in the Docker image."
        error "Failing the pipeline due to HIGH severity vulnerabilities."
    }
}
