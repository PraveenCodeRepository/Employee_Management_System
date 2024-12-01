package com.praveen.Employee_Management_System.service;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.praveen.Employee_Management_System.dto.EmployeeDto;
import com.praveen.Employee_Management_System.entity.Employee;
import com.praveen.Employee_Management_System.mapper.EmployeeMapper;
import com.praveen.Employee_Management_System.repository.EmployeeRepository;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;

    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;
  
    
    @Mock
    private Employee employee;

    @BeforeEach
    void setUp() {
    	
    	MockitoAnnotations.openMocks(this);
    	
        // Initialize EmployeeDto
        employeeDto1 = new EmployeeDto();
        employeeDto1.setId(1);
        employeeDto1.setName("Praveen");
        employeeDto1.setSalary(66000.00);
        employeeDto1.setMobileNo(1234567890L);

        employeeDto2 = new EmployeeDto();
        employeeDto2.setId(1);
        employeeDto2.setName("Kumar");
        employeeDto2.setSalary(70000.00);
        employeeDto2.setMobileNo(1234567899L);

       
    }

    @Test
    @DisplayName("Should save employee in database")
    void testSaveEmployee() {
    	
        // Arrange
        when(employeeMapper.toEmployeeEntity(any(EmployeeDto.class))).thenReturn(employee); 
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);  
        when(employeeMapper.toEmployeeDto(employee)).thenReturn(employeeDto1);  

        // Act
        EmployeeDto newEmployeeDto = employeeService.saveEmployee(employeeDto1);

        // Assert
        assertNotNull(newEmployeeDto);
        assertThat(newEmployeeDto.getName()).isEqualTo("Praveen");
        assertEquals(newEmployeeDto.getId(), employeeDto1.getId());
        assertEquals(newEmployeeDto.getSalary(), employeeDto1.getSalary());
        assertEquals(newEmployeeDto.getMobileNo(), employeeDto1.getMobileNo());
        assertSame(newEmployeeDto , employeeDto1);

    
    }
    
    @Test
    @DisplayName("Should throw exception when EmployeeDto is null")
    void testSaveEmployee_NullValues() {
    	
    	//Arrange
    	when(employeeRepository.save(any(Employee.class))).thenReturn(new Employee());

    	//Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            employeeService.saveEmployee(null);
        });
        
        //Assert
        assertEquals("EmployeeDto is null", exception.getMessage());
    }
    
    @Test
    @DisplayName("Should throw exception when Employee already exists")
    void testSaveEmployee_AlreadyExists() {
    	
    	//Arrange
        when(employeeRepository.searchEmployeeByMobileNumber(employeeDto1.getMobileNo()))
                .thenReturn(Optional.of(employee)); 
        //Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            employeeService.saveEmployee(employeeDto1); 
        });
        
        //Assert
        assertEquals("Employee is already exists in database", exception.getMessage());
    }
	 
	  @Test
	  
	  @DisplayName("Should fetch one employee by id")
	  void testGetEmployeeById() {
		  
	  //Arrange
	  when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee)); //stub repo 
	  when(employeeMapper.toEmployeeDto(any(Employee.class))).thenReturn(employeeDto1);//stub mapper 
	 
	  //Act 
	  EmployeeDto employeeDtoFound = employeeService.getEmployeeById(1);
	  
	  //Assert 
	  assertEquals(employeeDtoFound.getName() , employeeDto1.getName());
	  assertEquals(employeeDtoFound.getMobileNo(),employeeDto1.getMobileNo());
	  assertSame(employeeDtoFound , employeeDto1);
	  
	  }
	   
	  
	  @Test
	  @DisplayName("Should fetch all employees")
	  void testGetAllEmployees() {
	  
	  List<EmployeeDto> dtoList = new ArrayList();
	  dtoList.add(employeeDto1);
	  dtoList.add(employeeDto2);
	  
	  when(employeeRepository.findAll()).thenReturn(List.of(employee));
	  when(employeeMapper.toEmployeeDtoList(List.of(employee))).thenReturn(dtoList);
	  
	  List<EmployeeDto> employeeDtos = employeeService.getAllEmployees();
	  
	  assertNotNull(employeeDtos);
	  assertThat(employeeDtos.size()).isEqualTo(2);
	  assertSame(dtoList , employeeDtos);
	  
	  
	  }
	  
	    @Test
	    @DisplayName("Should update employee in database")
	    void testUpdateEmployeeById_EmployeeFound() {
		  
		     //set up the data to update
		    employeeDto1.setName("Ramesh");
	        employeeDto1.setMobileNo(9841332495L);
	        
	        
	        //Arrange
	        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));
	        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
	        when(employeeMapper.toEmployeeDto(any(Employee.class))).thenReturn(employeeDto1);
	        
	        // Act
	        EmployeeDto employeeDtoSaved = employeeService.updateEmployeeById(1, employeeDto1);
	        
	        // Assert
	        assertNotNull(employeeDtoSaved);
	        //assertEquals("Ramesh", employeeDtoSaved.getName());
	       // assertEquals(9841332495L, employeeDtoSaved.getMobileNo());
	        assertEquals(employeeDtoSaved.getName(), employeeDto1.getName());
	        assertEquals(employeeDtoSaved.getMobileNo(), employeeDto1.getMobileNo());
	    }
	  
	  @Test
	    @DisplayName("Should throw run time exception for wrong id to update")
	    void testUpdateEmployeeById_EmployeeNotFound() {
	    	
		   // Arrange
	       when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());  
		   
	       //Act
	    	RuntimeException exception =  assertThrows(RuntimeException.class , ()->{
	    		  employeeService.updateEmployeeById(3, employeeDto1);
	    	  });
	    	
	    	//Assert
	    	assertEquals("Employee not found with id : " + 3 , exception.getMessage());
	    	
	    }
	  
	    @Test
	    @DisplayName("Should delete employee in database for employee found ")
	    void testDeleteEmployeeById_EmployeeFound() {
		  
	        // Arrange
	    	 when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));
	        
	        // Act
	        employeeService.deleteEmployeeById(1);
	        
	        // Assert
	         Optional<Employee> deletedEmployee = employeeRepository.findById(1);
	         assertFalse(deletedEmployee.isEmpty());
	    }
	    
	    @Test
	    @DisplayName("Should throw run time exception for wrong id given to delete")
	    void testDeleteEmployeeById_EmployeeNotFound() {
	    	
	        // Arrange
	        when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());  
	          
	        // Act 
	        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
	            employeeService.deleteEmployeeById(3); 
	        });
	        
	        //Assert
	        assertEquals("Employee is not found with id : "+3 ,thrown.getMessage() );
	    }

    	
    	 
			                              
			                          
}

    
  

