package com.example.BOWO.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtil {

    /**
     * Compress the image bytes before storing in the database.
     *
     * @param data the original image bytes
     * @return the compressed image bytes
     */
    public static byte[] compress(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            byte[] buffer = new byte[1024];
            while (!deflater.finished()) {
                int count = deflater.deflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Could not compress image data", e);
        } finally {
            deflater.end();
        }
    }

    /**
     * Decompress the image bytes before displaying.
     *
     * @param data the compressed image bytes
     * @return the decompressed image bytes
     */
    public static byte[] decompress(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            byte[] buffer = new byte[1024];
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            return outputStream.toByteArray();
        } catch (IOException | java.util.zip.DataFormatException e) {
            throw new RuntimeException("Could not decompress image data", e);
        } finally {
            inflater.end();
        }
    }
}

