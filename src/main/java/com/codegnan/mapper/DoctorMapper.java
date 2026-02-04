package com.codegnan.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.codegnan.dto.DoctorDto;
import com.codegnan.entity.Doctor;
import com.codegnan.entity.Visit;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

	@Mapping(source = "id", target = "doctorId")
	@Mapping(source = "email", target = "emailAddress")
	@Mapping(source = "mobile", target = "phone")
	@Mapping(source = "specialization", target = "fieldSpeciality")
	@Mapping(source = "experience", target = "yrsExperience")
	@Mapping(source = "degrees", target = "qualifications")
	@Mapping(source = "salary", target = "monthlyPay")
	@Mapping(source = "visits", target = "encounterIds", qualifiedByName = "visitIds")
	DoctorDto toDto(Doctor doctor);

	@Mapping(source = "doctorId", target = "id")
	@Mapping(source = "emailAddress", target = "email")
	@Mapping(source = "phone", target = "mobile")
	@Mapping(source = "fieldSpeciality", target = "specialization")
	@Mapping(source = "yrsExperience", target = "experience")
	@Mapping(source = "qualifications", target = "degrees")
	@Mapping(source = "monthlyPay", target = "salary")
	@Mapping(target = "visits", ignore = true)
	Doctor toEntity(DoctorDto dto);

	@Named("visitIds")
	default List<Integer> mapVisitIds(List<Visit> visits) {
		if (visits == null)
			return null;
		return visits.stream().map(Visit::getId).collect(Collectors.toList());
	}
}
