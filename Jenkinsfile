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
        stage('Start test container and make a curl test') {        
            steps {
                sh "docker run -d -p 8080:8080 --name product_FirstPipeline ${TESTIMAGE}:${TESTTAG}"
                echo "Wait fo test container start up"
                sh "sleep 2m"
                sh "curl -v --fail http://localhost:8080/product/api/product"
                script {
                    docker.rm
                }
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
                script {
                    def image = docker.build("${DEPLOYIMAGE}:${DEPLOYTAG}", "-f Dockerfile.deploy .")
                    image.push()
                }
                sh "/home/di_sun/kube_projects/exp/scripts/applydeployment.sh /home/di_sun/kube_projects/exp/deployments/exp-product-v1.tmpl"
                sh "curl -v --fail https://test.splitthebill.ml"
            }
        }
    }
}