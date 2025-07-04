# Selenium Grid with Java, Docker, Jenkins (Dynamic & Multi-Browser)

## Steps

1. Start Selenium Grid manually (or Jenkins starts it):
    ```bash
    docker-compose up -d
    ```

2. Run tests locally:
    ```bash
    mvn clean test
    ```

3. Extent reports will be in `test-output/ExtentReports/`.

## Jenkins Pipeline (Declarative & Dynamic)

- Parameters:
    - `GIT_REPO`: Repository URL
    - `BRANCH`: Branch to build
    - `BROWSERS`: Comma-separated browsers (chrome,firefox)
- Starts Selenium Grid dynamically
- Runs tests
- Publishes multi-browser Extent Reports
- Stops Grid and cleans up workspace
