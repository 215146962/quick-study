package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

import tk.mybatis.spring.annotation.MapperScan;

//@ServletComponentScan是为了扫描session监听器(感觉没什么用)

@SpringBootApplication
@MapperScan(basePackages= {"com.example.demo.mapper"})
@ServletComponentScan
public class QuickStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickStudyApplication.class, args); 
	}
}
