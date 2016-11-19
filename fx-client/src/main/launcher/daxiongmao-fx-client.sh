#!/bin/bash
#
# Simple script to execute the JavaFX application using Java7. 
#
# (i) due to the JavaFX dialogs you cannot run that application with Java8.40+ 
#     ==> You must switch to the official JFX Dialogs and recompile
#
echo "Launching application with Java8" 
/usr/lib/jvm/java-8-oracle/bin/java -jar fx-client.jar
