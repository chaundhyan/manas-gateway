package com.mukul.pro.manasgateway.controller;

import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1")
public class AnalysisController {

    @GetMapping("/getSentiment/{text}")
    @ResponseBody
    public float getSentiment(@PathVariable String text) throws IOException {
        float sentimentScore;
        try (LanguageServiceClient language = LanguageServiceClient.create()) {
            System.out.println("Text Received: " + text);
            Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();
            System.out.println("Document Created: " + doc.getContent());

            // Detects the sentiment of the text
            Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();
            System.out.println("Got Sentiment: " + sentiment.getScore());
            sentimentScore = sentiment.getScore();
            System.out.printf("Text: %s%n", text);
            System.out.printf("Sentiment: %s, %s%n", sentiment.getScore(), sentiment.getMagnitude());
        }
        return sentimentScore;
    }
}