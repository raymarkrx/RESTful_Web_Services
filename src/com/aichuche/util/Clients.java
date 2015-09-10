package com.aichuche.util;

import java.util.HashMap;
import java.util.Map;

public class Clients {
	
	static Map<String, String> clients = new HashMap<String, String>();
	static{
		clients.put("1", "android");
		clients.put("2", "ios");
		clients.put("3", "winphone");
		clients.put("4", "air");
		clients.put("5", "android-tv");
		clients.put("11", "pc");
		clients.put("12", "web");
		clients.put("13", "pc-server");
		clients.put("14", "wl-server");
	}

	public static String getName(String clientId) {
		return clients.get(clientId);
	}
	
	
	
}
