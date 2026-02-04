package com.codegnan.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.codegnan.exceptions.InvalidDateFormatException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Visit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "p_id")
	private Patient patient;

	@ManyToOne
	@JoinColumn(name = "d_id")
	private Doctor doctor;

	private Date date;
	private String disease;
	private Double weight;
	private Double temperature;
	private Double bp;
	private String modeOfPayment;

	public Visit() {
	}

	public Visit(Integer id, String strDate, String disease, Double weight, Double temperature, Double bp,
			String modeOfPayment) throws InvalidDateFormatException {
		this.id = id;
		setDate(strDate);
		this.disease = disease;
		this.weight = weight;
		this.temperature = temperature;
		this.bp = bp;
		this.modeOfPayment = modeOfPayment;
	}

	public Visit(String strDate, String disease, Double weight, Double temperature, Double bp, String modeOfPayment)
			throws InvalidDateFormatException {
		setDate(strDate);
		this.disease = disease;
		this.weight = weight;
		this.temperature = temperature;
		this.bp = bp;
		this.modeOfPayment = modeOfPayment;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(String strDate) throws InvalidDateFormatException {
		try {
			this.date = new SimpleDateFormat("dd-MM-yyyy").parse(strDate);
		} catch (ParseException e) {
			throw new InvalidDateFormatException(e);
		}
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getBp() {
		return bp;
	}

	public void setBp(Double bp) {
		this.bp = bp;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	@Override
	public String toString() {
		String strDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
		return "Visit [id=" + id + ", date=" + strDate + ", disease=" + disease + ", weight=" + weight
				+ ", temperature=" + temperature + ", bp=" + bp + ", modeOfPayment=" + modeOfPayment + "]";
	}
}
