package com.codegnan.service;

import java.util.List;

import com.codegnan.dto.VisitDto;

public interface VisitService {
	VisitDto saveVisit(VisitDto visitDto);

	List<VisitDto> findVisits();

	VisitDto findVisitById(Integer id);

	VisitDto editVisit(VisitDto visitDto);

	VisitDto deleteVisit(Integer id);

	List<VisitDto> deleteVisitsByPatientId(Integer patientId);

	List<VisitDto> deleteVisitsByDoctorId(Integer doctorId);

	List<VisitDto> findVisitsByPatientId(Integer patientId);

	List<VisitDto> findVisitsByDoctorId(Integer doctorId);
}