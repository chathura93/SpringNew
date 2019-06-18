package com.pasindu.ems.repository;

import com.pasindu.ems.model.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlloationRepository extends JpaRepository<Allocation, Integer> {
    List<Allocation> findByEmpId(Integer empId);
}
