package com.codegnan.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codegnan.dto.DoctorDto;
import com.codegnan.entity.Doctor;
import com.codegnan.exceptions.InvalidDoctorIdException;
import com.codegnan.mapper.DoctorMapper;
import com.codegnan.repo.DoctorRepo;

@Service
public class DoctorServiceImpl implements DoctorService {
	private static final Logger log = LoggerFactory.getLogger(DoctorServiceImpl.class);
	private final DoctorRepo doctorRepo;
	private final DoctorMapper doctorMapper;

	public DoctorServiceImpl(DoctorRepo doctorRepo, DoctorMapper doctorMapper) {
		this.doctorRepo = doctorRepo;
		this.doctorMapper = doctorMapper;
	}

	@Override
	@Transactional
	public DoctorDto hireDoctor(DoctorDto doctorDto) {
		log.info("Hiring new doctor");
		Doctor doctor = doctorMapper.toEntity(doctorDto);
		Doctor saved = doctorRepo.save(doctor);
		log.debug("Doctor created with ID {}", saved.getId());
		return doctorMapper.toDto(saved);
	}

	@Override
	public DoctorDto findDoctorById(Integer id) {
		log.info("Fetching doctor with ID {}", id);
		Doctor doctor = doctorRepo.findById(id).orElseThrow(() -> {
			log.warn("Doctor not found with ID {}", id);
			return new InvalidDoctorIdException("Doctor ID " + id + " not found");
		});
		return doctorMapper.toDto(doctor);
	}

	@Override
	public List<DoctorDto> findAllDoctors() {
		log.info("Fetching all doctors");
		List<DoctorDto> list = doctorRepo.findAll().stream().map(doctorMapper::toDto).collect(Collectors.toList());
		log.debug("Total doctors found: {}", list.size());
		return list;
	}

	@Override
	@Transactional
	public DoctorDto updateDoctor(DoctorDto doctorDto) {
		Integer id = doctorDto.getDoctorId();
		log.info("Updating doctor with ID {}", id);
		Doctor existing = doctorRepo.findById(id).orElseThrow(() -> {
			log.warn("Doctor not found for update, ID {}", id);
			return new InvalidDoctorIdException("Doctor ID " + id + " not found");
		});
		existing.setName(doctorDto.getName());
		existing.setEmail(doctorDto.getEmailAddress());
		existing.setMobile(doctorDto.getPhone());
		existing.setGender(doctorDto.getGender());
		existing.setSpecialization(doctorDto.getFieldSpeciality());
		existing.setExperience(doctorDto.getYrsExperience());
		existing.setDegrees(doctorDto.getQualifications());
		existing.setSalary(doctorDto.getMonthlyPay());
		Doctor updated = doctorRepo.save(existing);
		log.debug("Doctor updated successfully with ID {}", updated.getId());
		return doctorMapper.toDto(updated);
	}

	@Override
	@Transactional
	public DoctorDto deleteDoctor(Integer id) {
		log.info("Deleting doctor with ID {}", id);
		Doctor doctor = doctorRepo.findById(id).orElseThrow(() -> {
			log.warn("Doctor not found for delete, ID {}", id);
			return new InvalidDoctorIdException("Doctor ID " + id + " not found");
		});
		doctorRepo.delete(doctor);
		log.debug("Doctor deleted with ID {}", id);
		return doctorMapper.toDto(doctor);
	}
}
