package com.praveen.Employee_Management_System.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.praveen.Employee_Management_System.dto.EmployeeDto;
import com.praveen.Employee_Management_System.entity.Employee;
import com.praveen.Employee_Management_System.mapper.EmployeeMapper;
import com.praveen.Employee_Management_System.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	 private  final EmployeeRepository employeeRepository;
	
	  private final EmployeeMapper  employeeMapper;
	
	  public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper
	  employeeMapper) { this.employeeRepository = employeeRepository;
	  this.employeeMapper = employeeMapper; }
	  
	  
	 

	public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
    	  
    	  if(employeeDto == null) throw new NullPointerException("EmployeeDto is null");
    	  
    	   Employee employee = employeeMapper.toEmployeeEntity(employeeDto);
    	   
    	   Employee employeeSaved = employeeRepository.save(employee);
    	   
            	 return  employeeMapper.toEmployeeDto(employeeSaved);
    	
    }
	
	public EmployeeDto getEmployeeById(Integer id) {
		
		  Optional<Employee> optionalEmployee =employeeRepository.findById(id);
		  
		       if(optionalEmployee.isPresent()) {
		    	   
		    	  Employee employeeFound = optionalEmployee.get();
		    	  
		    	  return employeeMapper.toEmployeeDto(employeeFound);
		       }
		       
		       else throw new RuntimeException("Employee not found with id : "+id);
		
	}
	
	public EmployeeDto getEmployeeByNameOrDepartment(String name , String department) {
		
		            Optional<Employee> optionalEmployee;
		           
		            if(name!=null) 
		            	 optionalEmployee =employeeRepository.searchEmployeeByName(name);
		            
		            else if(department!=null) 
		            	optionalEmployee =employeeRepository.searchEmployeeByDepartment(department);
		            
		            else throw new RuntimeException("Name or department is null");
		  
		            if(optionalEmployee.isPresent()) {
		    	   
		    	  Employee employeeFound = optionalEmployee.get();
		    	  
		    	  return employeeMapper.toEmployeeDto(employeeFound);
		       }
		       
		       else throw new RuntimeException("Employee not found by name : "+name+ "and  department : "+department );
		
	}
	
	public List<EmployeeDto> getAllEmployees(){
		
		List<Employee> employees = employeeRepository.findAll();
		
		return employeeMapper.toEmployeeDtoList(employees);
		
	}
	
	public EmployeeDto updateEmployeeById(Integer id, EmployeeDto employeeDto) {
	    
	    Employee existingEmployee = employeeRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Employee not found"));

	    
	    if (employeeDto.getName() != null) {
	        existingEmployee.setName(employeeDto.getName());
	    }
	    if (employeeDto.getSalary() != null) {
	        existingEmployee.setSalary(employeeDto.getSalary());
	    }
	    if (employeeDto.getDepartment() != null) {
	        existingEmployee.setDepartment(employeeDto.getDepartment());
	    }

	    
	    Employee updatedEmployee = employeeRepository.save(existingEmployee);

	    return employeeMapper.toEmployeeDto(updatedEmployee);
	}

		   
		   public void deleteEmployeeById(Integer id) {
			   
			 Optional<Employee> optionalEmployee = employeeRepository.findById(id);
			 
			      if(optionalEmployee.isPresent()) {
			    	  
			    	 Employee employeeFound = optionalEmployee.get();
			    	 
			    	 employeeRepository.delete(employeeFound);
			      }
			      
			      else throw new RuntimeException("Employee is not found with id : "+id);
			      
			    
		   }
	

	}
