package org.acme;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.runtime.Startup;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.acme.tools.RidesImporter;
import org.acme.tools.WaitingTime;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.util.List;

@Path("/")
public class RideResource {

    @ConfigProperty(name = "current.location")
    List<Double> currentLocation;

    @ConfigProperty(name = "quarkus.langchain4j.easy-rag.path")
    String docPath;

    @Inject
    RidesImporter importer;

    @Inject
    WaitingTime waitingTime;

    @Startup
    public void populateData() throws IOException {

        importer.deleteAttractions();
        importer.insertAttractions();

    }

    @Inject
    ChatManager chatManager;

    @Inject
    ObjectMapper mapper;

    @POST
    @Path("/chat")
    public JsonNode chat(JsonNode node) {

        String question = node.get("message").asText();

        String response = chatManager.resolveQuestion(question,
            currentLocation.getFirst(), currentLocation.getLast());

        return mapper.createObjectNode()
            .put("message", response);
    }

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance index(String coordinates, Set<Map.Entry<String, Long>> waitingTimes, Set<Map.Entry<java.nio.file.Path, String>> rides);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Blocking
    public TemplateInstance get() throws IOException {
        return Templates.index(
            getCoordinates(),
            getWaitingTimes().entrySet(),
            getRidesInfo().entrySet());
    }

    private Map<java.nio.file.Path, String> getRidesInfo() throws IOException {
        try (Stream<java.nio.file.Path> stream = Files.list(Paths.get(docPath))) {
            return stream
                .filter(file -> !Files.isDirectory(file))
                .collect(Collectors.toMap(java.nio.file.Path::getFileName, path -> {
                    try {
                        return Files.readString(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                );
        }
    }

    private Map<String, Long> getWaitingTimes() {
        return this.waitingTime.getWaitingTimes();
    }

    private String getCoordinates() {
        return "%s long - %s lat".formatted(currentLocation.getFirst(), currentLocation.getLast());
    }

    /**@GET
    public List<Ride> listAll() {
        System.out.println(currentLocation);
        Point refPoint = new Point(new Position(currentLocation.getFirst(), currentLocation.getLast()));
        return Ride.findNear(refPoint);
    }**/
}
