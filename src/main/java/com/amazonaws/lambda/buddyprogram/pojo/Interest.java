package com.amazonaws.lambda.buddyprogram.pojo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Interest {

	private Long interestId;
	private InterestEnum interestEnum;
	private Long userId;
	private Timestamp activeDate;
	private Timestamp inActiveDate;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getInterestId() {
		return interestId;
	}

	public void setInterestId(Long interestId) {
		this.interestId = interestId;
	}

	public InterestEnum getInterestEnum() {
		return interestEnum;
	}

	public void setInterestEnum(InterestEnum interestEnum) {
		this.interestEnum = interestEnum;
	}

	public Timestamp getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Timestamp activeDate) {
		this.activeDate = activeDate;
	}

	public Timestamp getInActiveDate() {
		return inActiveDate;
	}

	public void setInActiveDate(Timestamp inActiveDate) {
		this.inActiveDate = inActiveDate;
	}

}
