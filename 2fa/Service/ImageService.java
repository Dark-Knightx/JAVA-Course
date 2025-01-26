package com.example.BOWO.Service;

import com.example.BOWO.Modal.MyEntity;
import com.example.BOWO.Repo.MyRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

@Service
public class ImageService {

    private final MyRepository myRepository;

    public ImageService(MyRepository myRepository) {
        this.myRepository = myRepository;
    }

    public void saveImage(MultipartFile file) throws IOException {
            byte[] compressedImage = ImageUtil.compress(file.getBytes());
            MyEntity entity = new MyEntity();
            entity.setImageData(compressedImage);
            myRepository.save(entity);
    }

//    public byte[] getImage(Long id) {
//        MyEntity entity = myRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Image not found with id: " + id));
//        return ImageUtil.decompress(entity.getImageData());
//    }
    public byte[] getImage(Long id) {
        return myRepository.findById(id)
                .map(entity -> ImageUtil.decompress(entity.getImageData()))
                .orElseGet(this::getDefaultImage);
    }

    private byte[] getDefaultImage() {
        try {
            // Load the default image from resources (e.g., "static/default.png")
            ClassPathResource defaultImage = new ClassPathResource("static/assets/image/one-piece-roronoa-zoro-swordsman-sword-katana-hd-wallpaper-preview.jpg");
            return Files.readAllBytes(defaultImage.getFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException("Default image could not be loaded", e);
        }
    }
}

