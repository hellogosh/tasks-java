package training.tracker.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import training.tracker.stub.TrackerStubExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static training.tracker.config.ConfigProvider.config;

@ExtendWith(TrackerStubExtension.class)
@Tag("api")
public class TaskApiSmokeTest {

    @Test
    @Tag("smoke")
    void taskEndpointShouldReturnTask() {
       Response response =  given()
                .baseUri(config().baseUrl())
                .when()
                .get("/tasks/QA-1")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    @Test
    @Tag("regression")
    void unknownTaskEndpointShouldReturn404WithError() {
        given()
                .baseUri(config().baseUrl())
                .when()
                .get("/tasks/QA-999")
                .then()
                .statusCode(404)
                .contentType("application/json")
                .body("error", equalTo("Task not found"))
                .body("key", equalTo("QA-999"));
    }

        }



