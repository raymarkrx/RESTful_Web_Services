package com.aichuche.util;
//package com.bianfeng.bfas.bfrd.util;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.regex.Matcher;
//
//import com.bianfeng.bfas.bfrd.evaluator.AbstractEvaluator;
//import com.bianfeng.bfas.bfrd.evaluator.Evaluator;
//import com.bianfeng.bfas.bfrd.evaluator.Expression;
//import com.bianfeng.bfas.bfrd.evaluator.OutputFormatter;
//
///**
// * <pre>
// * providing the method to evaluate the simple expression
// * 
// * 1. check the expression match
// * 
// * e.g. [M3] { k = 1 and M2.k > 3 }
// * 
// * 2. retrieve the value
// * e.g. M3.k|int or k|int
// * 
// * </pre>
// * 
// * @author Peng Peng
// *
// */
//public class ExpressionEvaluator implements Evaluator {
//	
//	private Map<String, Evaluator> evaluatorMap = new ConcurrentHashMap<String, Evaluator>();
//	private Map<String, Expression> expressionMap = new ConcurrentHashMap<String, Expression>();
//	
//	public String guessLayer(String expression){
//		String layer = "";
//		
//		if(expression != null) {
//			Matcher matcher = AbstractEvaluator.EXPRESSION_MATCH_REGEX.matcher(expression);
//			
//			if(matcher.find()) {
//				layer = matcher.group(1);
//			}
//		}
//		
//		return layer;
//	}
//	
//	/**
//	 * <pre>
//	 * retrieve object value by path
//	 * 
//	 * x.y.z|(int|float|long|double|boolean|string)
//	 * x eq y ? a : (c > d ? e : f|int,string) |int
//	 * 
//	 * z,a|func
//	 * 
//	 * ?val#scope1start,scope1end:scope1val;scope2start,scope2end:scope2val;val3:val3value;default:val4|func
//	 * 
//	 * </pre>
//	 * 
//	 * @param valuePath
//	 * @param ms
//	 * @return
//	 */
//	public Object retrieveValue(String valuePath, Object... ms) {
//		
//		Evaluator evaluator = evaluatorMap.get(valuePath);
//		if(evaluator == null) {
//			evaluator = AbstractEvaluator.parseEvaluator(valuePath);
//			evaluatorMap.put(valuePath, evaluator);
//		}
//		
//		return evaluator.eval(ms);
//	}
//	
//	public boolean match(String condition, Object... ms) {
//		Expression expression = expressionMap.get(condition);
//		if(expression == null) {
//			expression = new Expression(condition);
//			expressionMap.put(condition, expression);
//		}
//		return expression.match(ms);
//	}
//	
//	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
//		
//		ExpressionEvaluator ee = new ExpressionEvaluator();
//		
//		String expression = "[M2]{id = 52 and ((M1.serverType = 0 and M1.clientType != 14) or (M1.clientType = 0 and M1.serverType != 14))}";
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("id", "52");
//		map.put("serverType", "13");
//		Map<String, Object> map1 = new HashMap<String, Object>();
//		
//		map1.put("m2G_3G", "abcd");
//		map1.put("value", 2);
//		map1.put("sellPrice", "11.113");
//		map1.put("count", 4);
//		map1.put("areaName", "一区");
//		map1.put("roomName", "一放");
//		map1.put("type", "7");
//		map1.put("appVersionCode2", "7");
//		
//		
//		map.put("deviceProfile", map1);
//		map.put("parameters", map1);
//		map.put("receiveTimestamp", System.currentTimeMillis());
//		map.put("startTime", new Date(System.currentTimeMillis() / 1000));
//		map.put("deviceVersionCode", "deviceVersionCode");
//		
//		map.put("appProfile", map1);
//		
//		System.out.println( System.currentTimeMillis());
//		
//		boolean result = ee.match(expression, map, map);
//		
//		System.out.println(result);
//		
//		//String valuePath = "M1.clientType in ('4', '2', '3', '11') ? (M1.receiveTimestamp) : (M2.startTime)|hour";
//		
//		String valuePath = "M1.appProfile.appVersionCode == \'\' ? (M2.appProfile.appVersionCode == \'\' ? (M2.deviceVersionCode == \'\' ? M1.appVersionCode : M2.deviceVersionCode) : (M2.appProfile.appVersionCode)) : (M1.appProfile.appVersionCode)|string";
//		                    
//		
//		Object value = null;
//		
//		long startTs = System.nanoTime();
//		
//		for(int i = 0; i < 1000000; i++) {
//			value =	ee.retrieveValue(valuePath , map, map);
//		}
//		
//		long endTs = System.nanoTime();
//		
//		System.out.println(": ts1  : " + (endTs - startTs));
//		
//		System.out.println("ts1 rst: " + value + ": " + (value == null ? null : value.getClass()));
//		
//		valuePath = "M2.parameters.type == \'\' ? 0 : M2.parameters.type";
//		
//		startTs = System.nanoTime();
//		
//		for(int i = 0; i < 1000000; i++) {
//			value =	ee.retrieveValue(valuePath , map, map);
//		}
//		
//		endTs = System.nanoTime();
//		
//		System.out.println(": ts2  : " + (endTs - startTs));
//		
//		System.out.println("ts1 rst: " + value + ": " + (value == null ? null : value.getClass()));
//	}
//
//	@Override
//	public Object eval(Object[] ms) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void setOutputFormatter(OutputFormatter outputFormatter) {
//		// TODO Auto-generated method stub
//		
//	}
//}
