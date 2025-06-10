#!/bin/bash

JAVAFX_LIB_PATH=./lib/javafx-sdk-21.0.7/lib
JAR_FILE=MOHANO.jar

java --module-path "$JAVAFX_LIB_PATH" --add-modules javafx.controls,javafx.fxml -jar "$JAR_FILE"