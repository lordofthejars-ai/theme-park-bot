package org.acme.tools;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;


@ApplicationScoped
public class WaitingTime {

    private final ValueCommands<String, Long> timeCommands;

    public WaitingTime(RedisDataSource ds) {
        this.timeCommands = ds.value(Long.class);
    }

    public void setWaitingTime(String attraction, long waitingTime) {
        this.timeCommands.set(attraction, waitingTime);
    }

    public long getWaitingTime(String attraction) {
        return this.timeCommands.get(attraction);
    }

}
