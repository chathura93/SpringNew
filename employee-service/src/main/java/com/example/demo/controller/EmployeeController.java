package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modal.Address;
import com.example.demo.modal.Employee;
import com.example.demo.modal.Project;
import com.example.demo.service.EmployeeService;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/emscloud")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(RestTemplateBuilder builder){
		return builder.build();
	}
	
	@RequestMapping(value = "/employee", method = RequestMethod.POST)
	public Employee save(@RequestBody Employee employee) {
		return employeeService.save(employee);
	}

	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public List<Employee> fetchAllEmployee() {
		return employeeService.fetchAllEmployee();
	}

	@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
	public ResponseEntity<Employee> fetchEmployee(@PathVariable Integer id, HttpHeaders headers) {
		Employee employee = new Employee();
		employee.setId(id);
		Employee employee1 = employeeService.fetchEmployee(employee,headers );
		if (employee1 == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(employee1);
		}
	}
	
	@RequestMapping(value = "/employee/{id}/projects", method = RequestMethod.GET)
	public ResponseEntity<List<Project>> fetchEmployeeProjects(@PathVariable Integer id, HttpHeaders headers) {
		Employee employee = new Employee();
		employee.setId(id);
		Employee employee1 = employeeService.fetchEmployee(employee,headers);
		if (employee1 == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(employee1.getProjects());
		}
	}
	

	@GetMapping("test")
	public Employee test() {
		Employee employee = new Employee();
		employee.setId(1);
		employee.setName("Pasindu");
		Address address = new Address();
		address.setId(1);
		address.setAddress1("Kotikawatta");
		address.setCity("Colombo");

		employee.setAddress(address);

		return employee;
	}

	@GetMapping("test1")
	public ResponseEntity<Employee> test1() {
		Integer x = 2;
		if (x == null) {
			Employee employee = new Employee();
			employee.setId(1);
			employee.setName("Pasindu");
			Address address = new Address();
			address.setId(1);
			address.setAddress1("Kotikawatta");
			address.setCity("Colombo");

			employee.setAddress(address);

			return ResponseEntity.ok(employee);
		} else {
			HttpHeaders headers = new HttpHeaders();
			headers.add("status", "invalid-ststus");
			return ResponseEntity.notFound().headers(headers).build();
		}

	}

}
