package com.pasindu.ems.service;

import com.pasindu.ems.model.Allocation;

import java.util.List;
import java.util.Optional;

public interface AllocationService {
    List<Allocation> getAll();

    Allocation save(Allocation allocation);

    Optional<Allocation> findById(Integer id);

    List<Allocation> findByEmployeeId(Integer empId);
}
