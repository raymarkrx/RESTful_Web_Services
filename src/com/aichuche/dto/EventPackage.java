package com.aichuche.dto;
//package com.bianfeng.bfas.bfrd.dto;
//
//import java.io.Serializable;
//import java.util.Arrays;
//import java.util.List;
//
//import org.msgpack.annotation.Message;
//
///**
// * 
// * @author ShaoHongLiang
// * @date 2013-8-11
// */
//@Message
//public class EventPackage implements Serializable{
//
//	private static final long serialVersionUID = 8833969328294082691L;
//
//	/**
//	 * 设备id
//	 * <p/>
//	 * 1.可以取到IMEI，devId = "IMEI-" + md5(IMEI)�?
//	 * </p>
//	 * 2.不能取到IMEI，但能取到WIFI的MAC，devId = "MAC-" + md5(MAC)�?
//	 * <p/>
//	 * 3.IMEI和wifi MAC都取不到，devId = new UUID()�?
//	 */	
//	private String deviceId;
//	
//	/**
//	 * �?发�?�的App key
//	 */
//	private String deveploperAppkey;
//	
//	/**
//	 * 应用的Profile
//	 */
//	private AppProfile  appProfile;
//	
//	/**
//	 * Server要求每次交互都上传的信息
//	 */
//	private DeviceProfile deviceProfile;
//	/**
//	 * 事件List
//	 */
//	private List<TMessage> messages;
//	
//	private Long[][] activeApps;
//	
//	public String getDeviceId() {
//		return deviceId;
//	}
//
//	public void setDeviceId(String deviceId) {
//		this.deviceId = deviceId;
//	}
//
//	public String getDeveploperAppkey() {
//		return deveploperAppkey;
//	}
//
//	public void setDeveploperAppkey(String deveploperAppkey) {
//		this.deveploperAppkey = deveploperAppkey;
//	}
//
//	public AppProfile getAppProfile() {
//		return appProfile;
//	}
//
//	public void setAppProfile(AppProfile appProfile) {
//		this.appProfile = appProfile;
//	}
//
//	public DeviceProfile getDeviceProfile() {
//		return deviceProfile;
//	}
//
//	public void setDeviceProfile(DeviceProfile deviceProfile) {
//		this.deviceProfile = deviceProfile;
//	}
//
//	public List<TMessage> getMessages() {
//		return messages;
//	}
//
//	public void setMessages(List<TMessage> messages) {
//		this.messages = messages;
//	}
//
//	public Long[][] getActiveApps() {
//		return activeApps;
//	}
//
//	public void setActiveApps(Long[][] activeApps) {
//		this.activeApps = activeApps;
//	}
//
//	@Override
//	public String toString() {
//		return "EventPackage [deviceId=" + deviceId + ", deveploperAppkey="
//				+ deveploperAppkey + ", appProfile=" + appProfile
//				+ ", deviceProfile=" + deviceProfile + ", messages="
//				+ messages + ", activeApps=" + Arrays.toString(activeApps)
//				+ "]";
//	}
//	
//
//}
