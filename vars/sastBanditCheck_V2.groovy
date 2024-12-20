def call() {
    sh '''
        echo "Running Bandit for security checks..."
        bandit -r . -f json -o bandit_Report.json || true

        # Echo the Bandit report for visibility in Jenkins log
        echo "Bandit security report:"
        cat bandit_Report.json
        echo "Bandit security report ends here."

        # Check if Bandit found any issues, but don't fail the pipeline
        if grep '"issue_severity":' bandit_Report.json > /dev/null; then
            echo "Security issues detected by Bandit. Please review the report."
        else
            echo "SAST stage passed successfully: No issues found by Bandit."
        fi
    '''
}
