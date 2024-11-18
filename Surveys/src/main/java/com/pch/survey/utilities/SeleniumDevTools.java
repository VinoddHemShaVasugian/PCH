package com.pch.survey.utilities;

import java.util.ArrayList;
import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v125.network.Network;
import org.openqa.selenium.devtools.v125.network.model.Request;
import org.openqa.selenium.devtools.v125.network.model.RequestId;
import org.openqa.selenium.devtools.v125.network.model.Response;

import com.google.common.collect.ImmutableMap;

public class SeleniumDevTools {

	static DevTools devTools;
	static ArrayList<DevToolsNetworkTabDto> dtoList = new ArrayList<DevToolsNetworkTabDto>();

	public static ArrayList<DevToolsNetworkTabDto> getRequestsDetails(WebDriver driver, String url) {
		DevToolsNetworkTabDto dto = new DevToolsNetworkTabDto();
		dtoList.clear();
		devTools = ((ChromeDriver) driver).getDevTools();
		devTools.createSession();
		devTools.send(new Command<>("Network.enable", ImmutableMap.of()));
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.addListener(Network.requestWillBeSent(), requestConsumer -> {
			Request request = requestConsumer.getRequest();
			if (request.getUrl().contains(url)) {
				dto.setUrl(request.getUrl());
				System.out.println("res.getUrl(): " + request.getUrl());
				dto.setPostData(request.getPostData().toString());
				dto.setMethod(request.getMethod().toString());
				dto.setHeaders(request.getHeaders().toString());
			}
			dtoList.add(dto);
		});
		return dtoList;

	}

	public static ArrayList<DevToolsNetworkTabDto> getResponseDetails(WebDriver driver, String url) {
		DevToolsNetworkTabDto dto = new DevToolsNetworkTabDto();
		dtoList.clear();
		devTools = ((ChromeDriver) driver).getDevTools();
		devTools.createSession();
		devTools.send(new Command<>("Network.enable", ImmutableMap.of()));
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.addListener(Network.responseReceived(), responseConsumer -> {
			Response response = responseConsumer.getResponse();
			RequestId[] requestId = new RequestId[1];
			dto.setUrl(response.getUrl());
			dto.setStatus(response.getStatus().toString());
			dto.setHeaders(response.getHeaders().toString());
			if (response.getUrl().contains(url)) {
				dto.setUrl(response.getUrl());
				dto.setResponse(devTools.send(Network.getResponseBody(requestId[0])).getBody().toString());
			}

			dtoList.add(dto);
		});
		return dtoList;

	}

//	public static ArrayList<DevToolsNetworkTabDto> startDevToolsNetworkSession(ChromeDriver driver) {
//	dtoList.clear();
//	devTools = driver.getDevTools();
//	devTools.createSession();
//	devTools.send(new Command<>("Network.enable", ImmutableMap.of()));
//	devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
//	devTools.addListener(Network.requestWillBeSent(), l -> {
//		DevToolsNetworkTabDto dto = new DevToolsNetworkTabDto();
//		dto.setUrl(l.getRequest().getUrl());
//		dto.setPostData(l.getRequest().getPostData().toString());
//		dtoList.add(dto);
//		// System.out.println(l.getRequest().getUrl() + " " +
//		// l.getRequest().getPostData().toString());
//	});
//	return dtoList;
//
//}

	public static void stopDevToolsNetworkSession() {
		devTools.disconnectSession();

	}

	public static ArrayList<DevToolsNetworkTabDto> getNetworkRequests() {
		return dtoList;

	}

	public static void main(String[] args) {

	}

}
