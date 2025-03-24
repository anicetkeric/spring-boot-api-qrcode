package com.boolabs.sample.service;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface BarcodeService {

    byte[] generateQRCode(String text) throws WriterException, IOException;

    byte[] generateBarcode(String text) throws IOException, WriterException;
}
