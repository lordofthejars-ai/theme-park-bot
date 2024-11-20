package org.acme.model;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.geojson.Point;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

import org.bson.conversions.Bson;

import java.util.List;
import java.util.StringJoiner;

@MongoEntity(collection="ride")
public class Ride extends PanacheMongoEntity {

    public String name;
    public String category;

    public List<Double> location;

    public static List<Ride> findNear(Point point) {
        Bson query = Filters
                .near("location",
                        point,
                        140.0,
                        0.0);
        return find(query).list();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Ride.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("category='" + category + "'")
                .add("location=" + location)
                .toString();
    }
}
