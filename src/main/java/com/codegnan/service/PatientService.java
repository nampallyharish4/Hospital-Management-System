package com.codegnan.service;

import java.util.List;

import com.codegnan.dto.PatientDto;

import jakarta.validation.Valid;

public interface PatientService {
	PatientDto findPatientById(Integer id);

	List<PatientDto> findAllPatients();

	PatientDto updatePatient(PatientDto patientDto);

	PatientDto deletePatient(Integer id);

	PatientDto updatePatientDetails(Integer id, @Valid PatientDto patientDto);

	PatientDto registerPatient(@Valid PatientDto patientDto);
}