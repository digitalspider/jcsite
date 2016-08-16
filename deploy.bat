@echo off

REM mvn -DskipTests package

set TOMCATDIR=\apps\tomcat\apache-tomcat-8.0.35

del \apps\tomcat\apache-tomcat-8.0.35\webapps\website.war
PING 1.1.1.1 -n 2 -w 60000 >NUL
copy /Y target\website.war %TOMCATDIR%\webapps\website.war

REM tail -100f %TOMCATDIR%\logs\catalina.out 
