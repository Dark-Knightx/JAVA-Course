package com.example.BOWO.Security;

import com.example.BOWO.Modal.CMS;
import com.example.BOWO.Modal.Role;
import com.example.BOWO.Repo.CmsRepo;
import com.example.BOWO.Repo.RoleRepo;
import com.example.BOWO.Service.CustomUserDetailsService;
import com.example.BOWO.Repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class MyConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public MyConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/regg/**","/assets/**","/loginPageAdmin","/registerr", "/css/**", "/js/**", "/image/**").permitAll()
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
                        .logoutSuccessUrl("/loginPage?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

//    @Bean
//    public CommandLineRunner initializeUsers(UserRepository userRepository, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
//        return args -> {
//            if (userRepository.findByEmail("ss@gmail.com").isEmpty()) {
//                com.example.BOWO.Modal.User user = new com.example.BOWO.Modal.User();
//                user.setUsername("Sykku");
//                user.setEmail("ss@gmail.com");
//                user.setPassword(passwordEncoder.encode("aaaaa")); // Encrypt password
//
//                Role role = new Role();
//                role.setName("USER");
//                roleRepo.save(role);
//                user.getRoles().add(role);
//                userRepository.save(user);
//            }
//
//            if (userRepository.findByEmail("sss@gmail.com").isEmpty()) {
//                com.example.BOWO.Modal.User user = new com.example.BOWO.Modal.User();
//                user.setUsername("Sykkk");
//                user.setEmail("sss@gmail.com");
//                user.setPassword(passwordEncoder.encode("sssss")); // Encrypt password
//
//                Role role = new Role();
//                role.setName("ADMIN");
//                roleRepo.save(role);
//                user.getRoles().add(role);
//                userRepository.save(user);
//            }
//        };
//    }

//        @Bean
//    public CommandLineRunner initializeUsers2(CmsRepo cmsRepo) {
//        return args -> {
//            CMS cms = new CMS();
//
//            cms.setName("Sykkk");
//            cms.setCms(
//                    "A privacy policy typically includes information about:\n" +
//                            "Data collection: How data is collected, such as through cookies or user entry\n" +
//                            "Data storage: How data is stored, such as through encryption or service locality\n" +
//                            "Data use: How data is used, such as for marketing, usability, or service functionality\n" +
//                            "Data sharing: If data is shared or sold to third parties or partners \n" +
//                            "A privacy policy is legally required, while a Terms and Conditions agreement is not. \n" + "A Terms and Conditions agreement explains how users can interact with a site or service, \n"+"while a Privacy Policy explains how the company will interact with a user's personal information."
//            );
//            cmsRepo.save(cms);
//
//        };
//    }

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
}
