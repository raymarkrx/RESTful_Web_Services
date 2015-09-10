package com.redis;

public final class Constants {
	public final static String START_BEGIN_SUCCESS_KEY = "startBeginFlag";

	public final static String START_END_SUCCESS_KEY = "startEndFlag";

	public final static String UNSUCCESS = "unsuccess";
	public final static String SUCCESS = "success";

	public final static String DATA_TIME = "dataTime";
	
	public final static String KEY_DRIVER_STATUS = "driverStatus";
	public final static String RESULT = "result";
	
	public final static String KEY_DAISU__START_SUFFIX= "daisuStart";//怠速起步开始的缓存后缀
	public final static String DAISU__START_WAIN_MESG= "怠速起步开始_超速";
	public final static String DAISU__END_WAIN_MESG= "怠速起步结束_超速";
	public final static String DAISU__START_WAIN_TYPE= "101";
	public final static String DAISU__END_WAIN_TYPE= "102";
	
	//车辆的实时状态
	public final static String STOP = "1";//停止
	public final static String DAISU_START = "2";//怠速起步开始
	public final static String DAISU_END = "3";//怠速起步结束
	
	//车辆的实时状态细分后的状态
	// 10 停止 20 怠速起步开始 30 怠速起步结束 40 行驶 41 转弯 42 加速 43 减速 44变道 50 刹车
    // 用两位来表示， 主要考虑以后会有小的状态， 类似（40 行驶 41 转弯 42 加速 43 减速 ）
	
	public final static String DAISU_START_1 = "201";// 怠速起步_急起步(起步超速)
	
}
