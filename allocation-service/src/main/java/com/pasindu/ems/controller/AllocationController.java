package com.pasindu.ems.controller;

import com.pasindu.ems.model.Allocation;
import com.pasindu.ems.model.EmployeeAllocation;
import com.pasindu.ems.service.AllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/emscloud")
public class AllocationController {
    @Autowired
    private AllocationService allocationService;

    @PostMapping("/allocation")
    public ResponseEntity<Allocation> create(@RequestBody Allocation allocation) {
        return ResponseEntity.ok(allocationService.save(allocation));
    }

    @GetMapping("/allocations")
    public ResponseEntity<List<Allocation>> fetchAll() {
        return ResponseEntity.ok(allocationService.getAll());
    }

    @GetMapping("/allocation/{id}")
    public ResponseEntity<Allocation> findById(@PathVariable Integer id) {
        Optional<Allocation> optionalAllocation = allocationService.findById(id);
        if (!optionalAllocation.isPresent()) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optionalAllocation.get());
    }

    @GetMapping("/allocation/employee/{empId}")
    public ResponseEntity<EmployeeAllocation> fetchByEmployeeId(@PathVariable Integer empId) {
        EmployeeAllocation employeeAllocation = new EmployeeAllocation();
        employeeAllocation.setEmployeeAllocations(allocationService.findByEmployeeId(empId));
        return ResponseEntity.ok(employeeAllocation);
    }


}
