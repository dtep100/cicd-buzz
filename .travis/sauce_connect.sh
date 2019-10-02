#!/bin/bash

# Setup and start Sauce Connect for your TravisCI build
# This script requires your .travis.yml to include the following two private env variables:
# SAUCE_USERNAME
# SAUCE_ACCESS_KEY
# Follow the steps at https://saucelabs.com/opensource/travis to set that up.
#
# Curl and run this script as part of your .travis.yml before_script section:
# before_script:
#   - curl https://gist.github.com/santiycr/5139565/raw/sauce_connect_setup.sh | bash

VERSION_STRING="sc-4.5.4"
echo "$OSTYPE"
if [[ "$OSTYPE" == "linux-gnu" ]]; then
        # ...
        OS_STRING="linux32"
        FILE_EXT="tar.gz"
        UNZIP_CMD="tar -xzvf"
else
        # Mac OSX
        OS_STRING="osx"
        FILE_EXT="zip"
        UNZIP_CMD="unzip"
fi


CONNECT_URL="https://saucelabs.com/downloads/$VERSION_STRING-$OS_STRING.$FILE_EXT"
CONNECT_DIR="/tmp/sauce-connect-$RANDOM"
CONNECT_DOWNLOAD="Sauce_Connect.zip"
READY_FILE="connect-ready-$RANDOM"

# Get Connect and start it
mkdir -p $CONNECT_DIR
cd $CONNECT_DIR
curl $CONNECT_URL > $CONNECT_DOWNLOAD
$UNZIP_CMD $CONNECT_DOWNLOAD
rm $CONNECT_DOWNLOAD
pwd
cd $VERSION_STRING-$OS_STRING
pwd
cd bin
pwd
ls -l
./sc -u $SAUCE_USERNAME -k $SAUCE_ACCESS_KEY -x https://eu-central-1.saucelabs.com/rest/v1 --readyfile $READY_FILE --tunnel-identifier $TRAVIS_JOB_NUMBER

# Wait for Connect to be ready before exiting
while [ ! -f $READY_FILE ]; do
  sleep .5
done