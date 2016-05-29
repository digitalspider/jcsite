#!/bin/bash

mvn package

TOMCATDIR=/opt/tomcat/tomcat-stage

rm -rf $TOMCATDIR/webapps/website*
sleep 2
cp target/website.war $TOMCATDIR/webapps/
tail -100f $TOMCATDIR/logs/catalina.out 
