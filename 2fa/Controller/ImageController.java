package com.example.BOWO.Controller;

import com.example.BOWO.Service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }
    @GetMapping("/SaveImage")
    public String kjbj(Model model){
        Long imageId = 4L; // Example value
        model.addAttribute("imageId", imageId);
        return "ImageTest";
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        boolean errors = false;
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No image file uploaded. Please select a file.");
            System.out.println("No file uploaded error added");
            errors = true; // Redirect to the upload page
            return "redirect:/images/SaveImage";
        }
        imageService.saveImage(file);
        return "redirect:/images/SaveImage";
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        byte[] image = imageService.getImage(id);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }
}

