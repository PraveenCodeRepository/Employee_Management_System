package com.praveen.Employee_Management_System.repository;

import com.praveen.Employee_Management_System.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee1;

    @BeforeEach
    void setUp() {
        // Set up test data before each test
        employee1 = new Employee();
        employee1.setName("Praveen");
        employee1.setSalary(66000.00);
        employee1.setMobileNo(1234567890L);

        // Save employee to the in-memory database
        employeeRepository.save(employee1);
    }

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    void searchEmployeeByName_Found() {
    	
        // Act
        Optional<Employee> foundEmployee = employeeRepository.searchEmployeeByName("Praveen");

        // Assert
        assertTrue(foundEmployee.isPresent());
        assertEquals("Praveen", foundEmployee.get().getName());
        assertEquals(66000.00, foundEmployee.get().getSalary());
    }

    @Test
    void searchEmployeeByName_NotFound() {
    	
        // Act
        Optional<Employee> foundEmployee = employeeRepository.searchEmployeeByName("NonExistingName");

        // Assert
        assertFalse(foundEmployee.isPresent());
    }

    @Test
    void searchEmployeeByMobileNumber_Found() {
    	
        // Act
        Optional<Employee> foundEmployee = employeeRepository.searchEmployeeByMobileNumber(1234567890L);

        // Assert
        assertTrue(foundEmployee.isPresent());
        assertEquals("Praveen", foundEmployee.get().getName());
        assertEquals(66000.00, foundEmployee.get().getSalary());
    }

    @Test
    void searchEmployeeByMobileNumber_NotFound() {
        // Act
        Optional<Employee> foundEmployee = employeeRepository.searchEmployeeByMobileNumber(9876543210L);

        // Assert
        assertFalse(foundEmployee.isPresent());
    }
}
