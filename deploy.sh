#!/bin/bash

mvn package

#TOMCATDIR=/opt/tomcat/tomcat-stage
TOMCATDIR=/c/apps/tomcat/apache-tomcat-8.0.35

rm -rf $TOMCATDIR/webapps/website*
sleep 2
cp target/website.war $TOMCATDIR/webapps/
tail -100f $TOMCATDIR/logs/catalina.out
