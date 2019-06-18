package com.pasindu.ems.service;

import com.pasindu.ems.model.Allocation;
import com.pasindu.ems.repository.AlloationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AllocationServiceImpl implements AllocationService {

    @Autowired
    private AlloationRepository alloationRepository;

    @Override
    public List<Allocation> getAll() {
        return alloationRepository.findAll();
    }

    @Override
    public Allocation save(Allocation allocation) {
        return alloationRepository.save(allocation);
    }

    @Override
    public Optional<Allocation> findById(Integer id) {
        return alloationRepository.findById(id);
    }

    @Override
    public List<Allocation> findByEmployeeId(Integer empId) {
        return alloationRepository.findByEmpId(empId);
    }
}
