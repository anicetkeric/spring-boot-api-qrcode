package com.boolabs.sample.web;

import com.boolabs.sample.service.BarcodeService;
import com.google.zxing.WriterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api")
public class BarcodeController {

    private final BarcodeService barcodeService;

    public BarcodeController(BarcodeService barcodeService) {
        this.barcodeService = barcodeService;
    }

    @GetMapping(value = "/{type}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateCode(@PathVariable String type, @RequestParam String text) {
        byte[] code;
        try {
            return switch (type.toLowerCase()) {
                case "qrcode" -> {
                    code = barcodeService.generateQRCode(text);
                    yield ResponseEntity.ok(code);
                }
                case "barcode" -> {
                    code = barcodeService.generateBarcode(text);
                    yield ResponseEntity.ok(code);
                }
                default -> ResponseEntity.badRequest().body(null); // 400 Bad Request for invalid type
            };
        } catch (WriterException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}