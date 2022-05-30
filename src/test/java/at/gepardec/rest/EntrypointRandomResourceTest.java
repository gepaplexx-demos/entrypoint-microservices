package at.gepardec.rest;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class EntrypointRandomResourceTest {

    @Test
    public void zeroTtlGivesNoContentResponse() {
        given()
                .when().get("/start/0")
                .then()
                .statusCode(204);
    }

}
