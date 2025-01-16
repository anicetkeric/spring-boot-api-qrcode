package com.boolabs.sample.service;

import com.boolabs.sample.exception.QRCodeGeneratorException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumMap;

@Slf4j
@Component
public class QrCodeService {

    private static final int QR_CODE_DEFAULT_SIZE = 400;

    private static final int DEFAULT_BLACK_COLOR = 0xff000000;

    private static final String DEFAULT_IMAGE_FORMAT = "png";

    private static final int DEFAULT_WHITE_COLOR = 0xFFFFFFFF;

    /**
     * @param codeContent content text
     * @return Byte image
     * @throws WriterException if an error occurs during encoding
     * @throws IOException if an error occurs during writing or when not
     *       able to create required ImageOutputStream.
     */
    public byte[] getQRCodeByteImage(String codeContent) throws WriterException, IOException {
        if(!org.springframework.util.StringUtils.hasText(codeContent)){
            throw new QRCodeGeneratorException("QR Content cannot be null");
        }

        BufferedImage qrcodeImage = qrCodeImageProcessing(codeContent);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        ImageIO.write(qrcodeImage, DEFAULT_IMAGE_FORMAT, pngOutputStream);

       // log.info("Encoding byte successful.");
        return pngOutputStream.toByteArray();
    }


    /**
     * @param codeContent qr code content
     * @param qrCodeSize qr code size
     * @return @{@link BufferedImage}
     * @throws WriterException if cannot encode
     * @throws IOException if path not found
     */
    private BufferedImage qrCodeImageProcessing(String codeContent) throws WriterException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(codeContent, BarcodeFormat.QR_CODE, QR_CODE_DEFAULT_SIZE, QR_CODE_DEFAULT_SIZE, getEncodeHintType());

        int qrcodeWidth = bitMatrix.getWidth();
        int qrcodeHeight = bitMatrix.getHeight();

        // Generate QR code image
        BufferedImage qrcodeImage = new BufferedImage(qrcodeWidth, qrcodeHeight, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < qrcodeWidth; x++) {
            for (int y = 0; y < qrcodeHeight; y++) {
                qrcodeImage.setRGB(x, y, (bitMatrix.get(x, y) ? DEFAULT_BLACK_COLOR : DEFAULT_WHITE_COLOR));
            }
        }

        return qrcodeImage;
    }

    /**
     * @return EnumMap EncodeHintType
     */
    private EnumMap<EncodeHintType, Object> getEncodeHintType() {
        EnumMap<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CharacterSetECI.UTF8);
        hints.put(EncodeHintType.MARGIN, 4);

        return hints;
    }
}
