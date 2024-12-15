def call() {
    sh '''
        echo "Checking if virtual environment exists..."

        # Check if the virtual environment directory exists
        if [ -d "venv" ]; then
            echo "Virtual environment already exists, activating..."
        else
            echo "Creating virtual environment..."
            python3 -m venv venv
        fi

        # Activate virtual environment and install dependencies
        bash -c "source venv/bin/activate && pip install -r requirements.txt && deactivate"

        result=$?

        if [ $result -eq 0 ]; then
            echo "Installing dependencies successfully."
        else
            echo "Installing dependencies failed."
            exit 1
        fi
    '''
}
