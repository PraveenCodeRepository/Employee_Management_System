package com.praveen.Employee_Management_System.controller;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.praveen.Employee_Management_System.dto.EmployeeDto;
import com.praveen.Employee_Management_System.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(EmployeeController.class)
@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

	@MockitoBean
	private EmployeeService employeeService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private EmployeeDto employeeDto1;

	private EmployeeDto employeeDto2;

	@BeforeEach
	void setUp() {
		employeeDto1 = new EmployeeDto();
		employeeDto1.setId(1);
		employeeDto1.setName("Praveen");
		employeeDto1.setSalary(66000.00);
		employeeDto1.setMobileNo(1L);

		employeeDto2 = new EmployeeDto();
		employeeDto2.setId(2);
		employeeDto2.setName("Kumar");
		employeeDto2.setSalary(62000.00);
		employeeDto2.setMobileNo(2L);
	}

	@Test
	@DisplayName("Should save employee in database")
	void testSaveEmployee() throws Exception {
		// Mocking the service method
		when(employeeService.saveEmployee(any(EmployeeDto.class))).thenReturn(employeeDto1);

		// Performing the mock request
		this.mockMvc
				.perform(post("/employee/saveEmployee")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(employeeDto1)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(employeeDto1.getId())))
				.andExpect(jsonPath("$.name", is(employeeDto1.getName())))
				.andExpect(jsonPath("$.mobileNo", is(1)))
				.andExpect(jsonPath("$.salary", is(employeeDto1.getSalary())))
				.andDo(print());
	}

	@Test
	@DisplayName("Should fetch one employee by id")
	void testGetEmployeeById() throws Exception {

		when(employeeService.getEmployeeById(anyInt())).thenReturn(employeeDto1);

		this.mockMvc
		    .perform(get("/employee/getEmployee/{id}", 1))
		    .andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(employeeDto1.getId())))
			.andExpect(jsonPath("$.name", is(employeeDto1.getName())))
			.andExpect(jsonPath("$.mobileNo", is(1)))
			.andExpect(jsonPath("$.salary", is(employeeDto1.getSalary())))
			.andDo(print());
	}
    
	@Test
	@DisplayName("Should fetch all employees list")
	void tesyGetAllEmployess() throws Exception {

		List<EmployeeDto> list = new ArrayList<EmployeeDto>();
		                 list.add(employeeDto1);
		                 list.add(employeeDto2);

		when(employeeService.getAllEmployees()).thenReturn(list);

		this.mockMvc.perform(get("/employee/getAllEmployees"))
		            .andExpect(status().isOk())
				    .andExpect(jsonPath("$.size()", is(list.size())))
				    .andDo(print());

	}
	
	@Test
	@DisplayName("Should update employee in database")
	void testUpdateEmployee() throws Exception {
	 
	    employeeDto2.setName("Ramesh");  
	    employeeDto2.setSalary(67000.00); 
	    
	    when(employeeService.updateEmployeeById(anyInt(), any(EmployeeDto.class))).thenReturn(employeeDto2);

	    this.mockMvc
	        .perform(put("/employee/updateEmployee/{id}", 2)  
	        .contentType(MediaType.APPLICATION_JSON)    
	        .content(objectMapper.writeValueAsString(employeeDto2))) 
	        .andExpect(status().isOk()) 
	        .andExpect(jsonPath("$.id", is(employeeDto2.getId()))) 
	        .andExpect(jsonPath("$.name", is(employeeDto2.getName()))) 
	        .andExpect(jsonPath("$.mobileNo", is(2))) 
	        .andExpect(jsonPath("$.salary", is(employeeDto2.getSalary()))) 
	        .andDo(print()); 
	}
	
	@Test
	@DisplayName("Should delete employee in database")
	void testDeleteEmployee() throws Exception {
		
		doNothing().when(employeeService).deleteEmployeeById(anyInt());
		           
		this.mockMvc
		    .perform(delete("/employee/deleteEmployee/{id}" , 1))
		    .andExpect(status().isNoContent())
		    .andDo(print());
	}
	
	@AfterEach
	void tearDown() {
		
		employeeDto1=null;
		employeeDto2=null;
		Mockito.reset(employeeService);
	}


}
