package net.busonline.api.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Scope;
@Scope("prototype")
public interface SignMapper {

	public List<Map<String,String>> getAllSign();
	public List<Map<String,Object>> city(@Param("city")String city);
	public String lineCity(@Param("city")String city,@Param("data")String data) ;
	public List<Map<String,Object>> lineStop(@Param("data")String[] data);
	public List<Map<String,Object>> getLine(@Param("city")String city);
	public List<Map<String,Object>> getLineByCityAndLine(@Param("city")String city,@Param("data")String[] data);
}

