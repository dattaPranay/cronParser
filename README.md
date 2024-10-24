# Cron Expression Parser

This command-line application parses a cron expression and expands each field to display the exact times at which the scheduled command will run.

## Overview

The parser handles standard cron expressions consisting of five time fields:

1. **Minute**
2. **Hour**
3. **Day of Month**
4. **Month**
5. **Day of Week**

These fields are followed by the command to execute. The application accepts the cron expression as a single argument provided in one line.
- Note: The case where a month has fewer than 31 days has not been handled.

## Features

- **Standard Cron Parsing**: Supports standard cron syntax for time fields.
- **Field Expansion**: Expands each time field to list all the times when the command will run.
- **Command Execution Display**: Clearly shows the command to be executed.
- **User-Friendly Output**: Formats output in an easy-to-read table.

## Prerequisites

- **JDK 20 or higher**: [Download from Oracle](https://www.oracle.com/java/technologies/javase-downloads.html) or use your package manager.
- **Maven**: Ensure Maven is installed (`mvn -v` to check). Alternatively, use the provided Maven wrapper script `./mvnw` if included in your project.

## Running the Script:

- **Make the Script Executable:**   `chmod +x run.sh`
- **Run the Script:** `./run.sh "*/15 0 1,15 * 1-5 /usr/bin/find"`

### Sample Output

Given the input:

```bash
$ ./run.sh "*/15 0 1,15 * 1-5 /usr/bin/find"
```

The program produces:

```
minute            0 15 30 45
hour              0
day of month      1 15
month             1 2 3 4 5 6 7 8 9 10 11 12
day of week       1 2 3 4 5
command           /usr/bin/find
```

#### Output Format Details

- **Field Names**: Each line starts with the field name, occupying the first 14 characters for alignment.
- **Expanded Values**: Lists all the possible values for each time field, separated by spaces.
- **Command**: Displays the command exactly as provided.

## Testing

Basic test cases are included in the `test/` directory.
