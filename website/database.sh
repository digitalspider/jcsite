#!/bin/bash

TOMCATDIR=/opt/tomcat/tomcat-stage

sudo rm $TOMCATDIR/database.db
sudo sqlite3 $TOMCATDIR/database.db -init src/main/resources/students.sql
