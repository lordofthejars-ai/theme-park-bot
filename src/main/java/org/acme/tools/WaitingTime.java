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

    public void setWaitingTime(String attraction, Duration waitingTime) {
        this.timeCommands.set(attraction, waitingTime.toMinutes());
    }

    public Duration getWaitingTime(String attraction) {
        return Duration.ofMinutes(this.timeCommands.get(attraction));
    }

}
