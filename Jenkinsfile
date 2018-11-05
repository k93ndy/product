pipeline {
    agent any
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
            steps {
                sh "docker stop product_FirstPipeline || true && docker rm product_FirstPipeline || true"
                sh "export TIMESTAMP=`date +%s`"
                sh "docker build ./ -t exp-product-$TIMESTAMP:0.0.1"
                sh "docker run -d --network host --name product_FirstPipeline exp-product-$TIMESTAMP:0.0.1"
                echo "Sleep 2 minutes waiting test container start up"
                sh "sleep 2m"
                sh "curl -v http://localhost:8080/product/api/product"
                sh "docker stop product_FirstPipeline"
                sh "docker rm product_FirstPipeline"
            }
        }
        stage('Build container for integration test') {
            steps {
                echo 'ToDo'
            }
        }
    }
}