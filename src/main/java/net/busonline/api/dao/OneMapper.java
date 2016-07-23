package net.busonline.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OneMapper {
	public List<Map<String, Object>> getOneById(@Param("id") String id);
}
