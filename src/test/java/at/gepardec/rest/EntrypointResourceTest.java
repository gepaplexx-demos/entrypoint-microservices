package at.gepardec.rest;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestHTTPEndpoint(EntrypointResource.class)
class EntrypointResourceTest {

    @Inject
    EntrypointResource entrypoint;

    @Inject
    Logger Log;


}