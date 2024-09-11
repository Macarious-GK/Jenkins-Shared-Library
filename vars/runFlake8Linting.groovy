def call(){

    sh 'flake8 --count --statistics --select=E999 --exit-zero > flake8_report.txt || true'
    // Read the report
    def report = readFile('flake8_report.txt')
    echo "Flake8 Report:\n${report}"
    
    // Check if the report contains syntax errors (E999)
    if (report.contains('E999')) {
        error("Flake8 report contains syntax errors (E999)! Linting Stage Failing the pipeline.")
    }else {
    // If no syntax errors, display a message
        echo "No syntax errors found in the Flake8 report."
        echo "Linting Stage Passed SUCCESSFULLY"
    }
}