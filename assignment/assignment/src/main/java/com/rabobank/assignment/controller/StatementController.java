package com.rabobank.assignment.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rabobank.assignment.constant.AppConstants;
import com.rabobank.assignment.model.AppResponse;
import com.rabobank.assignment.model.Record;
import com.rabobank.assignment.service.ExtractorService;
import com.rabobank.assignment.service.ValidatorService;

/**
 * 
 * @author Dinesh
 *
 */

@RestController
@RequestMapping("/rabobank")
public class StatementController {

	@Autowired
	private ValidatorService validatorService;

	@Autowired
	private ExtractorService extractorService;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody AppResponse test() throws Exception {
		AppResponse appResponse = new AppResponse();
		return appResponse;
	}

	@RequestMapping(value = "/processStatment", method = RequestMethod.POST)
	public @ResponseBody AppResponse handleFileUpload(@RequestParam("file") MultipartFile multipart, HttpServletRequest request) throws Exception {
		AppResponse appResponse = new AppResponse();
		System.out.println("Content type===>" + multipart.getContentType());
		if (!multipart.isEmpty()) {
			if (multipart.getContentType().equalsIgnoreCase(AppConstants.FILE_TYPE_CSV)) {
				List<Record> errorRecords = new ArrayList<Record>();
				
				String filePath = request.getServletContext().getRealPath("/");
				File csvFile = new File(filePath + multipart.getOriginalFilename());
				
				multipart.transferTo(csvFile);
				List<Record> extractedRecords = extractorService.extractStatmentFromCSV(csvFile);
				errorRecords.addAll(validatorService.getDuplicateRecords(extractedRecords));
				errorRecords.addAll(validatorService.getEndBalanceErrorRecords(extractedRecords));
				if (!errorRecords.isEmpty()) {
					appResponse.setResponseCode(AppConstants.HTTP_CODE_SUCCESS);
					appResponse.setResponseMessage(AppConstants.VALIDATION_SUCCESS);
					appResponse.setRecords(errorRecords);
				} else {
					appResponse.setResponseCode(AppConstants.HTTP_CODE_ERROR);
					appResponse.setResponseMessage(AppConstants.VALIDATION_ERROR);
				}
			} else if (multipart.getContentType().equalsIgnoreCase(AppConstants.FILE_TYPE_XML)) {
				List<Record> errorRecords = new ArrayList<Record>();
				String filePath = request.getServletContext().getRealPath("/");
				File xmlFile = new File(filePath + multipart.getOriginalFilename());
				multipart.transferTo(xmlFile);
				List<Record> extractedRecords = extractorService.extractStatmentFromXML(xmlFile);
				errorRecords.addAll(validatorService.getDuplicateRecords(extractedRecords));
				errorRecords.addAll(validatorService.getEndBalanceErrorRecords(extractedRecords));
				if (!errorRecords.isEmpty()) {
					appResponse.setResponseCode(AppConstants.HTTP_CODE_SUCCESS);
					appResponse.setResponseMessage(AppConstants.VALIDATION_SUCCESS);
					appResponse.setRecords(errorRecords);
				} else {
					appResponse.setResponseCode(AppConstants.HTTP_CODE_ERROR);
					appResponse.setResponseMessage(AppConstants.VALIDATION_ERROR);
				}
			} else {
				appResponse.setResponseCode(AppConstants.HTTP_CODE_INVALID_INPUT);
				appResponse.setResponseMessage(AppConstants.UNSUPORTED_FILE_FORMAT);
			}
		} else {
			appResponse.setResponseCode(AppConstants.HTTP_CODE_INVALID_INPUT);
			appResponse.setResponseMessage(AppConstants.INVALID_INPUT);
		}
		return appResponse;
	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody AppResponse handleException(HttpServletRequest request, Exception ex) {
		AppResponse appResponse = new AppResponse();
		appResponse.setResponseCode(AppConstants.HTTP_CODE_ERROR);
		appResponse.setResponseMessage(AppConstants.UNEXPECTED_SERVER_ERROR);
		return appResponse;
	}

}