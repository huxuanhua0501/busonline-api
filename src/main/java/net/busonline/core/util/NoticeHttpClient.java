package net.busonline.core.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NoticeHttpClient extends AbstractHttpClient<String> {
	@Value("${busonline_url}")
	private String busonline_url;

	@Override
	public String parseResult(String info) {
		// TODO Auto-generated method stub
		return info;
	}

	public String PosttoOpen(String url, Map<String, Object> map) {
		return this.Post( url, map);
	}

	public String GettoOpen(String url, Map<String, Object> map) {
		return this.Get(url, map);
	}

}
