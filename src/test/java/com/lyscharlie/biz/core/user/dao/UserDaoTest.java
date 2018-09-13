package com.lyscharlie.biz.core.user.dao;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lyscharlie.biz.core.user.domain.UserDO;
import com.lyscharlie.common.utils.EncryptUtils;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class UserDaoTest extends TestCase {

	@Autowired
	private UserDao userDao;

	@Test
	public void testInsertUser() {
		UserDO user = new UserDO();
		user.setUserName("lyscharlie_" + new Date().getTime());
		user.setPassword(EncryptUtils.encodeMD5("123456"));
		user.setEmail("lyscharlie@hotmail.com");

		int num = this.userDao.insertUser(user);

		System.out.println(user.getUserId());

		assertTrue(num > 0 && user.getUserId() > 0);
	}

	@Test
	public void testSelectUserById() {
		long userId = 1L;

		UserDO user = this.userDao.selectUserById(userId);

		assertTrue(null != user);
	}

	@Test
	public void testSelectUserByName() {
		String userName = "lyscharlie";

		UserDO user = this.userDao.selectUserByName(userName);

		assertTrue(null != user);
	}

	@Test
	public void testUpdateUser() {
		UserDO user = new UserDO();
		user.setUserId(1L);
		user.setUserName("lyscharlie");
		user.setEmail("lyscharlie@gmail.com");
		user.setMobile("13512345678");
		
		int num = this.userDao.updateUser(user);
		
		assertTrue(num > 0);
	}

	@Test
	public void testUpdateLastLoginTime() {
		long userId = 1L;
		
		int num = this.userDao.updateLastLoginTime(userId);
		
		assertTrue(num > 0);
	}

	@Test
	public void testUpdatePassword() {
		long userId = 1L;
		String password = EncryptUtils.encodeMD5("hello123456");
		
		int num = this.userDao.updatePassword(userId, password);
		
		assertTrue(num > 0);
	}

}
