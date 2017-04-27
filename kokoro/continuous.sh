#!/bin/bash

# Fail on any error.
set -e
# Display commands to stderr.
set -x

/escalated_sign/escalated_sign.py

cd github/appengine-plugins-core
sudo /opt/google-cloud-sdk/bin/gcloud components update
sudo /opt/google-cloud-sdk/bin/gcloud components install app-engine-java
mvn clean install cobertura:cobertura -B -U
# bash <(curl -s https://codecov.io/bash)
