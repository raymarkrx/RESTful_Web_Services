package com.aichuche.servlet.chh;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.ObjectMapper;

import com.aichuche.util.UtilData;
import com.chh.utils.DbUtils;
import com.chh.utils.JSONUtils;

public class TestServlet extends HttpServlet {
	final Log log = LogFactory.getLog(getClass());

	private static final long serialVersionUID = 3624858948204016394L;
	private static final int BUFFER_SIZE = 1024;
	private static String logPath = "logs";
	private static Object obj = new Object();
	private static String CHARSET_UTF8 = "UTF-8";
	private static ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
	private static String yyyyMMdd = "[0-9]{8}";
	static{
		mapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
		mapper.configure(Feature.ALLOW_COMMENTS, true);
		mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		//允许出现特殊字符和转义符
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true) ;
		//允许出现单引号
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true) ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		/*post方式，开始
		InputStream in = request.getInputStream();

		String ce = request.getHeader("Content-Encoding");
		if (ce != null && ce.indexOf("gzip")>=0){
			in = new GZIPInputStream(in);
		}
		
		String requestEncoding = request.getParameter("charset");
		
		if(requestEncoding == null){
			requestEncoding = request.getCharacterEncoding();
		}
		if(requestEncoding == null || !Charset.isSupported(requestEncoding)){
			requestEncoding = CHARSET_UTF8;
		}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream(); 
		byte[] buf = new byte[BUFFER_SIZE];
		int len;
		//out从request中得到字节流，然后封装得到值
		while ((len = in.read(buf)) != -1) {
			// process byte buffer
			out.write(buf, 0, len);
		}
		
		byte[] bytes = out.toByteArray();
		String postMesg=new String(bytes); //最后得到postmesg
		*///post方式，结束
		
		//get方式，开始
		String  who = request.getParameter("who") ;
		String  data = request.getParameter("data") ;

		Map<String, Object> result = new HashMap<String, Object>();
		long time = System.currentTimeMillis();
    	result.put("return_code", 0);
    	result.put("who", "you are "+who);
		result.put("data","you send me :"+data);
		result.put("date", time);
	
		response.getOutputStream().write(JSONUtils.toJSONString(result).getBytes(CHARSET_UTF8));
		
	}
	
	private PrintStream getErrorStream() throws IOException {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		File logFoloder = new File(logPath);
		
		if(!logFoloder.exists()){
			logFoloder.mkdirs();
		}
		
		File file = new File(logFoloder, "/err_json_" + df.format(new Date()));
		
		if(!file.exists()){
			file.createNewFile();
		}
		
		return new PrintStream(new FileOutputStream(file, true), false);
	}
	
}
