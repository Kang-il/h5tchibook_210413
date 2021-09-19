package com.h5tchibook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.h5tchibook.user.bo.UserBO;
import com.h5tchibook.user.model.Sex;
import com.h5tchibook.user.model.User;


//단위테스트 Unit Test
//JUnit
@SpringBootTest
class H5tchibookApplicationTests {
	private Logger logger=LoggerFactory.getLogger(this.getClass()); 
	@Autowired
	private UserBO userBO;
	//@Test
	void contextLoads() {
		// 1, 2, 3, 4, 5+
		logger.debug("####### Hello World !!! #########");
		
		User user = userBO.getUserById(1);
		
		logger.debug("#########"+user.toString());
		
		//junit안에 포함되어있는
		//null이면 실행오류 //null이 아니면 실행 성공
		assertNotNull(user);
	}

	//@Test
	void 더하기테스트() {
		logger.debug("###############더하기 테스트 시작");
		int a = 10;
		int b = 20;
		
		assertEquals(30,sum(a,b));
	}
	
	int sum(int x , int y) {
		return x+y;
	}
	
	@Test
	@Transactional //rollback
	void addUserTest() {
		User user=User.builder().introduce("안녕").loginId("adfe").name("123").password("1234").sex(Sex.MALE).build();
		userBO.createUserAccount(user);
	}
}
