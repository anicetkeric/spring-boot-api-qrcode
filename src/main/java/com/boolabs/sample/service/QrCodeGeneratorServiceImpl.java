package com.boolabs.sample.service;

import com.boolabs.sample.exception.QRCodeGeneratorException;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

@Service
public class QrCodeGeneratorServiceImpl implements QrCodeGeneratorService {

    private final QrCodeService qrCodeService;

    public QrCodeGeneratorServiceImpl(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @Override
    public byte[] generateQrCodeAsByte(String contentToGenerate) {
        try {
            return qrCodeService.getQRCodeByteImage(contentToGenerate);
        } catch (WriterException | IOException e) {
            throw new QRCodeGeneratorException("Error generating QR Code as byte array", e);
        }
    }

    @Override
    public String generateQrCodeAsBase64(String contentToGenerate) {
        byte[] qrCodeBytes = generateQrCodeAsByte(contentToGenerate);
        return Base64.getEncoder().encodeToString(qrCodeBytes);
    }

    @Override
    public boolean generateQRCodeAsImage(String qrCodeContent) {
        return true;
    }
}
