package edu.praktikum.sprint7;

import edu.praktikum.sprint7.clients.CourierClient;
import edu.praktikum.sprint7.models.Courier;
import edu.praktikum.sprint7.models.CourierCredsResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static edu.praktikum.sprint7.generators.CourierGenerator.randomCourier;
import static edu.praktikum.sprint7.models.CourierCreds.credsFromCourier;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourierTests {

    private final CourierClient courierClient = new CourierClient();

    private String id;

    @Test
    public void createCourierTest() {
        Courier courier = randomCourier();
        Response response = courierClient.create(courier);

        assertEquals(201, response.statusCode());

        Response loginResponse = courierClient.login(credsFromCourier(courier));
        id = loginResponse.as(CourierCredsResponse.class).getId();

        assertEquals(200, loginResponse.statusCode());
    }

    @AfterEach
    public void tearDown() {
        courierClient.delete(id);
    }
}
