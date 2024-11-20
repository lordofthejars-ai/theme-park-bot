package org.acme.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(tools = RidesTool.class, retrievalAugmentor = RegisterAiService.NoRetrievalAugmentorSupplier.class)
public interface RidesParkService {

    @SystemMessage("""
        You are an assistant for answering questions about a theme park.
        
        These questions can only be related to theme park.
        Example of these questions can be:
        
        - What is the waiting time for a specific ride?
        - What rides are near me?
        - Give me information about a specific ride
         
        If questions are not about theme park or you don't know the answer, you should return always "I don't know".
        Don't give information that it is wrong
        """)
    @UserMessage("""
        The theme park user has the following question: {question}
        
        The current latitude position of the user is {latitude} and the longitude is {longitude} 
        """)
    String chat(@V("question") String question,
                @V("latitude") double latitude,
                @V("longitude") double longitude);

}
