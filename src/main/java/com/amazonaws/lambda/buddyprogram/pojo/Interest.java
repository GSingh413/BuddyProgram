package com.amazonaws.lambda.buddyprogram.pojo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Interest {

	private String currentInterest;
	private String areaOfBusiness;
	private String title;
	private Long interestId;
	private Long userId;
	private Timestamp activeDate;
	private Timestamp inActiveDate;

	public String getcurrentInterest() {
		return currentInterest;
	}

	public void setCurrentInterest(String currentInterest) {
		this.currentInterest = currentInterest;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getareaOfBusiness() {
		return areaOfBusiness;
	}

	public void setAreaOfBusiness(String areaOfBusiness) {
		this.areaOfBusiness = areaOfBusiness;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Timestamp activeDate) {
		this.activeDate = activeDate;
	}

	public Timestamp getInactiveDate() {
		return inActiveDate;
	}

	public void setInActiveDate(Timestamp inActiveDate) {
		this.inActiveDate = inActiveDate;
	}

	public Long getInterestId() {
		return interestId;
	}

	public void setInterestId(Long interestId) {
		this.interestId = interestId;
	}

}
