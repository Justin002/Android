package com.beyondsoft.ep2p.model;

import java.io.Serializable;

public class RegionBean implements Serializable{

	private String region_code;
	private String region_name;
	private String region_grade;
	private String parents_region_grade;
	
	public String getRegion_code() {
		return region_code;
	}
	public void setRegion_code(String region_code) {
		this.region_code = region_code;
	}
	public String getRegion_name() {
		return region_name;
	}
	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}
	public String getRegion_grade() {
		return region_grade;
	}
	public void setRegion_grade(String region_grade) {
		this.region_grade = region_grade;
	}
	public String getParents_region_grade() {
		return parents_region_grade;
	}
	public void setParents_region_grade(String parents_region_grade) {
		this.parents_region_grade = parents_region_grade;
	}
	
	
}
