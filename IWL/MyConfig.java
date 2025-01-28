//package com.example.BOWO.Security;
//
//import com.example.BOWO.Modal.Bank;
//import com.example.BOWO.Modal.CMS;
//import com.example.BOWO.Modal.Role;
//import com.example.BOWO.Repo.BankRepo;
//import com.example.BOWO.Repo.CmsRepo;
//import com.example.BOWO.Repo.RoleRepo;
//import com.example.BOWO.Service.CustomUserDetailsService;
//import com.example.BOWO.Repo.UserRepository;
//import com.warrenstrange.googleauth.GoogleAuthenticator;
//import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class MyConfig {
//
//    private final CustomUserDetailsService customUserDetailsService;
//
//    public MyConfig(CustomUserDetailsService customUserDetailsService) {
//        this.customUserDetailsService = customUserDetailsService;
//    }
//
//    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();
//    GoogleAuthenticatorKey key = gAuth.createCredentials();
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        String allowedIp = "1.1.1.1";
//        http
//                .csrf(csrf -> csrf.disable())
//                .cors(cors -> cors.disable())
//                .authorizeHttpRequests(req -> req
//                        .requestMatchers("/static/**","/loginPage","/verify2fa","/verify-2faPage","verify-2fa","/images/**","/regg/**","/assets/**","/loginPageAdmin","/registerr", "/css/**", "/js/**", "/image/**").permitAll()
//                        .requestMatchers("/user/**").hasRole("USER")
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/loginPage")
//                        .successHandler(customAuthSuccessHandler())
//                        .failureHandler(customAuthFailureHandler())
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessHandler(customLogoutSuccessHandler())
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                        .permitAll()
//                )
//                .exceptionHandling(exceptions -> exceptions
//                        .accessDeniedHandler((request, response, accessDeniedException) -> {
//                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                            response.getWriter().write("Access Denied: " + accessDeniedException.getMessage());
//                        })
//        );
//
//        return http.build();
//    }
//
//    @Bean
//    public CommandLineRunner initializeUsers(CmsRepo cmsRepo, UserRepository userRepository, BankRepo bankRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
//        return args -> {
//            if (userRepository.findByEmail("ssaa@gmail.com").isEmpty()) {
//                com.example.BOWO.Modal.User user = new com.example.BOWO.Modal.User();
//                user.setUsername("Sykku");
//                user.setEmail("ssaa@gmail.com");
//                user.setPassword(passwordEncoder.encode("aaaaa")); // Encrypt password
//
//                Role role = new Role();
//                role.setName("USER");
//                roleRepo.save(role);
//                user.getRoles().add(role);
//
//                Bank bank = new Bank();
//                bank.setBankName("YesBank");
//                bank.setBalance(10000);
//                bank.setAccountNumber("12454545");
//                bankRepo.save(bank);
//                user.getBankDetails().add(bank);
//
//                userRepository.save(user);
//            }
//
//            if (userRepository.findByEmail("ssd@gmail.com").isEmpty()) {
//                com.example.BOWO.Modal.User user = new com.example.BOWO.Modal.User();
//                user.setUsername("Sykkk");
//                user.setEmail("ssd@gmail.com");
//                user.setPassword(passwordEncoder.encode("sssss")); // Encrypt password
//                user.set2faEnabled(false); // Disable 2FA by default
//                user.setSecretKey(key.getKey());
//
//                Bank bank = new Bank();
//                bank.setBankName("YesBank");
//                bank.setBalance(20000);
//                bank.setAccountNumber("12434545");
//                bank.setVerified(true);
//                user.getBankDetails().add(bank);
//
//                Role role = new Role();
//                role.setName("ADMIN");
//                user.getRoles().add(role);
//
//
//
//                roleRepo.save(role);
//                bankRepo.save(bank);
//                userRepository.save(user);
//            }
//            if (cmsRepo.findByName("Sykkk")==null) {
//                CMS cms = new CMS();
//
//                cms.setName("Sykkk");
//                cms.setCms(
//                        "\nA privacy policy typically includes information about:\n" +
//                                "Data collection: How data is collected, such as through cookies or user entry\n" +
//                                "Data storage: How data is stored, such as through encryption or service locality\n" +
//                                "Data use: How data is used, such as for marketing, usability, or service functionality\n" +
//                                "Data sharing: If data is shared or sold to third parties or partners \n" +
//                                "A privacy policy is legally required, while a Terms and Conditions agreement is not. \n" + "A Terms and Conditions agreement explains how users can interact with a site or service, \n" + "while a Privacy Policy explains how the company will interact with a user's personal information."
//                );
//                cmsRepo.save(cms);
//            }
//        };
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public CustomAuthenticationSuccessHandler customAuthSuccessHandler() {
//        return new CustomAuthenticationSuccessHandler();
//    }
//
//    @Bean
//    public CustomAuthenticationFailureHandler customAuthFailureHandler() {
//        return new CustomAuthenticationFailureHandler();
//    }
//
//    @Bean
//    public CustomLogoutSuccessHandler customLogoutSuccessHandler() {
//        return new CustomLogoutSuccessHandler();
//    }
//
//}
//
//
//

package com.example.BOWO.Security;

import com.example.BOWO.Modal.Bank;
import com.example.BOWO.Modal.CMS;
import com.example.BOWO.Modal.Role;
import com.example.BOWO.Repo.BankRepo;
import com.example.BOWO.Repo.CmsRepo;
import com.example.BOWO.Repo.RoleRepo;
import com.example.BOWO.Service.CustomUserDetailsService;
import com.example.BOWO.Repo.UserRepository;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
public class MyConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public MyConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();
    GoogleAuthenticatorKey key = gAuth.createCredentials();

    // Define your whitelisted IPs here
    private static final List<String> ALLOWED_IPS = Arrays.asList("0:0:0:0:0:0:0:1", "192.168.31.100"); // Add your IPs here

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/static/**", "/loginPage", "/verify2fa", "/verify-2faPage", "/verify-2fa", "/images/**", "/regg/**", "/assets/**", "/loginPageAdmin", "/registerr", "/css/**", "/js/**", "/image/**").permitAll()
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/loginPage")
                        .successHandler(customAuthSuccessHandler())
                        .failureHandler(customAuthFailureHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(customLogoutSuccessHandler())
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.getWriter().write("Access Denied: " + accessDeniedException.getMessage());
                        })
                )
                .addFilterBefore(ipWhitelistFilter(), BasicAuthenticationFilter.class); // Add the IP whitelist filter

        return http.build();
    }

    @Bean
    public OncePerRequestFilter ipWhitelistFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                String clientIp = request.getRemoteAddr();
                System.out.println(clientIp);

                if (!ALLOWED_IPS.contains(clientIp)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Access Denied: Your IP is not allowed.");
                    return;
                }

                filterChain.doFilter(request, response);
            }
        };
    }

    @Bean
    public CommandLineRunner initializeUsers(CmsRepo cmsRepo, UserRepository userRepository, BankRepo bankRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("ssaa@gmail.com").isEmpty()) {
                com.example.BOWO.Modal.User user = new com.example.BOWO.Modal.User();
                user.setUsername("Sykku");
                user.setEmail("ssaa@gmail.com");
                user.setPassword(passwordEncoder.encode("aaaaa")); // Encrypt password

                Role role = new Role();
                role.setName("USER");
                roleRepo.save(role);
                user.getRoles().add(role);

                Bank bank = new Bank();
                bank.setBankName("YesBank");
                bank.setBalance(10000);
                bank.setAccountNumber("12454545");
                bankRepo.save(bank);
                user.getBankDetails().add(bank);

                userRepository.save(user);
            }

            if (userRepository.findByEmail("ssd@gmail.com").isEmpty()) {
                com.example.BOWO.Modal.User user = new com.example.BOWO.Modal.User();
                user.setUsername("Sykkk");
                user.setEmail("ssd@gmail.com");
                user.setPassword(passwordEncoder.encode("sssss")); // Encrypt password
                user.set2faEnabled(false); // Disable 2FA by default
                user.setSecretKey(key.getKey());

                Bank bank = new Bank();
                bank.setBankName("YesBank");
                bank.setBalance(20000);
                bank.setAccountNumber("12434545");
                bank.setVerified(true);
                user.getBankDetails().add(bank);

                Role role = new Role();
                role.setName("ADMIN");
                user.getRoles().add(role);

                roleRepo.save(role);
                bankRepo.save(bank);
                userRepository.save(user);
            }
            if (cmsRepo.findByName("Sykkk") == null) {
                CMS cms = new CMS();

                cms.setName("Sykkk");
                cms.setCms(
                        "\nA privacy policy typically includes information about:\n" +
                                "Data collection: How data is collected, such as through cookies or user entry\n" +
                                "Data storage: How data is stored, such as through encryption or service locality\n" +
                                "Data use: How data is used, such as for marketing, usability, or service functionality\n" +
                                "Data sharing: If data is shared or sold to third parties or partners \n" +
                                "A privacy policy is legally required, while a Terms and Conditions agreement is not. \n" + "A Terms and Conditions agreement explains how users can interact with a site or service, \n" + "while a Privacy Policy explains how the company will interact with a user's personal information."
                );
                cmsRepo.save(cms);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationSuccessHandler customAuthSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public CustomAuthenticationFailureHandler customAuthFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public CustomLogoutSuccessHandler customLogoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }
}