def call() {
    sh '''
        echo "Running Bandit for security checks..."
        bandit -r . -f json -o bandit_Report.json

        # Echo the Bandit report for visibility in Jenkins log
        echo "Bandit security report:"
        cat bandit_Report.json

        # Check if Bandit found any issues
        // if grep '"issue_severity":' bandit_Report.json > /dev/null; then
        //     echo "Failed from SAST stage: Security issues detected by Bandit."

        // else
        //     echo "SAST stage passed successfully: No issues found by Bandit."
        // fi
    '''
}
