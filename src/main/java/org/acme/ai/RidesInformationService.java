package org.acme.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService()
public interface RidesInformationService {

    @SystemMessage("""
        You are an assistant for answering questions about a theme park.
        
        These questions can only be related to theme park.
        Example of these questions can be:
        
        - Can you describe a given ride?
        - What is the minimum height to enter to a ride?
        - What rides can I access with my height? 
         
        If questions are not about theme park or you don't know the answer, you should return always "I don't know".
        Don't give information that it is wrong
        """)
    @UserMessage("""
        The theme park user has the following question: {question}
        """)
    String chat(String question);

}
