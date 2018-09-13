package com.lyscharlie.biz.core.area.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lyscharlie.biz.core.area.bo.AreaQueryBO;
import com.lyscharlie.biz.core.area.dao.AreaDao;
import com.lyscharlie.biz.core.area.domain.AreaDO;
import com.lyscharlie.biz.core.area.service.AreaService;
import com.lyscharlie.common.dto.BatchResultDTO;

public class AreaServiceImpl implements AreaService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AreaDao areaDao;

	@Override
	public BatchResultDTO<AreaDO> queryAreaList(AreaQueryBO query) {
		BatchResultDTO<AreaDO> r = new BatchResultDTO<AreaDO>();

		try {
			List<AreaDO> list = this.areaDao.selectList(query);

			r.setModule(list);
			r.setSuccess(true);
		} catch (Exception e) {
			logger.error("查询地区失败", e);
			r.setSuccess(false);
			r.setErrorDetail("查询地区失败");
		}
		return r;
	}

}
