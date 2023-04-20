package com.amos.upskillsspringboot.controller;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("biometric/status")
public class BiometricController {
	
	@PostMapping("/update")
	public static ResponseEntity<String> updateBiometricStatus(@RequestParam("facilityId") Long facility, @RequestParam("file") MultipartFile file){
		processBiometricDataCSV(file);
		return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
	}
	
	private static void processBiometricDataCSV(MultipartFile file) {
		try (FileReader filereader = new FileReader(file.getResource().getFile());
		     CSVReader csvReader = new CSVReader(filereader)) {
			List<String[]> data = csvReader.readAll();
			log.debug("data {}", data);
		} catch (IOException | CsvException e) {
			throw new RuntimeException(e);
		}
	}
}
