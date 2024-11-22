package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.ai.RidesInformationService;
import org.acme.ai.RidesParkService;
import org.acme.ai.RidesRouteService;
import org.acme.ai.Route;

@ApplicationScoped
public class ChatManager {

    @Inject
    RidesParkService ridesParkService;

    @Inject
    RidesInformationService ridesInformationService;

    @Inject
    RidesRouteService ridesRouteService;

    public String resolveQuestion(String question, double longitude, double lat) {

        final Route route = ridesRouteService.route(question);

        return switch (route) {
            case RIDES_PARK -> ridesParkService.chat(question, longitude, lat);
            case RIDES_INFORMATION -> ridesInformationService.chat(question);
            case UNKNOWN -> "Cannot answer your question";
        };
    }


}
