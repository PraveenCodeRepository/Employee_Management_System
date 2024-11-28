package com.praveen.Employee_Management_System.mapper;

import java.util.List;

import org.mapstruct.factory.Mappers;

import com.praveen.Employee_Management_System.dto.EmployeeDto;
import com.praveen.Employee_Management_System.entity.Employee;

@org.mapstruct.Mapper(componentModel="spring")
public interface EmployeeMapper {
	
	 EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);
	
    Employee toEmployeeEntity(EmployeeDto employeeDto);
    EmployeeDto toEmployeeDto(Employee employee);
    List<Employee> toEmployeeEntityList(List<EmployeeDto> employeeDtos);
    List<EmployeeDto> toEmployeeDtoList(List<Employee> employees);
    
  
    
}

