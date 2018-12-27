# API tests for fctestpy.webapp

## Setup

- Make sure that Java/Maven is installed.
- The test can be started using `mvn test -Durl="http://www.fengtest.net/"`. If no url environment is provided, it will test the localhost url.
- Now the test is run in Jenkins [http://3.104.72.234:8080/job/fctestpy-project-test/](http://3.104.72.234:8080/job/fctestpy-project-test/).
- The test is based on Cucumber and the test report can be found in Jenkins as well.