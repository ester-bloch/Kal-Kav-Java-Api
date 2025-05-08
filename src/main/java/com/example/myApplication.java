package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class myApplication {

	public static void main(String[] args) {
		SpringApplication.run(myApplication.class, args);
	}

	// @Bean
	// public CourseConverter getCourseConverter() {
	// 	return new CourseConverter();
	// }

	// @Bean
	// public TeacherConverter getTeacherConverter() {
	// 	return new TeacherConverter();
	// }

	// @Bean
	// public StudentConverter getStudentConverter() {
	// 	return new StudentConverter();
	// }

}
