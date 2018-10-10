#!/bin/sh
JAR="/home/admin/jenkins-cli.jar"
KEY="/home/admin/jenkins-cli"

URL="http://localhost:{{ config.jenkins.port }}/jenkins/"

if [ -f "/var/lib/jenkins/secrets/initialAdminPassword" ]; then
  PASSWORD="$(cat /var/lib/jenkins/secrets/initialAdminPassword)"
else
  PASSWORD="{{ config.jenkins.password }}"
fi

java -jar "$JAR" -s "$URL" -http -auth admin:"$PASSWORD" "$@"
