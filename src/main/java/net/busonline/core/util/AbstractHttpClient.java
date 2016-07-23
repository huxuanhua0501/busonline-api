package net.busonline.core.util;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

public abstract class AbstractHttpClient<E> {
	@Autowired
	private CloseableHttpClient httpClient;
	private static Logger logger = Logger.getLogger(AbstractHttpClient.class);

	/**
	 * post请求
	 * 
	 * @param url请求的地址
	 * @param map参数map
	 * @return 结果
	 */
	public String Post(String url, Map<String, Object> map) {
		// 加入log
		logger.info("Post请求地址========" + url);
		logger.info("Post请求参数========" + JSON.toJSONString(map));

		// 插入url
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;

		// 封装参数
		List<NameValuePair> listParam = new ArrayList<NameValuePair>();
		if (map != null) {
			for (String key : map.keySet()) {
				listParam.add(new BasicNameValuePair(key,
						map.get(key) == null ? "" : map.get(key).toString()));
			}
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(listParam, "UTF-8"));
			response = httpClient.execute(httpPost);
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status <= 200) {
				HttpEntity entity = response.getEntity();
				String result = entity == null ? null : EntityUtils.toString(
						entity, "UTF-8");
				return result;
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
					EntityUtils.consume(response.getEntity());
				}
				httpPost.releaseConnection();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 读取数据
		// 获取相应

		return null;
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @param map
	 * @return 结果
	 */

	public String Get(String url, Map<String, Object> map) {
		logger.info("get请求地址======" + url);
		logger.info("get请求参数======" + JSON.toJSONString(map));
		url = this.buildGetParams(url, map);
//		System.out.println("url========="+url);
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 & status <= 300) {
				HttpEntity entity = response.getEntity();
				String result = entity != null ? EntityUtils.toString(entity)
						: null;
				return result;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public String buildGetParams(String url, Map<String, Object> map) {
		if (map == null || map.size() == 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (String key : map.keySet()) {
			String value = (String) map.get(key);
			this.concat(sb, key + "=" + value, "&");
		}
		return url+"?"+sb.toString();
	}

	private StringBuilder concat(StringBuilder sb, String addStr,
			String splitStr) {
		if (addStr == null || addStr.length() == 0) {
			return sb;
		}
		if (sb != null && sb.length() > 0 && addStr != null
				&& addStr.length() != 0) {
			sb.append(splitStr).append(addStr);
		} else {
			sb.append(addStr);
		}
		return sb;
	}

	public StringBuffer concat(StringBuffer srcStr, String addStr,
			String splitStr) {
		if (PubMethod.isEmpty(addStr)) {
			return srcStr;
		}
		if (!PubMethod.isEmpty(srcStr) && srcStr.length() > 0
				&& !PubMethod.isEmpty(addStr)) {
			srcStr.append(splitStr).append(addStr);
		} else {
			srcStr.append(addStr);
		}
		return srcStr;
	}

	public abstract E parseResult(String info);
	

}
