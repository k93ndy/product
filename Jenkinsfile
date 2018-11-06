pipeline {
    agent any
    options {
        timeout(time: 10, unit: 'MINUTES') 
    }
    stages {
        stage('Unit test') {
            steps {
                sh "chmod 700 gradlew"
                sh "./gradlew -v"
                sh "sed --in-place s/P35537/127.0.0.1/g src/main/resources/application.properties"
                sh "./gradlew test"
            }
        }
        stage('Build WAR file') {
            steps {
                sh "./gradlew bootWar"
            }
        }
        stage('Build container for local test and make a curl test') {
            environment {
                IMAGENAME = "exp-product"
            }
            steps {
                sh "docker stop product_FirstPipeline || true && docker rm product_FirstPipeline || true"
                sh "docker build ./ -t exp-product-$BUILD_NUMBER"
                sh "docker run -d --network host --name product_FirstPipeline exp-product-$BUILD_NUMBER"
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