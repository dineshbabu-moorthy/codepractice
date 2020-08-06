package com.rabobank.assignment.model;

import java.util.List;

public class AppResponse {
	
	private String responseMessage;
	private int responseCode;
	private List<Record> duplicateRecords;
	private List<Record> wrongEndBalRecords;

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public List<Record> getDuplicateRecords() {
		return duplicateRecords;
	}

	public void setDuplicateRecords(List<Record> duplicateRecords) {
		this.duplicateRecords = duplicateRecords;
	}

	public List<Record> getWrongEndBalRecords() {
		return wrongEndBalRecords;
	}

	public void setWrongEndBalRecords(List<Record> wrongEndBalRecords) {
		this.wrongEndBalRecords = wrongEndBalRecords;
	}

	@Override
	public String toString() {
		return "Error [responseMessage=" + responseMessage + ", responseCode=" + responseCode + "]";
	}

}
