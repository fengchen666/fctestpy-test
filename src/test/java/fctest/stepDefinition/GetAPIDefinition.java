package fctest.stepDefinition;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;

import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

public class GetAPIDefinition {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GetAPIDefinition.class);

    HttpClient client = HttpClientBuilder.create().build();
    public static HttpGet HttpGetTest;
    public static HttpResponse HttpGetResponse;
    public static String HttpGetResponseString;

    public static String myUrl = "http://localhost:8080/";
    public static Integer returnCode;

    @When("^I Get the \"([^\"]*)\" url$")
    public void i_Get_the_url(String urlToGet) throws Throwable {

        if (System.getProperty("url") != null) {
            myUrl = System.getProperty("url");
        }

        LOGGER.info("The url is {}", myUrl);

        LOGGER.info("I get the " + urlToGet);

        switch (urlToGet) {
            case "root":
                HttpGetTest = new HttpGet(myUrl);

                break;
            case "health":
                HttpGetTest = new HttpGet(myUrl + "health");

                break;
            case "metadata":
                HttpGetTest = new HttpGet(myUrl + "metadata");

                break;
            default:
                LOGGER.info("Wrong content to GET");
                break;
        }

        try {
            HttpGetResponse = client.execute(HttpGetTest);

            returnCode = HttpGetResponse.getStatusLine().getStatusCode();

            // CONVERT RESPONSE TO STRING
            HttpGetResponseString = EntityUtils.toString(HttpGetResponse.getEntity());
            LOGGER.info("http response is " + HttpGetResponseString);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Then("^I am able to Get the return code of (\\d+)$")
    public void i_am_able_to_Get_the_return_code_of(int returncode) throws Throwable {

        LOGGER.info("Return code is " + returnCode);
        assertTrue("Return code is not 200", returnCode == returncode);

    }

    @Then("^I am able to Get the \"([^\"]*)\"$")
    public void i_am_able_to_Get_the(String bodyGet) throws Throwable {

        JSONParser parser = new JSONParser();

        boolean successful = false;

        switch (bodyGet) {
            case "hello world":

                if (HttpGetResponseString.contains("Hello, world")) {
                    successful = true;
                }
                assertTrue("Return body is incorrect", successful);

                break;

            case "health":

                if (HttpGetResponseString.contains("\"status\": \"healthy\"")) {
                    successful = true;
                }
                assertTrue("Return body is incorrect", successful);
                break;

            case "lastcommitsha;version;description":

                JSONArray arr = (JSONArray) parser.parse(HttpGetResponseString);
                JSONObject jsonitem = (JSONObject) arr.get(0);

                if (jsonitem.get("version").equals("1.0") && jsonitem.get("description").equals("technical test")) {
                    successful = true;
                }

                assertTrue("Return body is incorrect", successful);
                break;

            default:
                LOGGER.info("Wrong content to GET");
                break;
        }
    }


}
