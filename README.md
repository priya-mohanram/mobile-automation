## Prerequisites and Setup
- Java 22 and Maven installed.
- Appium server available (default URL in code: `http://127.0.0.1:4723`).
- Android: emulator/device available and visible to Appium.
- iOS: local execution requires macOS/Xcode with XCUITest, or use a supported mobile cloud provider.
- Project config is read from `src/main/resources/config.properties` by default.

## How to Run Mobile Tests on Android
- Set the app path at runtime (`APP_PATH`) because `config.properties` keeps a placeholder value.
- Use the Cucumber JUnit suite class `RunCucumberTest`.

```powershell
$env:APP_PATH="C:\path\to\app.apk"
mvn test -Dtest=RunCucumberTest
mvn test -Dtest=RunCucumberTest "-Dcucumber.features=src/test/resources/features/login.feature"
mvn test -Dtest=RunCucumberTest "-Dcucumber.filter.tags=@smoke"
mvn test -Dtest=RunCucumberTest "-Dcucumber.filter.tags=@regression"
```

## How to Run Mobile Tests on iOS
- Use the committed sample config file: `src/main/resources/config-ios.properties`.
- The file contains sample values only.
- Replace `udid`, `deviceName`, `app`, and `appiumServerUrl` for your target environment.
- Set `APP_PATH` at runtime.
- Local iOS execution requires macOS/Xcode with XCUITest, or a supported cloud device.
- Current iOS locators in page objects are provisional samples and require validation in a real iOS/XCUITest environment.
- iOS execution is configuration-ready, but not claimed as fully validated in this repository.

```powershell
$env:APP_PATH="/path/to/app.app"
mvn test -Dtest=RunCucumberTest -Dconfig.file="src/main/resources/config-ios.properties"
mvn test -Dtest=RunCucumberTest -Dconfig.file="src/main/resources/config-ios.properties" "-Dcucumber.filter.tags=@smoke"
mvn test -Dtest=RunCucumberTest -Dconfig.file="src/main/resources/config-ios.properties" "-Dcucumber.filter.tags=@regression"
```

## How to Run API Tests
- API test class: `src/test/java/com/mobileautomation/api/ApiTest.java`.

```powershell
mvn -Dtest=ApiTest test
```

## Test Coverage
- Successful login
- Invalid-credentials login
- Logout
- Add-to-cart with cart product-name validation
- API test (`GET /posts/1`, status and `id` assertion)

## Failure Screenshots
- Cucumber `Hooks` capture a screenshot and attach it to the scenario report when a mobile scenario fails.

## Configuration Approach
- Default config source: classpath `config.properties`.
- Optional override file: `-Dconfig.file=<path>`.
- Required keys include: `platform`, `deviceName`, `udid`, `app`, `automationName`, `appWaitActivity`.
- `app` resolution priority in `ConfigManager`:
  1. `APP_PATH` environment variable
  2. `app` property in config file

## Framework Structure and Key Decision
- UI automation stack: Appium + Cucumber + JUnit Platform Suite.
- API testing stack: Rest Assured + JUnit.
- POM design: page objects (`BasePage`, `LoginPage`, `ProductsPage`, `CartPage`) hold locator/action logic.
- Driver lifecycle is centralized in `DriverFactory` and Cucumber `Hooks`.
- Cucumber reports are generated via Maven Surefire `cucumber.plugin` configuration.

## AI usage
- AI assistance was used for scaffolding and iterative refactoring of framework code, locators, and test organization.
- Final behavior depends on local/mobile environment setup and runtime configuration values.

## Test Reports
- Cucumber HTML report: `target/cucumber-reports/cucumber.html`
- Cucumber JSON report: `target/cucumber-reports/cucumber.json`
- Maven Surefire reports: `target/surefire-reports`
