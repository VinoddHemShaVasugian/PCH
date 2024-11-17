package com.pch.survey.utilities;

import java.util.ArrayList;
import java.util.Optional;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.DevTools;
//import org.openqa.selenium.devtools.v114.network.Network;
import org.openqa.selenium.devtools.v116.network.Network;

import com.google.common.collect.ImmutableMap;

public class SeleniumDevTools {

	static DevTools devTools;
	static ArrayList<DevToolsNetworkTabDto> dtoList = new ArrayList<DevToolsNetworkTabDto>();

	public static ArrayList<DevToolsNetworkTabDto> startDevToolsNetworkSession(ChromeDriver driver) {
		dtoList.clear();
		devTools = driver.getDevTools();
		devTools.createSession();
		devTools.send(new Command<>("Network.enable", ImmutableMap.of()));
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.addListener(Network.requestWillBeSent(), l -> {
			DevToolsNetworkTabDto dto = new DevToolsNetworkTabDto();
			dto.setUrl(l.getRequest().getUrl());
			dto.setPostData(l.getRequest().getPostData().toString());
			dtoList.add(dto);
			// System.out.println(l.getRequest().getUrl() + " " +
			// l.getRequest().getPostData().toString());
		});
		return dtoList;

	}

	public static void stopDevToolsNetworkSession() {
		devTools.disconnectSession();

	}

	public static ArrayList<DevToolsNetworkTabDto> getNetworkRequests() {
		return dtoList;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
