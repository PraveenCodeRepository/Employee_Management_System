package com.praveen.Employee_Management_System.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;
import com.praveen.Employee_Management_System.dto.EmployeeDto;
import com.praveen.Employee_Management_System.entity.Employee;
import com.praveen.Employee_Management_System.repository.EmployeeRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	private String baseUrl = "http://localhost";
	
	private static RestTemplate restTemplate;
	
	private EmployeeRepository employeeRepository;
	
	private EmployeeDto employeeDto1;
	
	private EmployeeDto employeeDto2;
	

	public EmployeeIntegrationTest(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}
	
	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}
	
	@BeforeEach
	public void setUp() {
		
	}
	
	@AfterEach
	public void tearDown() {
		employeeRepository.deleteAll();
	}

}
