#!/bin/bash

cd src/
javac *.java
java ElectionMonitor
rm *.class
