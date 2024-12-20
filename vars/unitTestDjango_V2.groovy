def call(){
    sh '''
        echo "Starting unit tests..."

        # Activate virtual environment using bash
        bash -c "source venv/bin/activate &&  python3 manage.py test && deactivate"
        result=$?

        if [ $result -eq 0 ]; then
            echo "Unit tests passed successfully."
        else
            echo "Unit tests failed. Please check the test results."
        fi      
        '''
}