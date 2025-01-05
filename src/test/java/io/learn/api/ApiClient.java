package io.learn.api;

import io.learn.utils.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

public class ApiClient {

    private static final Logger logger = LogManager.getLogger(ApiClient.class);
    private static final ConfigReader configReader = new ConfigReader();
    private static final String BASE_URL = configReader.getProperty("api.url");

    private ApiClient() {
    }

    public static RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static RequestSpecification getRequestSpecificationWithHeader() {
        return getRequestSpecification().header("Content-Type", ContentType.JSON);
    }

    public static Response get(String endpoint) {
        return given()
                .spec(getRequestSpecification())
                .log().all()
                .when()
                .log().all()
                .get(endpoint);
    }

    public static Response post(String endpoint, String body) {
        return given()
                .spec(getRequestSpecificationWithHeader())
                .body(body)
                .when()
                .post(endpoint);
    }

    public static Response put(String endpoint, String body) {
        return given()
                .spec(getRequestSpecificationWithHeader())
                .body(body)
                .when()
                .put(endpoint);
    }

    public static Response delete(String endpoint) {
        return given()
                .spec(getRequestSpecification())
                .when()
                .delete(endpoint);
    }
}
