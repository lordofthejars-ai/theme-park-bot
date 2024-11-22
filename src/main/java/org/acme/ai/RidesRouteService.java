package org.acme.ai;

import dev.langchain4j.service.SystemMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(retrievalAugmentor = RegisterAiService.NoRetrievalAugmentorSupplier.class)
public interface RidesRouteService {

    @SystemMessage("""
            You are an expert at routing a user question to a give a response. 
            
            If the question is related to waiting time of a ride or the location of a ride then return RIDES_PARK.
            
            Return RIDES_INFORMATION when the question is related to information of a ride like a description or restrictions to ride.
            
            In case the question is not related to theme park or you don't know how to categorize, then return UNKNOWN.
            """)
    Route route(String text);
}
