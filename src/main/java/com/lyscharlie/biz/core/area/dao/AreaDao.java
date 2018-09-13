package com.lyscharlie.biz.core.area.dao;

import java.util.List;

import com.lyscharlie.biz.core.area.bo.AreaQueryBO;
import com.lyscharlie.biz.core.area.domain.AreaDO;

public interface AreaDao {

	public List<AreaDO> selectAll();

	public List<AreaDO> selectList(AreaQueryBO query);

	public AreaDO selectById(Long areaId);

}
