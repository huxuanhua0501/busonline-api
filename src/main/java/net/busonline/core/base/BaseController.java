package net.busonline.core.base;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import net.okdi.api.entity.BasCompInfo;
//import net.okdi.core.exception.ServiceException;
//import net.okdi.core.sms.AbstractHttpClient;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import net.busonline.core.exception.ServiceException;

public class BaseController {
	
//	@Override
//	public String parseResult(String info) {
//		return info;
//	}
	public static SerializerFeature[] s = {SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullBooleanAsFalse,SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteNullStringAsEmpty};
	public static SerializerFeature[] s1 = {SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteNullBooleanAsFalse,SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteNullStringAsEmpty};
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		binder.registerCustomEditor(java.sql.Timestamp.class, new CustomDateEditor(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss"), true));
	}

	protected String jsonSuccess(Object mapObject) {
		Map<String, Object> allMap = new HashMap<String, Object>();
		allMap.put("success", true);
		if (null != mapObject) {
			allMap.put("data", mapObject);
		}
		String result = JSON.toJSONString(mapObject,s).replaceAll(":null", ":\"\"");
		return result;
	}
	protected String jsonSuccess2(Object mapObject) {
		Map<String, Object> allMap = new HashMap<String, Object>();
		allMap.put("success", true);
		if (null != mapObject) {
			allMap.put("data", mapObject);
		}
		String result = JSON.toJSONString(allMap,s1).replaceAll(":null", ":\"\"");
		return result;
	}
	
	protected String jsonSuccess() {
		Map<String, Object> allMap = new HashMap<String, Object>();
		allMap.put("success", true);
		return JSON.toJSONString(allMap);
	}
	
	protected String jsonFailure() {
		Map<String, Object> allMap = new HashMap<String, Object>();
		allMap.put("success", false);
		return JSON.toJSONString(allMap);
	}
	
	protected String jsonFailure(Exception e) {
		e.printStackTrace();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		if(e instanceof ServiceException ){
			ServiceException temp=(ServiceException)e;
			map.put("errCode", temp.getErrCode());
			map.put("errSubCode", temp.getErrSubCode());
			map.put("message", temp.getMessage());
		}else{
			map.put("errCode", 1);
			map.put("errSubCode", "");
			map.put("message", e.initCause(e.getCause()).toString());
		}
		return JSON.toJSONString(map);
	}
	protected String paramsFailure() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		map.put("message", "parameter is not correct ");
		return JSON.toJSONString(map);
	}
	
	
	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>参数异常JSON</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xuanhua.hu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2016-7-9 上午11:54:17</dd>
	 * @param errSubcode
	 * @param message
	 * @return null
	 * @since v1.0
	*/
	protected String paramsFailure(String errSubcode,String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		map.put("errCode", 0);
		map.put("errSubcode",errSubcode);
		map.put("message",message);
		return JSON.toJSONString(map);
	}
}