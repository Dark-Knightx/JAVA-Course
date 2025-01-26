package com.example.BOWO.Service;

import com.example.BOWO.Modal.*;
import com.example.BOWO.Repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

@Service
public class UserService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private OTPRepo otpRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private BankRepo bankRepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void genotp(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        OTPgenerator otpGenerator = new OTPgenerator();
        otpGenerator.setEmail(email);
        otpGenerator.setOtp(String.valueOf((int) (Math.random() * 90000) + 10000)); // Generate random 5-digit OTP
        otpRepo.save(otpGenerator);
    }

    public boolean saveStep1(Customer customer) {
        Customer customer1 = customerRepo.findByEmail(customer.getEmail());
        if (customer1 == null) {
            Customer customer2 = new Customer();
            customer2.setUsername(customer.getUsername());
            customer2.setPassword(customer.getPassword());
            customer2.setEmail(customer.getEmail());
            customer2.setIsComplete(false);
            customerRepo.save(customer2);
            String roleName = "USER";
            Role role = roleRepo.findByName(roleName).orElseGet(() -> {
                Role newRole = new Role();
                newRole.setName(roleName);
                return roleRepo.save(newRole);
            });
            User user = new User();
            user.setUsername(customer.getUsername());
            user.setEmail(customer.getEmail());
            user.setPassword(passwordEncoder.encode(customer.getPassword()));
            roleRepo.save(role);
            user.getRoles().add(role);

            Bank bank = new Bank();
            bankRepo.save(bank);
            user.getBankDetails().add(bank);
            userRepository.save(user);
            return true;
        }else {
            return false;
        }
    }

    public boolean completeRegistration(Customer additionalDetails, MultipartFile file) throws IOException {
        Customer existingUser = customerRepo.findByEmail(additionalDetails.getEmail());

        if (additionalDetails.getEmail().equals(existingUser.getEmail())) {
            existingUser.setBrandName(additionalDetails.getBrandName());
            existingUser.setBusinessId(additionalDetails.getBusinessId());
            existingUser.setCategory(additionalDetails.getCategory());
            existingUser.setDescription(additionalDetails.getDescription());
            existingUser.setIsComplete(true);
            byte[] compressedImage = ImageUtil.compress(file.getBytes());
            existingUser.setImageData(compressedImage);
            customerRepo.save(existingUser);
            return true;
        }else {
            return false;
        }
    }
//    public byte[] getImage(Integer id) {
//        return customerRepo.findById(id)
//                .map(entity -> ImageUtil.decompress(entity.getImageData()))
//                .orElseGet(this::getDefaultImage);
//    }
public byte[] getImage(Integer id) {
    return customerRepo.findById(id)
            .map(entity -> {
                if (entity.getImageData() == null) {
                    return getDefaultImage();
                }
                return ImageUtil.decompress(entity.getImageData());
            })
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
