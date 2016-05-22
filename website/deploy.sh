#!/bin/bash

TOMCATDIR=/opt/tomcat/tomcat-stage

sudo rm -rf $TOMCATDIR/webapps/website*
sleep 2
sudo cp target/website.war $TOMCATDIR/webapps/
sudo chown -R spider: $TOMCATDIR/webapps/website*
tail -100f $TOMCATDIR/logs/catalina.out 
