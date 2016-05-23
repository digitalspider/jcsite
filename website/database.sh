#!/bin/bash

DBDIR=/opt/jetty

sudo rm $DBDIR/database.db
sudo sqlite3 $DBDIR/database.db -init src/main/resources/students.sql
