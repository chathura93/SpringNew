package com.example.hystrix;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.modal.Allocation;
import com.example.demo.modal.Employee;
import com.example.demo.modal.EmployeeAllocation;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class AllocationCommand extends HystrixCommand<Allocation[]>{

	
	Employee employee;
	HttpHeaders httpHeadrs;
	RestTemplate restTemplates;
	
	public AllocationCommand(Employee employee,HttpHeaders httpHeadrs,RestTemplate restTemplates) {
		
		super(HystrixCommandGroupKey.Factory.asKey("default"));
		this.employee=employee;
		this.httpHeadrs=httpHeadrs;
		this.restTemplates=restTemplates;
		
		// TODO Auto-generated constructor stub
	}
	
	protected Allocation[] run() throws Exception {
		 ResponseEntity<Allocation[]> responeEntity;
	        HttpEntity<String> entity = new HttpEntity<>("", httpHeadrs);
	        resposeEntity = restTemplates.exchange("http://allocation-service/emscloud/allocation/"
	                .concat(employee.getId().toString()), HttpMethod.GET, entity, EmployeeAllocation.class);

	        return responeEntity.getBody();
	}
	protected Allocation[] getFallBack() {
		return new Allocation[1];
	}

}
