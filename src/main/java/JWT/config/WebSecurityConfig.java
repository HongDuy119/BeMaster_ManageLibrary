package JWT.config;

import JWT.security.jwt.JwtEntryPoint;
import JWT.security.jwt.JwtTokenFilter;
import JWT.security.userprincal.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig{
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    private JwtEntryPoint jwtEntryPoint;
    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return new JwtTokenFilter();
    }
//    @Bean
//    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder.userDetailsService(userDetailService)
//                .passwordEncoder(passwordEncoder());
//    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    private static final String[] ADMIN_URLs = {
            "api/book/editBook/**",
            "api/book/addBook",
            "api/book/deleteBook/**",
            "api/book/getAllbyAdmin",
            "api/book/getAdmin/**",
            "api/buybook/getAll",
            "api/auth/getAll",
    };
    private static final String[] PERMIT_URLs = {
            "/",
            "**",
            "/api/auth/signin",
            "/api/auth/signup",
            "api/auth/getUser",
            "api/auth/updateProfile",
            "api/auth/updateAvatar",
            "api/book/getAll",
            "api/book/**",
            "api/book/getbytitle",
            "api/comment/get/**",
            "api/comment/add/**",
            "/api/buybook/get",
            "/api/buybook/addbuy/**",
            "api/buybook/editStatus/**",
            "api/buybook/delete/**",


    };
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(ADMIN_URLs).hasAuthority("ADMIN")
                .and()
                .authorizeHttpRequests()
                .requestMatchers(PERMIT_URLs).permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling()
                .authenticationEntryPoint(jwtEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
       httpSecurity.addFilterBefore(jwtTokenFilter(),UsernamePasswordAuthenticationFilter.class);
       return httpSecurity.build();
    }
}
