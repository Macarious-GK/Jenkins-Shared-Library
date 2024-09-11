def call(){
    sh '''
        echo "Starting unit tests..."
        python3 -m venv venv

        # Activate virtual environment using bash
        bash -c "source venv/bin/activate && pip install -r requirements.txt && python3 manage.py test && deactivate"
        result=$?

        if [ $result -eq 0 ]; then
            echo "Unit tests passed successfully."
        else
            echo "Unit tests failed. Please check the test results."
            exit 1
        fi
        '''
}