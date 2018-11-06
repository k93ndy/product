pipeline {
    agent any
    environment {
        IMAGENAME = "exp-product"
    }
    options {
        timeout(time: 15, unit: 'MINUTES') 
    }
    stages {
        stage('Multi stage build') {
            steps {
                //sh "chmod 700 gradlew"
                sh "sed --in-place s/P35537/test-sample-1/g src/main/resources/application.properties"
                sh "docker build ./ -t ${IMAGENAME}-$BUILD_NUMBER"
            }
        }
        stage('Build container for local test and make a curl test') {        
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
        stage('Build container for integration test') {
            steps {
                echo 'ToDo...'
            }
        }
    }
}