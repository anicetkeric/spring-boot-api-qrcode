package com.boolabs.sample.service;

public interface QrCodeGeneratorService {

    byte[] generateQrCodeAsByte(String contentToGenerate);

    String generateQrCodeAsBase64(String contentToGenerate);

    boolean generateQRCodeAsImage(String qrCodeContent);
}
