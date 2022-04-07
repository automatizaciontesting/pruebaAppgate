pipeline{
    agent any
    stages{
        stage("Test"){
            steps{
                git url: 'https://github.com/automatizaciontesting/pruebaAppgate.git'
                bat 'gradle clean test'
            }
        }
    }
}