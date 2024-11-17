/*
 * @Author pvadivelu
 * @Version 1.0.0
 * PCH Serenity Framework Template
 */
package com.pch.sample.steps;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;

import com.pch.sample.response.dto.SampleDto;
import com.pch.sample.utilities.AppConfigLoader;
import com.pch.sample.utilities.WebServiceClient;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;


/**
 * The Class SampleApiSteps.
 */
public class SampleApiSteps extends ScenarioSteps{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The web service client. */
	private WebServiceClient webServiceClient = new WebServiceClient();
	
	/** The object json mapper. */
	private ObjectMapper objectJsonMapper = new ObjectMapper();
	
	/** The sample dto. */
	private SampleDto sampleDto[];

	/**
	 * Call active segmentation.
	 */
	@Step
	public void callActiveSegmentation(){
		webServiceClient.submitHttpGetRequest(AppConfigLoader.getInstance().sampleAPIURL+"/segmentationapi/svc/segmentation.svc/json/api/segments/active");
		try {
			sampleDto = objectJsonMapper.readValue(
					webServiceClient.getJSONResponse(), SampleDto[].class);
		} catch (JsonParseException e) {			
			e.printStackTrace();
		} catch (JsonMappingException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}

	}

	/**
	 * Verify active segments.
	 */
	@Step
	public void verifyActiveSegments(){
		Assert.assertTrue("Verify atleast one active segment returned", sampleDto.length > 0);
		for(SampleDto dto : sampleDto){
			System.out.println(dto.getCode());
			System.out.println(dto.getId());
			System.out.println(dto.getIsActive());
			System.out.println(dto.getCreated());
			System.out.println(dto.getName());
		}
	}
}
