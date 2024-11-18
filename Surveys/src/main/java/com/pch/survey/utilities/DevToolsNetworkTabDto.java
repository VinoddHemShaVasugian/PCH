package com.pch.survey.utilities;

public class DevToolsNetworkTabDto {

	private String url;
	private String postData;
	private String method;
	private String headers;
	private String response;
	private String status;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getPostData() {
		return postData;
	}

	public void setPostData(String postData) {
		this.postData = postData;
	}

	public void setMethod(String method) {
		this.method = method;

	}

	public String getMethod() {
		return method;
	}

	public void setHeaders(String headers) {
		this.headers = headers;

	}

	public String getHeaders() {
		return headers;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
