package com.rabobank.assignment.service;

import java.io.File;
import java.util.List;

import com.rabobank.assignment.model.Record;

public interface ExtractorService {
	
    public List<Record> extractStatmentFromCSV(File file) throws Exception;
	
	public List<Record> extractStatmentFromXML(File file) throws Exception;

}
