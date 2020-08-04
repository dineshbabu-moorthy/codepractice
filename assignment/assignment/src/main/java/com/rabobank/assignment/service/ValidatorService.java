package com.rabobank.assignment.service;

import java.util.List;

import com.rabobank.assignment.model.Record;

public interface ValidatorService {

	public List<Record> getDuplicateRecords(List<Record> records);
	
	public List<Record> getEndBalanceErrorRecords(List<Record> records);
}
