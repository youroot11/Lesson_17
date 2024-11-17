package org.example;
import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.specification.*;
import io.restassured.builder.*;

import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.*;

public class BaseTest {

    private static final String BASE_URI = "https://postman-echo.com";
    private static final String RESPONSE = "This is expected to be sent back as part of response body.";

    private RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;

    @BeforeEach
    public void setBase() {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    @Test
    public void get() {
        given()
                .baseUri("https://postman-echo.com/get")
                .spec(requestSpecification)
                .when()
                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .log().all()
                .body("args.foo1", equalTo("bar1"))
                .body("args.foo2", equalTo("bar2"))
                .body("url", equalTo("https://postman-echo.com/get"));
    }

    @Test
    public void post() {
        given()
                .baseUri("https://postman-echo.com/post")
                .spec(requestSpecification)
                .body("")
                .when()
                .post("/post")
                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .log().all()
                .body("json", equalTo(null));
    }

    @Test
    public void postForm() {
        given()
                .baseUri("https://postman-echo.com/get?foo1=bar1&foo2=bar2")
                .spec(requestSpecification)
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("foo1", "bar1")
                .formParam("foo2", "bar2")
                .when()
                .post("/post")
                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .log().all()
                .body("form.foo1", equalTo("bar1"))
                .body("form.foo2", equalTo("bar2"))
                .body("url", equalTo("https://postman-echo.com/post"));
    }

    @Test
    public void put() {
        given()
                .baseUri("https://postman-echo.com/put")
                .contentType(ContentType.JSON)
                .spec(requestSpecification)
                .body(RESPONSE)
                .when()
                .put("/put")
                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .log().all()
                .body("data", equalTo(RESPONSE))
                .body("url", equalTo("https://postman-echo.com/put"));
    }

    @Test
    public void patch() {
        given()
                .baseUri("https://postman-echo.com/patch")
                .contentType(ContentType.JSON)
                .spec(requestSpecification)
                .body(RESPONSE)
                .when()
                .patch("/patch")
                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .log().all()
                .body("data", equalTo(RESPONSE))
                .body("url", equalTo("https://postman-echo.com/patch"))
                .body("json", equalTo(null));
    }

    @Test
    public void delete() {
        given()
                .baseUri("https://postman-echo.com/delete")
                .spec(requestSpecification)
                .when()
                .delete("/delete")
                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .log().all()
                .body("url", equalTo("https://postman-echo.com/delete"))
                .body("json", equalTo(null));
    }
}
