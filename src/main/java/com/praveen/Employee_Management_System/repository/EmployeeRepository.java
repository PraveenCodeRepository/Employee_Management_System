package com.praveen.Employee_Management_System.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.praveen.Employee_Management_System.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	@Query("SELECT e FROM Employee e WHERE e.name = :name")
	Optional<Employee> searchEmployeeByName(@Param("name") String name);
	
	@Query("SELECT e FROM Employee e WHERE e.department = :department")
	Optional<Employee> searchEmployeeByDepartment(@Param("department") String department);
	

}
