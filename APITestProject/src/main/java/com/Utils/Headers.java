package com.Utils;

import java.util.HashMap;

public class Headers {
	private HashMap<String, String> headers = null;

	public Headers() {
		headers = new HashMap<String, String>();
	}

	public void setContentTypeAsJSON() {
		headers.put("Content-Type", "application/json");
	}

	public HashMap<String, String> getHeaders() {
		return headers;
	}
}
