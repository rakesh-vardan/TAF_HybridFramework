# Automation Framework

This document provides an overview of the automation framework, including the tools used, design patterns followed, and instructions for running tests.

## Overview

This automation framework is designed for end-to-end testing of web applications using Selenium WebDriver and TestNG. The framework supports parallel test execution, reporting, and logging.

## Tools Used

- **Selenium WebDriver**: For browser automation.
- **TestNG**: For test management and execution.
- **Log4j2**: For logging.
- **ExtentReports**: For detailed HTML reports.
- **Apache Commons IO**: For file operations (e.g., screenshot capture).

## Design Patterns

### Page Object Model (POM)

The framework follows the Page Object Model (POM) design pattern. Each web page in the application has a corresponding Page class that encapsulates the page's functionality and elements. This promotes code reusability and maintainability.

### Data-Driven Testing

The framework uses TestNG's DataProvider to supply test data for different scenarios. This allows for running the same test with different inputs without duplicating code.

### Custom Exception Handling

Custom exceptions are used to handle specific scenarios like login failures or missing elements, improving the clarity of error handling and debugging.

### Logger and Reporting

- **Log4j2** is used for logging different stages of test execution.
- **ExtentReports** is integrated to generate detailed test reports with screenshots and execution logs.

## Directory Structure

```
src
|-- main
|   |-- java
|       |-- io
|           |-- learn
|               |-- pages
|               |-- tests
|               |-- utils
    resources
        |-- application.properties
        |-- log4j2.xml
|-- test
    |-- java
        |-- io
            |-- learn
|               |-- data_provider
|               |-- listeners
                |-- tests
pom.xml
testng.xml
target
    |-- logs
        |-- test.log
    |-- screenshots
        |-- testLoginSuccess_12345.png
    |-- extent-reports.html
```

- **pages**: Contains Page Object classes for each page.
- **utils**: Contains utility classes like `WebDriverFactory`, `ConfigReader`, and `ScreenshotUtility`.
- **data_provider**: Contains classes for providing test data.
- **listeners**: Contains TestNG listeners for reporting and logging.
- **tests**: Contains test classes.

## Configuration

### Log4j2 Configuration

The logging configuration is defined in `src/main/resources/log4j2.xml`. It specifies console and file appenders with patterns for log formatting.

### Test Configuration

Test configuration is specified in `src/main/resources/application.properties`. 

Example:

```properties
base.url=https://www.saucedemo.com/
```

## Running Tests

### Prerequisites

- Ensure you have Java JDK installed.
- Ensure Maven is installed for dependency management.

### Setup

1. **Clone the Repository**

    ```bash
    git clone <repository-url>
    cd <repository-directory>
    ```

2. **Install Dependencies**

   Run the following command to install dependencies:

    ```bash
    mvn install
    ```

### Running Tests

1. **Simple Test Suite**

   To run a test suite, use:

    ```bash
    mvn clean test -DsuiteXMLFile=<testng-xml-name>
    ```

2. **Group Tests**

   To run tests with a specific group (e.g., `smoke`):

    ```bash
    mvn clean test -DsuiteXMLFile=<testng-xml-with-groups>
    ```

3. **Parallel Execution**

   To enable parallel test execution, configure it in the `testng.xml` file or use the following command:

    ```bash
    mvn clean test -DsuiteXMLFile=<testng-xml-with-parallel-tag>
    ```

### Viewing Reports

- **Extent Reports**: Generated reports are located in the `target/extent-reports.html` file.

---
