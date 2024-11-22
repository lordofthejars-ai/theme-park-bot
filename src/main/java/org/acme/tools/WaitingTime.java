package org.acme.tools;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.KeyCommands;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class WaitingTime {

    private final ValueCommands<String, Long> timeCommands;
    private final KeyCommands<String> keyCommands;

    public WaitingTime(RedisDataSource ds) {
        this.timeCommands = ds.value(Long.class);
        this.keyCommands = ds.key();
    }

    public void setWaitingTime(String attraction, long waitingTime) {
        this.timeCommands.set(attraction, waitingTime);
    }

    public long getWaitingTime(String attraction) {
        return this.timeCommands.get(attraction);
    }

    public Map<String, Long> getWaitingTimes() {
        final List<String> keys = this.keyCommands.keys("*")
            .stream()
            .filter(k -> !k.startsWith("embedding")).toList();
        return this.timeCommands.mget(keys.toArray(new String[0]));
    }

}
