package com.codegnan.service;

import java.util.List;

import com.codegnan.dto.DoctorDto;

public interface DoctorService {
	DoctorDto hireDoctor(DoctorDto doctorDto);

	DoctorDto findDoctorById(Integer id);

	List<DoctorDto> findAllDoctors();

	DoctorDto updateDoctor(DoctorDto doctorDto);

	DoctorDto deleteDoctor(Integer id);
}
