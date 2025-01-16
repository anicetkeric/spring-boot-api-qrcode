package com.boolabs.sample.web;

import com.boolabs.sample.service.QrCodeGeneratorService;
import com.boolabs.sample.service.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class QrCodeController {

    @Autowired
    private QrCodeGeneratorService qrCodeGeneratorService;

    @GetMapping("/byte-qr-code")
    public ResponseEntity<byte[]> getQrCode() {
        String contentToGenerateQrCode = "Simple Solution";
        byte[] qrCode = qrCodeGeneratorService.generateQrCodeAsByte(contentToGenerateQrCode);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrCode);
    }
}