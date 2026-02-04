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

import com.codegnan.dto.DoctorDto;
import com.codegnan.dto.VisitDto;
import com.codegnan.exceptions.InvalidDoctorIdException;
import com.codegnan.service.DoctorService;
import com.codegnan.service.VisitService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/doctors")
@CrossOrigin
public class DoctorController {
	private static final Logger log = LoggerFactory.getLogger(DoctorController.class);
	private final DoctorService doctorService;
	private final VisitService visitService;

	public DoctorController(DoctorService doctorService, VisitService visitService) {
		this.doctorService = doctorService;
		this.visitService = visitService;
	}

	@GetMapping
	public List<DoctorDto> getAllDoctors() {
		log.info("GET /doctors");
		return doctorService.findAllDoctors();
	}

	@GetMapping("/{id}")
	public DoctorDto getDoctorById(@PathVariable Integer id) {
		log.info("GET /doctors/{}", id);
		return doctorService.findDoctorById(id);
	}

	@PostMapping
	public ResponseEntity<DoctorDto> createDoctor(@Valid @RequestBody DoctorDto doctorDto) {
		log.info("POST /doctors");
		DoctorDto saved = doctorService.hireDoctor(doctorDto);
		log.debug("Doctor created with ID {}", saved.getDoctorId());
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@PutMapping("/{id}")
	public DoctorDto updateDoctor(@PathVariable Integer id, @Valid @RequestBody DoctorDto doctorDto) {
		log.info("PUT /doctors/{}", id);
		if (doctorDto.getDoctorId() == null || !id.equals(doctorDto.getDoctorId())) {
			log.warn("Doctor ID mismatch: path={}, body={}", id, doctorDto.getDoctorId());
			throw new InvalidDoctorIdException("Doctor ID in path and body must match");
		}
		return doctorService.updateDoctor(doctorDto);
	}

	@DeleteMapping("/{id}")
	public DoctorDto deleteDoctor(@PathVariable Integer id) {
		log.info("DELETE /doctors/{}", id);
		return doctorService.deleteDoctor(id);
	}

	@DeleteMapping
	public DoctorDto deleteDoctorParam(@RequestParam Integer id) {
		log.info("DELETE /doctors?id={}", id);
		return doctorService.deleteDoctor(id);
	}

	@GetMapping("/{id}/visits")
	public List<VisitDto> getVisitsByDoctor(@PathVariable Integer id) {
		log.info("GET /doctors/{}/visits", id);
		return visitService.findVisitsByDoctorId(id);
	}

	@DeleteMapping("/{id}/visits")
	public List<VisitDto> deleteVisitsByDoctorId(@PathVariable Integer id) {
		log.info("DELETE /doctors/{}/visits", id);
		return visitService.deleteVisitsByDoctorId(id);
	}

}