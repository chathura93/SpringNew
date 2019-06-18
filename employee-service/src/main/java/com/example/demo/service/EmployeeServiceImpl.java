package com.example.demo.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.demo.modal.Allocation;
import com.example.demo.modal.EmployeeAllocation;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

import com.example.demo.modal.Employee;
import com.example.demo.modal.Telephone;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Employee save(Employee employee) {

        for (Telephone telephone : employee.getTelephones()) {
            telephone.setEmployee(employee);
        }
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> fetchAllEmployee() {

        return employeeRepository.findAll();
    }

    @Override
    public Employee fetchEmployee(Employee employee, HttpHeaders headers) {
		   Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getId());
	        if (optionalEmployee.isPresent()) {
	         //   fetch project allocation
	            RestTemplate restTemplate = new RestTemplate();

	            Employee employee1 = optionalEmployee.get();
//	            System.out.println(responseEntity.getBody().getEmpId()+">>>>>>>>>");
	            EmployeeAllocation employeeAllocations = fetchEmployeesAllocation(employee1,headers);
	            employee1.setAllocations(employeeAllocations);
	            return employee1;
	        } else {
	            return null;
	        }     }

    @HystrixCommand(fallbackMethod = "fetchEmployeesAllocationFallBack")
    public EmployeeAllocation fetchEmployeesAllocation(Employee employee, HttpHeaders headers) {

        HttpHeaders httpHeaders = new HttpHeaders();

        //extract token from context
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext()
                .getAuthentication().getDetails();

        System.out.println(details.getTokenValue());

        httpHeaders.add("Authorization", "bearer ".concat(details.getTokenValue()));

        ResponseEntity<EmployeeAllocation> responseEntity;
        HttpEntity<String> entity = new HttpEntity<>("", httpHeaders);
        responseEntity = restTemplate.exchange("http://allocation-service/emscloud/allocation/employee/"
                .concat(employee.getId().toString()), HttpMethod.GET, entity, EmployeeAllocation.class);

        return responseEntity.getBody();

    }

    public EmployeeAllocation fetchEmployeesAllocationFallBack(Employee employee,HttpHeaders headers) {
        EmployeeAllocation employeeAllocation = new EmployeeAllocation();
        employeeAllocation.setEmployeeAllocations(Arrays.asList(new Allocation()));

        return employeeAllocation;
    }

	
}
