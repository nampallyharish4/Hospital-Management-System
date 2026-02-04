package com.codegnan.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codegnan.dto.PatientDto;
import com.codegnan.dto.VisitDto;
import com.codegnan.service.PatientService;
import com.codegnan.service.VisitService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/patients")
@CrossOrigin
public class PatientController {
	private static final Logger log = LoggerFactory.getLogger(PatientController.class);
	private final PatientService patientService;
	private final VisitService visitService;

	public PatientController(PatientService patientService, VisitService visitService) {
		super();
		this.patientService = patientService;
		this.visitService = visitService;
	}

	@GetMapping()
	public List<PatientDto> getAllPatients() {
		log.info("GET /patients");
		return patientService.findAllPatients();
	}

	@GetMapping("/{id}")
	public PatientDto getPatientById(@PathVariable Integer id) {
		log.info("GET /patients/{}", id);
		return patientService.findPatientById(id);
	}

	@PostMapping
	public ResponseEntity<PatientDto> createPatient(@Valid @RequestBody PatientDto patientDto) {
		log.info("POST /patients");
		PatientDto saved = patientService.registerPatient(patientDto);
		log.debug("Patient created with ID {}", saved.getPatientId());
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@PutMapping("/{id}")
	public PatientDto updatePatient(@PathVariable Integer id, @Valid @RequestBody PatientDto patientDto) {
		log.info("PUT /patients/{}", id);
		if (patientDto.getPatientId() != null && !patientDto.getPatientId().equals(id)) {
			throw new IllegalArgumentException("Patient ID in the path and request body must match");
		}
		return patientService.updatePatientDetails(id, patientDto);
	}

	@DeleteMapping("/{id}")
	public PatientDto deletePatient(@PathVariable Integer id) {
		log.info("DELETE /patients/{}", id);
		return patientService.deletePatient(id);
	}

	@DeleteMapping
	public PatientDto deletePatientParam(@RequestParam Integer id) {
		log.info("DELETE /patients?id={}", id);
		return patientService.deletePatient(id);
	}

	@GetMapping("/{id}/visits")
	public List<VisitDto> getVisitsByPatientId(@PathVariable Integer id) {
		log.info("GET /patients/{}/visits", id);
		return visitService.findVisitsByPatientId(id);
	}

	@DeleteMapping("/{id}/visits")
	public List<VisitDto> deleteVisitsByPatientId(@PathVariable Integer id) {
		log.info("DELETE /patients/{}/visits", id);
		return visitService.deleteVisitsByPatientId(id);
	}
}