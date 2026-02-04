package com.codegnan.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.codegnan.dto.VisitDto;
import com.codegnan.entity.Visit;

@Mapper(componentModel = "spring")
public interface VisitMapper {

	@Mapping(source = "id", target = "visitRef")
	@Mapping(source = "date", target = "visitDateStr", dateFormat = "dd-MM-yyyy")
	@Mapping(source = "disease", target = "diagnosis")
	@Mapping(source = "weight", target = "wtKg")
	@Mapping(source = "temperature", target = "tempC")
	@Mapping(source = "bp", target = "bpValue")
	@Mapping(source = "modeOfPayment", target = "paymentType")
	@Mapping(source = "patient.id", target = "pid")
	@Mapping(source = "doctor.id", target = "did")
	VisitDto toDto(Visit visit);

	@Mapping(source = "visitRef", target = "id")
	@Mapping(source = "visitDateStr", target = "date", dateFormat = "dd-MM-yyyy")
	@Mapping(source = "diagnosis", target = "disease")
	@Mapping(source = "wtKg", target = "weight")
	@Mapping(source = "tempC", target = "temperature")
	@Mapping(source = "bpValue", target = "bp")
	@Mapping(source = "paymentType", target = "modeOfPayment")
	@Mapping(target = "patient", ignore = true)
	@Mapping(target = "doctor", ignore = true)
	Visit toEntity(VisitDto dto);
}
