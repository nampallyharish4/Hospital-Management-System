package com.codegnan.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codegnan.entity.Doctor;
import com.codegnan.entity.Patient;
import com.codegnan.entity.Visit;

@Repository
public interface VisitRepo extends JpaRepository<Visit, Integer> {

	public List<Visit> findAllByPatient(Patient patient);

	public List<Visit> findAllByDoctor(Doctor doctor);
}
