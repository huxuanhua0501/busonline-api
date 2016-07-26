package net.busonline.api.service;

import java.util.List;
import java.util.Map;

public interface IBusApiService {
	public List<Map<String, Object>> getLine(String city);
	public String lineCity(String city,String data) ;
	public Map<String,Object> lineStop(String city,String data);
}
