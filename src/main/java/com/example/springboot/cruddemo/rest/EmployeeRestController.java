package com.example.springboot.cruddemo.rest;

import com.example.springboot.cruddemo.entity.Employee;
import com.example.springboot.cruddemo.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
    private EmployeeService employeeService;

    // inject employee dao directly (use constructor injection)
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // expose "/employees" and return a list of employees
    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeService.findAll();
    }

    // expose "/employees/{employeeId" to get a single meployee
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId) {
        Employee employee = employeeService.findById(employeeId);

        if (employee == null) {
            throw new RuntimeException("Employee with employee id " + employeeId + " not found!");
        }

        return employee;
    }

    // expose "/employees" to add another employee
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        // if the id is passed in the request, set it to 0
        // to force a save of new item instead of an update
        employee.setId(0);

        Employee dbEmployee = employeeService.save(employee);
        return dbEmployee;
    }

    // expose "/employees" to update an employee's information
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {
        // we will use the id as it is
        Employee dbEmployee = employeeService.save(employee);
        // return dbEmployee since this has the latest updated information
        return dbEmployee;
    }

    // expose "/employees/{employeeId}" to delete an existing employee
    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId) {
        Employee employee = employeeService.findById(employeeId);

        if(employee == null) {
            throw new RuntimeException("Employee with employee ID " + employeeId + " does not exist.");
        }
        employeeService.deleteById(employeeId);
        return "Deleted Employee with employee ID " + employeeId;
    }
}
