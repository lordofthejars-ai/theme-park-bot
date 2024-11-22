package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.ai.RidesRouteService;
import org.acme.ai.Route;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class RidesRouteTest {

    @Inject
    RidesRouteService ridesRoute;

    @Test
    public void shouldCategorizeAQuestionAsRidesPark() {
        Route route = ridesRoute.route("What is the waiting times for Shambala ride?");
        assertThat(route).isEqualTo(Route.RIDES_PARK);
    }

    @Test
    public void shouldCategorizeAQuestionAsRidesInformation() {
        Route route = ridesRoute.route("What rides I can go if my height is 115 cm?");
        assertThat(route).isEqualTo(Route.RIDES_INFORMATION);
    }

    @Test
    public void shouldCategorizeAQuestionAsUnknown() {
        Route route = ridesRoute.route("What is the weather for today?");
        assertThat(route).isEqualTo(Route.UNKNOWN);
    }

}
