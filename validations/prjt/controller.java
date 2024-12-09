@PostMapping("/saveCustomer")
public ResponseEntity<String> saveCustomer(
    @Valid @RequestBody SaveCustomerRequest request,
    Model model
) {

    Customer customer = request.getCustomer();
    String otp = request.getOtp();
    String email = request.getEmail();
    boolean hasErrors = false;

    // Validate username uniqueness
    if (adminService.isUsernameTaken(customer.getUsername())) {
        hasErrors = true;
        return ResponseEntity.badRequest().body("Username is already taken");
    }

    // Validate mobile number uniqueness
    if (adminService.isMobileTaken(customer.getMobile())) {
        hasErrors = true;
        return ResponseEntity.badRequest().body("Mobile number is already taken");
    }

    // Validate Date of Birth
    if (customer.getDob() == null) {
        hasErrors = true;
        return ResponseEntity.badRequest().body("Date of birth is required");
    }

    LocalDate today = LocalDate.now();
    Period age = Period.between(customer.getDob(), today);
    if (age.getYears() < 18) {
        hasErrors = true;
        return ResponseEntity.badRequest().body("Customer must be at least 18 years old");
    }

    customer.setEmail(email);

    // Validate OTP
    OTPgenerator otpRecord = otpRepo.findByEmail(customer.getEmail());
    if (otpRecord == null || !otpRecord.getOtp().equals(otp)) {
        hasErrors = true;
        return ResponseEntity.badRequest().body("Invalid OTP");
    }

    if (hasErrors) {
        return ResponseEntity.badRequest().body("Customer data validation failed.");
    }

    // Save customer logic if everything is fine
    adminService.saveCustomer(customer);
    return ResponseEntity.ok("Customer successfully saved!");
}
