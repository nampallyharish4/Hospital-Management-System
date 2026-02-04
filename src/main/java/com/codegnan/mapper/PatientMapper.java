package com.codegnan.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.codegnan.dto.PatientDto;
import com.codegnan.entity.Patient;
import com.codegnan.entity.Visit;

@Mapper(componentModel = "spring")
public interface PatientMapper {

	@Mapping(source = "id", target = "patientId")
	@Mapping(source = "email", target = "emailAddress")
	@Mapping(source = "mobile", target = "phone")
	@Mapping(source = "regDate", target = "registrationDateStr", dateFormat = "dd-MM-yyyy")
	@Mapping(source = "age", target = "patientAge")
	@Mapping(source = "visits", target = "encounterIds", qualifiedByName = "visitIds")
	PatientDto toDto(Patient patient);

	@Mapping(source = "patientId", target = "id")
	@Mapping(source = "emailAddress", target = "email")
	@Mapping(source = "phone", target = "mobile")
	@Mapping(source = "registrationDateStr", target = "regDate", dateFormat = "dd-MM-yyyy")
	@Mapping(source = "patientAge", target = "age")
	@Mapping(target = "visits", ignore = true)
	Patient toEntity(PatientDto dto);

	@Named("visitIds")
	default List<Integer> mapVisitIds(List<Visit> visits) {
		if (visits == null)
			return null;
		return visits.stream().map(Visit::getId).collect(Collectors.toList());
	}
}
