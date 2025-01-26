package com.example.BOWO.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class TwoFactorAuthService {

    private final GoogleAuthenticator gAuth;

    public TwoFactorAuthService() {
        // Configure GoogleAuthenticator to allow only the current time window
        GoogleAuthenticatorConfig config = new GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder()
                .setTimeStepSizeInMillis(30_000) // 30 seconds per step (default for TOTP)
                .setWindowSize(1) // Only allow the current time window
                .build();
        this.gAuth = new GoogleAuthenticator(config);
    }

    // Generate the QR code for 2FA
    public String generateQrCode(String secretKey, String username) {
        try {
            // otpauth URL format: otpauth://totp/{Issuer}:{AccountName}?secret={SecretKey}&issuer={Issuer}
            String url = "otpauth://totp/MyApp:" + username + "?secret=" + secretKey + "&issuer=MyApp";

            // Generate the QR code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hintMap = new HashMap<>();
            hintMap.put(EncodeHintType.MARGIN, 1); // Optional: controls the margin of the QR code
            BufferedImage image = toBufferedImage(qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 200, 200, hintMap));

            // Encode the image to base64 to be displayed in web pages
            return encodeImageToBase64(image);
        } catch (Exception e) {
            throw new RuntimeException("Error generating QR code", e);
        }
    }

    // Convert BufferedImage to Base64 string (used in Thymeleaf or Web front-end)
    private String encodeImageToBase64(BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            ImageIO.write(image, "PNG", baos);
            byte[] imageBytes = baos.toByteArray();
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error encoding image to base64", e);
        }
    }

    // Convert QR Code to BufferedImage
    private BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, (matrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB()));
            }
        }
        return image;
    }

    // Method to verify the 2FA code using TOTP
    public boolean verifyCode(String secretKey, String code) {
        try {
            // Validate the entered TOTP code
            return gAuth.authorize(secretKey, Integer.parseInt(code));
        } catch (NumberFormatException e) {
            return false; // If the code is not a valid integer, return false
        }
    }

    // Generate the secret key for a user
    public String generateSecretKey() {
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        return key.getKey(); // The permanent secret key for the user
    }
}
