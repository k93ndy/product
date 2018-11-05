pipeline {
    agent any
    stages {
        stage('Unit test') {
            steps {
                chmod 700 gradlew
                ./gradlew -v
                sed --in-place "s/P35537/127.0.0.1/g" src/main/resources/application.properties
                ./gradlew test
            }
        }
        stage('Build WAR file') {
            steps {
                ./gradlew bootWar
            }
        }
        stage('Build container for local test and make a curl test') {
            steps {
                docker stop product_FirstPipeline || true && docker rm product_FirstPipeline || true
                TIMESTAMP=$(date +%s)
                docker build ./ -t exp-product-$TIMESTAMP:0.0.1
                docker run -d --network host --name product_FirstPipeline exp-product-$TIMESTAMP:0.0.1
                echo "Sleep 2 minutes waiting test container start up"
                sleep 2m
                curl -v http://localhost:8080/product/api/product
                docker stop product_FirstPipeline
                docker rm product_FirstPipeline
            }
        }
        stage('Build container for integration test') {
            steps {
                echo 'ToDo'
                #sed --in-place "s/127.0.0.1/exp-sqlproxy/g"
            }
        }
    }
}