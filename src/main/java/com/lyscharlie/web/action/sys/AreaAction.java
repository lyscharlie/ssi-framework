package com.lyscharlie.web.action.sys;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lyscharlie.biz.core.area.bo.AreaQueryBO;
import com.lyscharlie.biz.core.area.domain.AreaDO;
import com.lyscharlie.biz.core.area.service.AreaService;
import com.lyscharlie.common.bo.ActionResultBO;
import com.lyscharlie.common.dto.BatchResultDTO;
import com.lyscharlie.web.action.BaseAction;
import com.lyscharlie.web.vo.AreaVO;

public class AreaAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1056420117484237212L;

	@Autowired
	private AreaService areaService;

	/**
	 * 获取地区树结构
	 */
	public void queryAreaTree() {
		ActionResultBO result = new ActionResultBO();

		AreaQueryBO query = new AreaQueryBO();
		query.setStatus(1);

		BatchResultDTO<AreaDO> r = this.areaService.queryAreaList(query);

		if (r.isSuccess()) {
			if (CollectionUtils.isNotEmpty(r.getModule())) {

				List<AreaVO> list = new ArrayList<AreaVO>();
				for (AreaDO areaDO : r.getModule()) {
					AreaVO vo = new AreaVO();
					vo.setId(areaDO.getAreaId());
					vo.setName(areaDO.getAreaName());
					vo.setParentId(areaDO.getParentId());
					list.add(vo);
				}

				AreaVO root = new AreaVO();
				root.setId(1L);
				root.setParentId(0L);

				createTree(root, list);

				result.setSuccess(true);
				result.setDataObject(root.getChildren());
			} else {
				result.setSuccess(false);
				result.setMsg("无数据");
			}
		} else {
			result.setSuccess(false);
			result.setMsg(r.getErrorDetail());
		}

		responseJson(result);
	}

	/**
	 * 生成树
	 * 
	 * @param parent
	 * @param list
	 */
	private void createTree(AreaVO parent, List<AreaVO> list) {
		for (AreaVO area : list) {
			if (parent.getId().equals(area.getParentId())) {
				parent.addChild(area);
				createTree(area, list);
			}
		}
	}
}
