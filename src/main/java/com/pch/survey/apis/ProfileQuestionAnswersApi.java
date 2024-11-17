package com.pch.survey.apis;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pch.survey.utilities.ConfigurationReader;
import com.pch.survey.utilities.WebServiceClient;

public class ProfileQuestionAnswersApi {

	private static WebServiceClient client = new WebServiceClient();
	private static String endPointUrl = ConfigurationReader.getProfileQuestionAnswerApi();
 
 	
	public static Map<String,String> getProfileQuestionsAndAnswers() {

		Map<String,String> dataMap = new HashMap<String,String>();;
		
		
		client.sendGetRequest(endPointUrl, null);
		String jsonString = client.getJSONResponse();

		  try {
	            ObjectMapper objectMapper = new ObjectMapper();
	            JsonNode rootNode = objectMapper.readTree(jsonString);
	            JsonNode messageNode = rootNode.get("message");
	            for (JsonNode questionNode : messageNode) {
	            	if(questionNode.get("id") == null)
	            		break;
	                String id = questionNode.get("id").asText();
	                JsonNode answersNode = questionNode.get("answers");
	                for (JsonNode answerNode : answersNode) {
	                	 if(answerNode.get("dependent_question").asText() == null)
	                	   continue;
	                    String answerId = answerNode.get("id").asText();
	                    dataMap.put(id, answerId);
	                    break;
	                }
	                System.out.println();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return dataMap; 
	
	}
}

 

