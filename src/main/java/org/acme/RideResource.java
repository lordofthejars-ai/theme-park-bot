package org.acme;


import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import io.quarkus.runtime.Startup;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.acme.model.Ride;
import org.acme.tools.RidesImporter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.util.List;

@Path("/attraction")
public class RideResource {

    @ConfigProperty(name = "current.location")
    List<Double> currentLocation;

    @Inject
    RidesImporter importer;

    @Startup
    public void populateData() throws IOException {

        importer.insertAttractions();

    }

    @GET
    public List<Ride> listAll() {
        Point refPoint = new Point(new Position(59.329897, 18.063941));
        return Ride.findNear(refPoint);
    }
}
