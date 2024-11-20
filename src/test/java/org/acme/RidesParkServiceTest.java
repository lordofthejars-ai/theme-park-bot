package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.util.List;
import org.acme.ai.RidesParkService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class RidesParkServiceTest {

    @ConfigProperty(name = "current.location")
    List<Double> currentLocation;

    @Inject
    RidesParkService ridesParkService;

    @Test
    public void shouldReturnWaitingTimeOfARide() {

        String response = ridesParkService.chat("What is the waiting times for Shambala ride?",
            59.329897, 18.063941);
        System.out.println(response);

    }

    @Test
    public void shouldReturnRidesNearMe() {
        String response = ridesParkService.chat("What are the rides that are near my position",
            currentLocation.getFirst(), currentLocation.getLast());
        System.out.println(response);
    }

}
