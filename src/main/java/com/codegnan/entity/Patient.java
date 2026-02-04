package com.codegnan.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.codegnan.exceptions.InvalidDateFormatException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Patient extends Person {

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date regDate;

	private Integer age;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Visit> visits;

	public Patient() {
	}

	public Patient(Integer id, String name, String email, String mobile, String gender, String strRegDate, Integer age)
			throws InvalidDateFormatException {
		super(id, name, email, mobile, gender);
		setRegDate(strRegDate);
		this.age = age;
	}

	public Patient(String name, String email, String mobile, String gender, String strRegDate, Integer age)
			throws InvalidDateFormatException {
		super(name, email, mobile, gender);
		setRegDate(strRegDate);
		this.age = age;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(String strRegDate) throws InvalidDateFormatException {
		try {
			this.regDate = new SimpleDateFormat("dd-MM-yyyy").parse(strRegDate);
		} catch (ParseException e) {
			throw new InvalidDateFormatException(e);
		}
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}

	@Override
	public String toString() {
		String dateStr = regDate == null ? "N/A" : new SimpleDateFormat("dd-MM-yyyy").format(regDate);
		return "Patient [ " + super.toString() + ", regDate=" + dateStr + ", age=" + age + "]";
	}
}
