package at.gepardec.rest;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class EntrypointResourceTest {

    @Test
    public void zeroTtlGivesNoContentResponse() {
        given()
                .when().get("/start/0")
                .then()
                .statusCode(204);
    }

}
