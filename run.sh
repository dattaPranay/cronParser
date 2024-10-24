#!/bin/bash
echo "Building the project..."
mvn clean install
JAR_FILE=$(find target -name "*.jar" -not -name "*-sources.jar" -not -name "*-javadoc.jar")

if ! command -v java &> /dev/null
then
    echo "Error: Java is not installed. Please install Java 11 or higher."
    exit 1
fi


CRON_EXPRESSION="$*"

# Run the Java program with the provided arguments
java -jar "$JAR_FILE" "$CRON_EX RESSION"