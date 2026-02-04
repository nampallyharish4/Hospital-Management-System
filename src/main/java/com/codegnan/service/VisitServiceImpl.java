package com.codegnan.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codegnan.dto.VisitDto;
import com.codegnan.entity.Doctor;
import com.codegnan.entity.Patient;
import com.codegnan.entity.Visit;
import com.codegnan.exceptions.InvalidDoctorIdException;
import com.codegnan.exceptions.InvalidPatientIdException;
import com.codegnan.exceptions.InvalidVisitIdException;
import com.codegnan.mapper.VisitMapper;
import com.codegnan.repo.DoctorRepo;
import com.codegnan.repo.PatientRepo;
import com.codegnan.repo.VisitRepo;

@Service
public class VisitServiceImpl implements VisitService {
	private static final Logger log = LoggerFactory.getLogger(VisitServiceImpl.class);
	private final VisitRepo visitRepo;
	private final PatientRepo patientRepo;
	private final DoctorRepo doctorRepo;
	private final VisitMapper visitMapper;

	public VisitServiceImpl(VisitRepo visitRepo, PatientRepo patientRepo, DoctorRepo doctorRepo,
			VisitMapper visitMapper) {
		this.visitRepo = visitRepo;
		this.patientRepo = patientRepo;
		this.doctorRepo = doctorRepo;
		this.visitMapper = visitMapper;
	}

	@Override
	@Transactional
	public VisitDto saveVisit(VisitDto visitDto) {
		log.info("Creating visit for patient {} and doctor {}", visitDto.getPid(), visitDto.getDid());
		Patient patient = patientRepo.findById(visitDto.getPid()).orElseThrow(() -> {
			log.warn("Patient not found for visit, ID {}", visitDto.getPid());
			return new InvalidPatientIdException("Patient ID " + visitDto.getPid() + " not found");
		});
		Doctor doctor = doctorRepo.findById(visitDto.getDid()).orElseThrow(() -> {
			log.warn("Doctor not found for visit, ID {}", visitDto.getDid());
			return new InvalidDoctorIdException("Doctor ID " + visitDto.getDid() + " not found");
		});
		Visit visit = visitMapper.toEntity(visitDto);
		visit.setPatient(patient);
		visit.setDoctor(doctor);
		Visit saved = visitRepo.save(visit);
		log.debug("Visit created with ID {}", saved.getId());
		return visitMapper.toDto(saved);
	}

	@Override
	public List<VisitDto> findVisits() {
		log.info("Fetching all visits");
		List<VisitDto> list = visitRepo.findAll().stream().map(visitMapper::toDto).collect(Collectors.toList());
		log.debug("Total visits found: {}", list.size());
		return list;
	}

	@Override
	public VisitDto findVisitById(Integer id) {
		log.info("Fetching visit with ID {}", id);
		Visit visit = visitRepo.findById(id).orElseThrow(() -> {
			log.warn("Visit not found with ID {}", id);
			return new InvalidVisitIdException("Visit ID " + id + " not found");
		});
		return visitMapper.toDto(visit);
	}

	@Override
	@Transactional
	public VisitDto editVisit(VisitDto visitDto) {
		Integer id = visitDto.getVisitRef();
		log.info("Updating visit with ID {}", id);
		Visit existing = visitRepo.findById(id).orElseThrow(() -> {
			log.warn("Visit not found for update, ID {}", id);
			return new InvalidVisitIdException("Visit ID " + id + " not found");
		});
		Patient patient = patientRepo.findById(visitDto.getPid()).orElseThrow(() -> {
			log.warn("Patient not found for visit update, ID {}", visitDto.getPid());
			return new InvalidPatientIdException("Patient ID " + visitDto.getPid() + " not found");
		});
		Doctor doctor = doctorRepo.findById(visitDto.getDid()).orElseThrow(() -> {
			log.warn("Doctor not found for visit update, ID {}", visitDto.getDid());
			return new InvalidDoctorIdException("Doctor ID " + visitDto.getDid() + " not found");
		});
		existing.setDate(visitDto.getVisitDateStr());
		existing.setDisease(visitDto.getDiagnosis());
		existing.setWeight(visitDto.getWtKg());
		existing.setTemperature(visitDto.getTempC());
		existing.setBp(visitDto.getBpValue());
		existing.setModeOfPayment(visitDto.getPaymentType());
		existing.setPatient(patient);
		existing.setDoctor(doctor);
		Visit updated = visitRepo.save(existing);
		log.debug("Visit updated successfully with ID {}", updated.getId());
		return visitMapper.toDto(updated);
	}

	@Override
	@Transactional
	public VisitDto deleteVisit(Integer id) {
		log.info("Deleting visit with ID {}", id);
		Visit visit = visitRepo.findById(id).orElseThrow(() -> new InvalidVisitIdException("Visit ID " + id + " not found"));
		visitRepo.delete(visit);
		return visitMapper.toDto(visit);
	}

	@Override
	@Transactional
	public List<VisitDto> deleteVisitsByPatientId(Integer patientId) {
		log.info("Deleting all visits for patient ID {}", patientId);
		Patient patient = patientRepo.findById(patientId).orElseThrow(() -> {
			log.warn("Patient not found for deleting visits, ID {}", patientId);
			return new InvalidPatientIdException("Patient ID " + patientId + " not found");
		});
		List<Visit> visits = visitRepo.findAllByPatient(patient);
		visitRepo.deleteAll(visits);
		return visits.stream().map(visitMapper::toDto).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public List<VisitDto> deleteVisitsByDoctorId(Integer doctorId) {
		log.info("Deleting all visits for doctor ID {}", doctorId);
		Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(() -> {
			log.warn("Doctor not found for deleting visits, ID {}", doctorId);
			return new InvalidDoctorIdException("Doctor ID " + doctorId + " not found");
		});
		List<Visit> visits = visitRepo.findAllByDoctor(doctor);
		visitRepo.deleteAll(visits);
		return visits.stream().map(visitMapper::toDto).collect(Collectors.toList());
	}

	/*
	 * =============================== IMPLEMENTED METHODS (FIX)
	 * ===============================
	 */
	@Override
	public List<VisitDto> findVisitsByPatientId(Integer patientId) {
		log.info("Fetching visits for patient ID {}", patientId);
		Patient patient = patientRepo.findById(patientId).orElseThrow(() -> {
			log.warn("Patient not found for visits, ID {}", patientId);
			return new InvalidPatientIdException("Patient ID " + patientId + " not found");
		});
		List<VisitDto> list = visitRepo.findAllByPatient(patient).stream().map(visitMapper::toDto)
				.collect(Collectors.toList());
		log.debug("Found {} visits for patient ID {}", list.size(), patientId);
		return list;
	}

	@Override
	public List<VisitDto> findVisitsByDoctorId(Integer doctorId) {
		log.info("Fetching visits for doctor ID {}", doctorId);
		Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(() -> {
			log.warn("Doctor not found for visits, ID {}", doctorId);
			return new InvalidDoctorIdException("Doctor ID " + doctorId + " not found");
		});
		List<VisitDto> list = visitRepo.findAllByDoctor(doctor).stream().map(visitMapper::toDto)
				.collect(Collectors.toList());
		log.debug("Found {} visits for doctor ID {}", list.size(), doctorId);
		return list;
	}

}
