package org.acme.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.model.Indexes;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Ride;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class RidesImporter {

    @ConfigProperty(name = "theme.park.data.location")
    String themeParkDataLocation;

    @Inject
    ObjectMapper mapper;

    @Inject
    Logger logger;

    public void insertAttractions() throws IOException {

        logger.infof("Importing theme park %s from Classpath", themeParkDataLocation);

        try (InputStream is = RidesImporter.class.getResourceAsStream(themeParkDataLocation)) {
            JsonNode rides = mapper.readTree(is);
            rides.elements()
                    .forEachRemaining(r -> {
                        Ride ride = new Ride();
                        ride.name = r.get("name").asText();
                        ride.category = r.get("category").asText();
                        List<Double> locations = new ArrayList<>();
                        r.get("location").elements().forEachRemaining(l -> locations.add(l.asDouble()));
                        ride.location = locations;

                        ride.persist();

                        logger.infof("Ride inserted %s", ride);
                    });

            Ride.mongoCollection()
                    .createIndex(
                            Indexes.geo2dsphere("location")
                    );
        }
    }
}
