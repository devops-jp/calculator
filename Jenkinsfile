pipeline {
	agent any
	triggers {
		pollSCM('* * * * *')
	}
	stages {
		stage("Compile") {
			steps {
				sh "./gradlew compileJava"
			}
		}
		stage("Unit test") {
			steps {
				sh "./gradlew test"
			}
		}
		stage("Code coverage") {
			steps {
				sh "./gradlew jacocoTestReport"
				publishHTML (target: [
					reportDir: 'build/reports/jacoco/test/html',
					reportFiles: 'index.html',
					reportName: "JaCoCo Report"
				])
				sh "./gradlew jacocoTestCoverageVerification"
			}
		}
		stage("Static code analysis") {
			steps {
				sh "./gradlew checkstyleMain"
				publishHTML (target: [
					reportDir: 'build/reports/checkstyle',
					reportFiles: 'main.html',
					reportName: "Checkstyle Report"
				])
			}
		}
		stage("Package") {
			steps {
				sh "./gradlew build"
			}
		}
		stage("Docker build") {
			steps {
				sh "docker build -t devopsjp/calculator ."
			}
		}
		stage("Docker login") {
			steps {
				withCredentials([[
					$class: 'UsernamePasswordMultiBinding',
					credentialsId: 'docker-hub-credentials',
					usernameVariable: 'USERNAME',
					passwordVariable: 'PASSWORD'
				]]) {
					sh "docker login --username $USERNAME --password $PASSWORD"
				}
			}
		}
		stage("Docker push") {
			steps {
				sh "docker push devopsjp/calculator"
			}
		}
		stage("Deploye to staging") {
			steps {
				sh "docker run -d --rm -p 8765:8081 --name calculator devopsjp/calculator"
			}
		}
		stage("Acceptance test") {
			steps {
				sleep 60
				sh "chmod +x acceptance_test.sh && ./acceptance_test.sh"
			}
		}
	}
	post {
		always {
			slackSend channel: '#builds',
			color: 'good',
			message: "Your buiild completed, please check: ${env.BUILD_URL}"
		}
		failure {
			slackSend channel: '#builds-failure',
			color: 'danger',
			message: "The pipeline ${currentBuild.fullDisplayName} failed."
		}
	}
}
