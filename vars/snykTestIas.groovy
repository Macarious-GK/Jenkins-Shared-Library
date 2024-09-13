def call() {
    echo "Running Snyk IaC test for Terraform files"

    // Run Snyk IaC test and capture the output
    def scanOutput = sh(script: "snyk iac test --severity-threshold=high --json", returnStdout: true).trim()

    // Check if there are any HIGH or CRITICAL severity issues
    def hasHighSeverity = scanOutput.contains("\"severity\": \"high\"") || scanOutput.contains("\"severity\": \"critical\"")

    // Fail the pipeline if HIGH or CRITICAL severity vulnerabilities are found
    if (hasHighSeverity) {
        echo "Snyk found HIGH or CRITICAL severity issues in the Terraform files:"
        echo "${scanOutput}"
        error "Failing the pipeline due to HIGH or CRITICAL severity issues."
    } else {
        echo "No HIGH or CRITICAL severity issues found by Snyk."
    }
}
