package com.codegnan.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codegnan.dto.PatientDto;
import com.codegnan.entity.Patient;
import com.codegnan.exceptions.InvalidPatientIdException;
import com.codegnan.mapper.PatientMapper;
import com.codegnan.repo.PatientRepo;

import jakarta.validation.Valid;

@Service
public class PatientServiceImpl implements PatientService {

	private static final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

	private final PatientRepo patientRepo;
	private final PatientMapper patientMapper;

	public PatientServiceImpl(PatientRepo patientRepo, PatientMapper patientMapper) {
		this.patientRepo = patientRepo;
		this.patientMapper = patientMapper;
	}

	@Override
	public PatientDto findPatientById(Integer id) {
		log.info("Fetching patient with ID {}", id);

		Patient patient = patientRepo.findById(id).orElseThrow(() -> {
			log.warn("Patient not found with ID {}", id);
			return new InvalidPatientIdException("Patient ID " + id + " not found");
		});

		return patientMapper.toDto(patient);
	}

	@Override
	public List<PatientDto> findAllPatients() {
		log.info("Fetching all patients");

		List<PatientDto> list = patientRepo.findAll().stream().map(patientMapper::toDto).collect(Collectors.toList());

		log.debug("Total patients found: {}", list.size());
		return list;
	}

	@Override
	@Transactional
	public PatientDto updatePatient(PatientDto patientDto) {
		Integer id = patientDto.getPatientId();
		log.info("Updating patient with ID {}", id);

		Patient existing = patientRepo.findById(id).orElseThrow(() -> {
			log.warn("Patient not found for update, ID {}", id);
			return new InvalidPatientIdException("Patient ID " + id + " not found");
		});

		existing.setName(patientDto.getName());
		existing.setEmail(patientDto.getEmailAddress());
		existing.setMobile(patientDto.getPhone());
		existing.setGender(patientDto.getGender());
		existing.setAge(patientDto.getPatientAge());
		existing.setRegDate(patientDto.getRegistrationDateStr());

		Patient updated = patientRepo.save(existing);
		log.debug("Patient updated successfully with ID {}", updated.getId());

		return patientMapper.toDto(updated);
	}

	@Override
	@Transactional
	public PatientDto deletePatient(Integer id) {
		log.info("Deleting patient with ID {}", id);

		Patient patient = patientRepo.findById(id).orElseThrow(() -> {
			log.warn("Patient not found for delete, ID {}", id);
			return new InvalidPatientIdException("Patient ID " + id + " not found");
		});

		patientRepo.delete(patient);
		log.debug("Patient deleted with ID {}", id);

		return patientMapper.toDto(patient);
	}

	@Override
	public PatientDto updatePatientDetails(Integer id, @Valid PatientDto patientDto) {
		patientDto.setPatientId(id);
		return updatePatient(patientDto);
	}

	@Override
	@Transactional
	public PatientDto registerPatient(@Valid PatientDto patientDto) {
		log.info("Registering new patient");

		Patient patient = patientMapper.toEntity(patientDto);
		Patient saved = patientRepo.save(patient);

		log.debug("Patient registered with ID {}", saved.getId());
		return patientMapper.toDto(saved);
	}
}