pipeline {
    agent any
    environment {
        TESTIMAGE = "exp-product"
        TESTTAG = "$BUILD_NUMBER"
        DEPLOYIMAGE = "asia.gcr.io/arch-project-176305/exp-product"
        DEPLOYTAG = "0.0.1.1-alpha"
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
                script {
                    docker.build("${TESTIMAGE}:${TESTTAG}", "-f Dockerfile.test .")
                }
            }
        }
        stage('Test container local test') {        
            steps {
                sh "docker run -d -p 8080:8080 --name product_FirstPipeline ${TESTIMAGE}:${TESTTAG}"
                echo "Wait fo test container starting up"
                sh "sleep 3m"
                sh "curl -v --fail http://localhost:8080/product/api/product"
            }
            post {
                always {
                    sh "docker stop product_FirstPipeline || true"
                    sh "docker rm product_FirstPipeline || true"
                    sh "docker image ls | grep '<none>' | awk '{print \$3}' | xargs docker rmi || true"
                    sh "docker rmi ${TESTIMAGE}:${TESTTAG}"
                }
            }
        }
        stage('Build, deploy and integration test') {
            steps {
                sh "sed --in-place s/test-sample-1/exp-sqlproxy/g src/main/resources/application.properties"
                script {
                    def image = docker.build("${DEPLOYIMAGE}:${DEPLOYTAG}", "-f Dockerfile.deploy .")
                    image.push()
                }
                sh "/home/di_sun/kube_projects/exp/scripts/applydeployment.sh /home/di_sun/kube_projects/exp/deployments/exp-product-v1.tmpl"
                echo "Wait for deployment upgrading"
                sh "sleep 2m"
                sh "kubectl get pods"
                sh "kubectl get svc"
                sh "kubectl get ep"
                sh "curl -v --fail https://test.splitthebill.ml"
            }
        }
    }
}