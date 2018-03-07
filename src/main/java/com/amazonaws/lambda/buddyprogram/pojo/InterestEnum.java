package com.amazonaws.lambda.buddyprogram.pojo;

import com.amazonaws.services.sns.model.NotFoundException;

public enum InterestEnum {
	BIG_DATA("Big Data"), SECURITY("Security"), IT("IT"), LEADERSHIP("Leadership"), APPLICATION_DEVELOPMENT(
			"Application Development"), FINANCIAL_ANALYSIS("Financial Analysis"), INVESTING(
					"Investing"), MARKETING("Marketing"), SALES("Sales"), CUSTOMER_RELATIONS("Customer Relations");

	private String interestTitle;

	InterestEnum(String interestTitle) {
		this.interestTitle = interestTitle;
	}

	public String interestTitle() {
		return interestTitle;
	}

	public static InterestEnum fromString(String enumStringValue) {
		InterestEnum finalInterEnum = null;
		for (InterestEnum interestEnumLocal : InterestEnum.values()) {
			if (interestEnumLocal.interestTitle().equals(enumStringValue)) {
				finalInterEnum = interestEnumLocal;
				break;
			}
		}

		if (finalInterEnum == null) {
			throw new NotFoundException("Unable to locate Interest Enum");
		}

		return finalInterEnum;

	}
}
