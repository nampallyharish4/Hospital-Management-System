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

import com.codegnan.dto.VisitDto;
import com.codegnan.exceptions.InvalidVisitIdException;
import com.codegnan.service.VisitService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/visits")
@CrossOrigin
public class VisitController {
	private static final Logger log = LoggerFactory.getLogger(VisitController.class);
	private final VisitService visitService;

	public VisitController(VisitService visitService) {
		this.visitService = visitService;
	}

	@GetMapping
	public List<VisitDto> getAllVisits() {
		log.info("GET /visits");
		return visitService.findVisits();
	}

	@GetMapping("/{id}")
	public VisitDto getVisitById(@PathVariable Integer id) {
		log.info("GET /visits/{}", id);
		return visitService.findVisitById(id);
	}

	@PostMapping
	public ResponseEntity<VisitDto> createVisit(@Valid @RequestBody VisitDto visitDto) {
		log.info("POST /visits (patientId={}, doctorId={})", visitDto.getPid(), visitDto.getDid());
		VisitDto saved = visitService.saveVisit(visitDto);
		log.debug("Visit created with ID {}", saved.getVisitRef());
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@PutMapping("/{id}")
	public VisitDto updateVisit(@PathVariable Integer id, @Valid @RequestBody VisitDto visitDto) {
		log.info("PUT /visits/{}", id);
		if (visitDto.getVisitRef() == null || !id.equals(visitDto.getVisitRef())) {
			log.warn("Visit ID mismatch: path={}, body={}", id, visitDto.getVisitRef());
			throw new InvalidVisitIdException("Visit ID in path and body must match");
		}
		return visitService.editVisit(visitDto);
	}

	@DeleteMapping("/{id}")
	public VisitDto deleteVisit(@PathVariable Integer id) {
		log.info("DELETE /visits/{}", id);
		return visitService.deleteVisit(id);
	}

	@DeleteMapping
	public VisitDto deleteVisitParam(@RequestParam Integer id) {
		log.info("DELETE /visits?id={}", id);
		return visitService.deleteVisit(id);
	}

	@GetMapping("/patient/{patientId}")
	public List<VisitDto> getVisitsByPatient(@PathVariable Integer patientId) {
		log.info("GET /visits/patient/{}", patientId);
		return visitService.findVisitsByPatientId(patientId);
	}

	@GetMapping("/doctor/{doctorId}")
	public List<VisitDto> getVisitsByDoctor(@PathVariable Integer doctorId) {
		log.info("GET /visits/doctor/{}", doctorId);
		return visitService.findVisitsByDoctorId(doctorId);
	}

	@DeleteMapping("/patient/{patientId}")
	public List<VisitDto> deleteVisitsByPatient(@PathVariable Integer patientId) {
		log.info("DELETE /visits/patient/{}", patientId);
		return visitService.deleteVisitsByPatientId(patientId);
	}

	@DeleteMapping("/doctor/{doctorId}")
	public List<VisitDto> deleteVisitsByDoctor(@PathVariable Integer doctorId) {
		log.info("DELETE /visits/doctor/{}", doctorId);
		return visitService.deleteVisitsByDoctorId(doctorId);
	}
}