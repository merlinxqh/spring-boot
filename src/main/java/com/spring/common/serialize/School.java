package com.spring.common.serialize;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class School implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5189111123319291981L;

	private String name;

	private Date fromDate;

	private Long years;

	private Integer students;

	private Float flo;

	private List<Student> stuList;

	private Map<String, Object> map;

	private Set<String> set;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Long getYears() {
		return years;
	}

	public void setYears(Long years) {
		this.years = years;
	}

	public Integer getStudents() {
		return students;
	}

	public void setStudents(Integer students) {
		this.students = students;
	}

	public Float getFlo() {
		return flo;
	}

	public void setFlo(Float flo) {
		this.flo = flo;
	}

	public List<Student> getStuList() {
		return stuList;
	}

	public void setStuList(List<Student> stuList) {
		this.stuList = stuList;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public Set<String> getSet() {
		return set;
	}

	public void setSet(Set<String> set) {
		this.set = set;
	}
}
