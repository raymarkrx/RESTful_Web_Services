package com.aichuche.service;

import java.util.Map;

public interface CommonEventService {

	void storeEvents(Map<String, Map<String, Object>> events, String key);

	void destory();

}
