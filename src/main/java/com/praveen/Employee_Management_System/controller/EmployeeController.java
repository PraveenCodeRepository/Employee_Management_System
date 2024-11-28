package com.praveen.Employee_Management_System.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.praveen.Employee_Management_System.dto.EmployeeDto;
import com.praveen.Employee_Management_System.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	private final EmployeeService  employeeService;

	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}


	@PostMapping("/saveEmployee")
	public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto){
		try {
		EmployeeDto employeeDtoCreated = employeeService.saveEmployee(employeeDto);
		 return new ResponseEntity<>(employeeDtoCreated , HttpStatus.CREATED);
		}
		catch(RuntimeException e) {
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/getEmployee/{id}")
	public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Integer id){
		try {
			EmployeeDto employeeDtoFound = employeeService.getEmployeeById(id);
			return new ResponseEntity<>(employeeDtoFound , HttpStatus.OK);
		}
		catch(RuntimeException e) {
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getEmployeeByNameOrDepartment")
	public ResponseEntity<EmployeeDto> getEmployeeByNameOrDepartmment(@RequestParam (required = false) String name ,
			@RequestParam (required = false) String department){
		try {
			EmployeeDto employeeDtoFound = employeeService.getEmployeeByNameOrDepartment(name, department);
			return new ResponseEntity<>(employeeDtoFound , HttpStatus.OK);
		}
		catch(RuntimeException e) {
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getAllEmployees")
	public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
		try {
			List<EmployeeDto>  employeeDtosFound = employeeService.getAllEmployees();
			return new ResponseEntity<>(employeeDtosFound , HttpStatus.OK);
		}
		catch(RuntimeException e) {
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/updateEmployee/{id}")
	public ResponseEntity<EmployeeDto> updateEmployeeById(@PathVariable Integer id , @RequestBody EmployeeDto employeeDto){
		try {
		EmployeeDto employeeDtoUpdated = employeeService.updateEmployeeById(id, employeeDto);
		 return new ResponseEntity<>(employeeDtoUpdated , HttpStatus.CREATED);
		}
		catch(RuntimeException e) {
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@DeleteMapping("/deleteEmployee/{id}")
	public ResponseEntity<EmployeeDto> deleteEmployeeById(@PathVariable Integer id){
		try {
			      employeeService.deleteEmployeeById(id);
			return new ResponseEntity<>( HttpStatus.NO_CONTENT);
		}
		catch(RuntimeException e) {
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		}
	}

	
	
	

}
