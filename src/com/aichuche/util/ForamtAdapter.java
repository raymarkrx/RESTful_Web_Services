package com.aichuche.util;
//package com.bianfeng.bfas.bfrd.util;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Collection;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.hadoop.io.MD5Hash;
//
//import com.bianfeng.base.util.ObjectMapConverter;
//import com.bianfeng.bfas.bfrd.dto.EventPackage;
//import com.bianfeng.bfas.bfrd.dto.RequestWarpper;
//
//public class ForamtAdapter {
//
//	private static String INIT_EVENT_ID = "1";
//	private static String EXCEPTION_EVENT_ID = "2";
//
//	@SuppressWarnings("unchecked")
//	public static Map<String, Map<String, Object>> separate(RequestWarpper reqWarpper) {
//		Map<String, Map<String, Object>> eventsMap = new HashMap<String, Map<String, Object>>();
//		
//		String requestType = reqWarpper.getType();
//		
//		if(UtilData.Request.Type.MSG_PACK.equals(requestType)){
//			//msgpack
//			EventPackage eventPackage = (EventPackage)reqWarpper.getValue();
//			
//			//convert object to map
//			Map<String, Object> dataMap = (Map<String, Object>)ObjectMapConverter.convertToMap(eventPackage);
//			
//			dataMap.put("uuid", reqWarpper.getUuid());
//			dataMap.put("ip", reqWarpper.getRemoteIp());
//			dataMap.put("receiveTimestamp", reqWarpper.getReceiveTimestamp());
//			dataMap.put("requestUri", reqWarpper.getRequestUri());
//			dataMap.put("isRepair", reqWarpper.isRepair());
//			dataMap.put("isPc", 0);
//			
//			Object appProfile = dataMap.remove("appProfile");
//			if(appProfile != null && appProfile instanceof Map<?, ?>){
//				Map<String, Object> appProfileAsMap = (Map<String, Object>)appProfile; 
//				updateMapKey(appProfileAsMap, "startTime", "appStartTime");
//				updateMapKey(appProfileAsMap, "osType", "clientType");
//				dataMap.putAll(appProfileAsMap);
//			}
//			
//			Object deviceProfile = dataMap.remove("deviceProfile");
//			if(deviceProfile != null && deviceProfile instanceof Map<?, ?>){
//				Map<String, Object> deviceProfileAsMap = (Map<String, Object>)deviceProfile; 
//				dataMap.putAll(deviceProfileAsMap);
//			}
//
//			updateMapKey(dataMap, "deveploperAppkey", "appKey");
//			
//			Object messages = dataMap.remove("messages");
//			if(messages == null){
//				//no events
//			}else if(messages instanceof Collection){
//				for(Object message : (Collection<?>)messages){
//					if(message != null){
//						if(message instanceof Map<?, ?>){
//							Map<String, Object> messageAsMap = (Map<String, Object>)message;
//							
//							Object session = messageAsMap.remove("session");
//							Object initProfile = messageAsMap.remove("initProfile");
//							Object exception = messageAsMap.remove("appException");
//							
//							if(session != null && session instanceof Map<?, ?>){
//								Map<String, Object> sessionAsMap = (Map<String, Object>)session; 
//								updateMapKey(sessionAsMap, "id", "sessionid");
//								updateMapKey(sessionAsMap, "duration", "sessionDuration");
//								updateMapKey(sessionAsMap, "status", "sessionStatus");
//								updateMapKey(sessionAsMap, "start", "sessionStart");
//								updateMapKey(sessionAsMap, "isConnected", "sessionConnected");
//								
//								Object appEvents = sessionAsMap.remove("appEvents");
//								dataMap.putAll(sessionAsMap);
//								if(appEvents != null && appEvents instanceof Collection){
//									for(Object event : (Collection<?>)appEvents){
//										if(event != null){
//											if(event instanceof Map<?, ?>){
//												Map<String, Object> eventAsMap = mergeMaps(dataMap, (Map<String, Object>)event);
//												addEvent(eventsMap, eventAsMap);
//											}
//										}
//									}
//								}
//							}
//							
//							if(initProfile != null && initProfile instanceof Map<?, ?>){
//								Map<String, Object> initProfileAsMap = (Map<String, Object>)initProfile; 
//								Map<String, Object> event = convertInitProfileToEvent(initProfileAsMap, dataMap.get("appStartTime"));
//								
//								Map<String, Object> eventAsMap = mergeMaps(dataMap, event);
//								addEvent(eventsMap, eventAsMap);
//							}
//
//							if(exception != null && exception instanceof Map<?, ?>){
//								Map<String, Object> exceptionAsMap = (Map<String, Object>)exception; 
//								Map<String, Object> event = convertExceptionToEvent(exceptionAsMap);
//								
//								Map<String, Object> eventAsMap = mergeMaps(dataMap, event);
//								addEvent(eventsMap, eventAsMap);
//							}
//						}
//					}
//				}
//			}			
//
//		}else if(UtilData.Request.Type.JSON.equals(requestType)){
//			//json
//			Map<String, Object> dataMap = (Map<String, Object>)reqWarpper.getValue();
//
//			dataMap.put("uuid", reqWarpper.getUuid());
//			dataMap.put("ip", reqWarpper.getRemoteIp());
//			dataMap.put("receiveTimestamp", reqWarpper.getReceiveTimestamp());
//			dataMap.put("requestUri", reqWarpper.getRequestUri());
//			dataMap.put("isRepair", reqWarpper.isRepair());
//			
//			Object clientType = dataMap.get("clientType");
//			if(clientType != null && "14".equals(clientType.toString())){
//				dataMap.put("isPc", 0);
//			}else{
//				dataMap.put("isPc", 1);
//			}
//			
//			Object events = dataMap.remove("appEvents");
//			if(events == null){
//				//no events
//			}else if(events instanceof Collection){
//				for(Object event : (Collection<?>)events){
//					if(event != null){
//						if(event instanceof Map<?, ?>){
//							Map<String, Object> eventAsMap = mergeMaps(dataMap, (Map<String, Object>)event);
//							standardStartTime(eventAsMap);
//							addEvent(eventsMap, eventAsMap);
//						}
//					}
//				}
//			}else if(events instanceof Map<?, ?>){
//				//防止客户端将AppEvent发送为Json Object
//				Map<String, Object> event = (Map<String, Object>)events;
//				
//				Map<String, Object> eventAsMap = mergeMaps(dataMap, event);
//				standardStartTime(eventAsMap);
//				addEvent(eventsMap, eventAsMap);
//			}
//			
//		}else if(UtilData.Request.Type.ERROR.equals(requestType)){
//			//error record, ingnore it
//		}
//		
//		return eventsMap;
//	}
//
//	private static void addEvent(Map<String, Map<String, Object>> eventsMap, Map<String, Object> eventAsMap) {
//		//set bfuniq start, groupId + "-" + appId +"-" + eventId + "-" + uniqueId
//		String bfuniq;
//		if(eventAsMap.containsKey("uniqueId")){
//			Object groupId = eventAsMap.get("groupId");
//			Object appId = eventAsMap.get("appKey");
//			Object eventId = eventAsMap.get("id");
//			Object uniqueId = eventAsMap.get("uniqueId");
//			Object startTime = eventAsMap.get("startTime");
//			if(startTime instanceof Number){
//				String day = new SimpleDateFormat(UtilData.Value.DATE_FORMAT_DAY_SHORT).format(new Date(((Number)startTime).longValue()));
//				bfuniq = StringUtils.join(new Object[]{groupId, appId, eventId, day, uniqueId}, "-");
//			}else{
//				bfuniq = StringUtils.join(new Object[]{groupId, appId, eventId, uniqueId}, "-");
//			}
//		}else{
//			bfuniq = eventAsMap.get("uuid") + "-" + eventsMap.size();
//		}
//		eventsMap.put(MD5Hash.digest(bfuniq).toString(), eventAsMap);
//	}
//
//	private static Map<String, Object> convertInitProfileToEvent(
//			Map<String, Object> initProfileAsMap, Object startTime) {
//		Map<String, Object> exceptionEvent = new LinkedHashMap<String, Object>();
//		
//		exceptionEvent.put("id", INIT_EVENT_ID);
//		exceptionEvent.put("startTime", startTime);
//		exceptionEvent.put("parameters", initProfileAsMap);
//		
//		return exceptionEvent;
//	}
//
//	private static Map<String, Object> convertExceptionToEvent(Map<String, Object> exceptionAsMap) {
//		Map<String, Object> exceptionEvent = new LinkedHashMap<String, Object>();
//		
//		exceptionEvent.put("id", EXCEPTION_EVENT_ID);
//		exceptionEvent.put("startTime", exceptionAsMap.remove("errorTime"));
//		exceptionEvent.put("parameters", exceptionAsMap);
//		
//		return exceptionEvent;
//	}
//
//	private static void standardStartTime(Map<String, Object> eventAsMap) {
//		Object value = eventAsMap.get("startTime");
//		if(value == null){
//			//do nothing
//		}else if(value instanceof Number){
//			long ts = ((Number) value).longValue();
//			if(String.valueOf(ts).length() == 10){
//				//timestamp accuracy is second
//				eventAsMap.put("startTime", ts * 1000);
//			}
//		}else if(value instanceof String){
//			try {
//				long ts = Long.valueOf(value.toString());
//				if(String.valueOf(ts).length() == 10){
//					//timestamp accuracy is second
//					eventAsMap.put("startTime", ts * 1000);
//				}
//			} catch (NumberFormatException e) {
//				if(value.toString().length() == UtilData.Value.DATE_FORMAT_FULL.length()){
//					try {
//						Date date = new SimpleDateFormat(UtilData.Value.DATE_FORMAT_FULL).parse(value.toString());
//						eventAsMap.put("startTime", date.getTime());
//					} catch (ParseException e1) {
//						//do nothing
//					}
//				}
//				
//			}
//		}
//	}
//
//	private static void updateMapKey(Map<String, Object> map,
//			String oldKey, String newKey) {
//		if(map.containsKey(oldKey)){
//			map.put(newKey, map.remove(oldKey));
//		}
//	}
//
//	private static Map<String, Object> mergeMaps(Map<String, Object>... maps) {
//		Map<String, Object> mergedMap = new LinkedHashMap<String, Object>();
//		
//		for(Map<String, Object> map : maps){
//			mergedMap.putAll(map);
//		}
//		
//		return mergedMap;
//	}
//
//}
