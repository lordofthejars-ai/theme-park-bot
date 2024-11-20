package org.acme.ai;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import org.acme.model.Ride;
import org.acme.tools.WaitingTime;
import org.jboss.logging.Logger;

@ApplicationScoped
public class RidesTool {

    @Inject
    Logger logger;

    @Inject
    WaitingTime waitingTime;

    @Tool("get the rides near me")
    public List<String> findNearRides(double latitude, double longitude) {

        logger.infof("Find Near Rides tool was called with lat %s long %s", latitude, longitude);

        Point refPoint = new Point(new Position(latitude, longitude));
        return Ride.findNear(refPoint).stream().map(r -> r.name).toList();
    }


    @Tool("get the waiting time for the given ride name")
    public long getWaitingTime(String rideName) {

        logger.infof("Waiting time tool was called with %s ride name", rideName);

        return waitingTime.getWaitingTime(rideName);
    }

}
