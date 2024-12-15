def call(){
    sh '''
        echo "Setting up virtual environment and installing dependencies..."
        python3 -m venv venv

        # Activate virtual environment using bash
        bash -c "source venv/bin/activate && pip install -r requirements.txt && deactivate"
        result=$?

        if [ $result -eq 0 ]; then
            echo "installing dependencies successfully."
        else
            echo "installing dependencies failed."
            exit 1
        fi
        '''
}