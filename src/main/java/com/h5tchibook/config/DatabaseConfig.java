package com.h5tchibook.config;

import javax.sql.DataSource;	

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.h5tchibook.alert.model.AlertType;
import com.h5tchibook.post.model.ContentType;
import com.h5tchibook.post.model.DisclosureStatus;
import com.h5tchibook.user.model.Sex;

@MapperScan(basePackages = "com.h5tchibook")
@Configuration
public class DatabaseConfig {
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		
		//myBatis 설정 시 SqlSessionFactoryBean 클래스에 TypeHandler 등록이 가능함
		sessionFactory.setTypeHandlers(new TypeHandler[] { new Sex.TypeHandler()
														  ,new ContentType.TypeHandler()
														  ,new DisclosureStatus.TypeHandler()
														  ,new AlertType.TypeHandler()
														 }
		);
		
		Resource[] res=new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*Mapper.xml");
		sessionFactory.setMapperLocations(res);
		
		return sessionFactory.getObject();
	}
}
