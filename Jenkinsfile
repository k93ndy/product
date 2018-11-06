pipeline {
    agent any
    environment {
        IMAGENAME = "exp-product"
    }
    options {
        timeout(time: 15, unit: 'MINUTES') 
    }
    stages {
        stage('Prepare for building test container') {
            steps {
                sh "chmod 700 gradlew"
                sh "sed --in-place s/P35537/test-sample-1/g src/main/resources/application.properties"
            }
        }
        stage('Test container multi stage build') {
            steps {
                docker.build("${IMAGENAME}-$BUILD_NUMBER", "-f Dockerfile.test .")
                //sh "docker build ./ -t ${IMAGENAME}-$BUILD_NUMBER"
            }
        }
        stage('Start test container and make a curl test') {        
            steps {
                sh "docker run -d -p 8080:8080 --name product_FirstPipeline ${IMAGENAME}-$BUILD_NUMBER"
                echo "Wait fo test container start up"
                sh "sleep 2m"
                sh "curl -v --fail http://localhost:8080/product/api/product"
            }
            post {
                always {
                    sh "docker stop product_FirstPipeline || true"
                    sh "docker rm product_FirstPipeline || true"
                }
            }
        }
        stage('Build container for deploy') {
            steps {
                echo 'ToDo...'
            }
        }
    }
}