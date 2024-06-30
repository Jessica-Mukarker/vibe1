package com.amar.onlinestore;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileToByteArrayConverter implements Converter<MultipartFile, byte[]> {
    @Override
    public byte[] convert(MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            // Handle the exception appropriately
            return null;
        }
    }
}
