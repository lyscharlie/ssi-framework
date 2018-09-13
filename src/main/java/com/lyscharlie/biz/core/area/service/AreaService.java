package com.lyscharlie.biz.core.area.service;

import com.lyscharlie.biz.core.area.bo.AreaQueryBO;
import com.lyscharlie.biz.core.area.domain.AreaDO;
import com.lyscharlie.common.dto.BatchResultDTO;

public interface AreaService {

	/**
	 * 查询地区
	 * 
	 * @param query
	 * @return
	 */
	public BatchResultDTO<AreaDO> queryAreaList(AreaQueryBO query);

}
