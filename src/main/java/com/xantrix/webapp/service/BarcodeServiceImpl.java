package com.xantrix.webapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xantrix.webapp.entities.Barcode;
import com.xantrix.webapp.repository.BarcodeRepository;

@Service
public class BarcodeServiceImpl implements BarcodeService {

	private static final Logger log = LoggerFactory.getLogger(BarcodeServiceImpl.class);

	@Autowired
	private BarcodeRepository barcodeRepo;

	@Override
	public Barcode SelByBarcode(String barcode) {
		log.info("SelByBarcode - START - barcode={}", barcode);
		Barcode theBarcode = barcodeRepo.findByBarcode(barcode);
		log.info("SelByBarcode - END - result={}", theBarcode);
		return theBarcode;
	}

}