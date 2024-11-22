package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.ai.RidesInformationService;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class RidesInformationServiceTest {

    @Inject
    RidesInformationService ridesInformationService;

    @Test
    public void shouldGiveInformationAboutARide() {
        String response = ridesInformationService.chat("Can you give me an explanation of what is Shambala ride?");
        System.out.println(response);
    }

    @Test
    public void shouldGiveTheMinimumHeightOfARide() {
        String response = ridesInformationService.chat("What is the minimum height for Uncharted ride?");
        System.out.println(response);
    }

    @Test
    public void shouldGiveAllRidesForTheGivenHeight() {
        String response = ridesInformationService.chat("What rides I can go if my height is 115 cm?");
        System.out.println(response);
    }


}
