package com.codegnan.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class VisitDto {

	private Integer visitRef;

	@NotBlank(message = "Visit date is required")
	@Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "Date must be dd-MM-yyyy")
	private String visitDateStr;

	@NotBlank(message = "Diagnosis is required")
	private String diagnosis;

	@Positive(message = "Weight must be positive")
	private Double wtKg;

	@DecimalMin(value = "35.0", message = "Temperature too low")
	@DecimalMax(value = "42.0", message = "Temperature too high")
	private Double tempC;

	@Positive(message = "BP must be positive")
	private Double bpValue;

	@NotBlank(message = "Payment type is required")
	private String paymentType;

	@NotNull(message = "Patient ID is required")
	private Integer pid;

	@NotNull(message = "Doctor ID is required")
	private Integer did;

	public Integer getVisitRef() {
		return visitRef;
	}

	public void setVisitRef(Integer visitRef) {
		this.visitRef = visitRef;
	}

	public String getVisitDateStr() {
		return visitDateStr;
	}

	public void setVisitDateStr(String visitDateStr) {
		this.visitDateStr = visitDateStr;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public Double getWtKg() {
		return wtKg;
	}

	public void setWtKg(Double wtKg) {
		this.wtKg = wtKg;
	}

	public Double getTempC() {
		return tempC;
	}

	public void setTempC(Double tempC) {
		this.tempC = tempC;
	}

	public Double getBpValue() {
		return bpValue;
	}

	public void setBpValue(Double bpValue) {
		this.bpValue = bpValue;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}
}
