package gogo.com.gogo_kan.security;

import gogo.com.gogo_kan.security.jwt.AuthEntryPointJwt;
import gogo.com.gogo_kan.security.jwt.AuthTokenFilter;
import gogo.com.gogo_kan.services.impl.user.CustomeUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.CodeFlow;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.sql.DataSource;
import java.security.CodeSigner;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private AuthEntryPointJwt authEntryPointJwt;
    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomeUserDetailsImpl customeUserDetails;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequest ->
                authorizeRequest.requestMatchers("/signin").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/send-mail").permitAll()
                        .anyRequest().authenticated()
        );
        http.sessionManagement(
                session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
        );
        http.headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin
                )
        );
        http.csrf(AbstractHttpConfigurer::disable);
        http.addFilterBefore(authenticationJwtTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customeUserDetails);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }
//
//
//    @Bean
//    public CommandLineRunner initData(UserDetailsService userDetailsService) {
//        return args -> {
//            JdbcUserDetailsManager manager = (JdbcUserDetailsManager) userDetailsService;
//            UserDetails user1 = User.withUsername("user1")
//                    .password(passwordEncoder().encode("password1"))
//                    .roles("USER")
//                    .build();
//
//            UserDetails admin = User.withUsername("admin")
//                    .password(passwordEncoder().encode("123"))
//                    .roles("ADMIN")
//                    .build();
//
////            gogo.com.gogo_kan.models.User user = new gogo.com.gogo_kan.models.User();
//
//            JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
//            userDetailsManager.createUser(user1);
//            userDetailsManager.createUser(admin);
////            userDetailsManager.createUser(user);
//        };
//
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }




    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:5174"));
        corsConfiguration.addAllowedHeader("Authorization");
        corsConfiguration.addAllowedHeader("Content-Type");
        corsConfiguration.addAllowedHeader("Accept");
        corsConfiguration.addAllowedMethod("POST");
        corsConfiguration.addAllowedMethod("GET");
        corsConfiguration.addAllowedMethod("DELETE");
        corsConfiguration.addAllowedMethod("PUT");
        corsConfiguration.addAllowedMethod("UPDATE");
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

}
