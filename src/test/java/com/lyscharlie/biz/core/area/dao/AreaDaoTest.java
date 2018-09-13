package com.lyscharlie.biz.core.area.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lyscharlie.biz.core.area.bo.AreaQueryBO;
import com.lyscharlie.biz.core.area.domain.AreaDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class AreaDaoTest {

	@Autowired
	private AreaDao areaDao;

	@Test
	public void testSelectAll() {
		List<AreaDO> list = this.areaDao.selectAll();

		assertTrue(CollectionUtils.isNotEmpty(list));
	}

	@Test
	public void testSelectList() {
		AreaQueryBO query = new AreaQueryBO();
		query.setParentId(1L);

		List<AreaDO> list = this.areaDao.selectList(query);

		assertTrue(CollectionUtils.isNotEmpty(list));
	}

	@Test
	public void testSelectById() {
		long areaId = 1L;

		AreaDO area = this.areaDao.selectById(areaId);

		assertTrue(null != area);
	}

}
