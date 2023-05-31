package JWT.controller;


import JWT.dto.request.BookDTO;
import JWT.dto.request.SignInForm;
import JWT.dto.request.SignUpForm;
import JWT.dto.respone.JwtResponse;
import JWT.dto.respone.ResponMessage;
import JWT.enity.Role;
import JWT.enity.RoleName;
import JWT.enity.User;
import JWT.security.jwt.JwtProvider;
import JWT.security.userprincal.UserPrinciple;
import JWT.service.impl.RoleServiceImpl;
import JWT.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequestMapping("/api/auth")
@RestController
@CrossOrigin
public class AuthController {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) {
//        System.out.println(signUpForm.getEmail());
        if (userServiceImpl.existsByUsername(signUpForm.getUsername())) {
            return new ResponseEntity<>(new ResponMessage("nouser"), HttpStatus.OK);
        }
        if (userServiceImpl.existsByEmail(signUpForm.getEmail())) {
            return new ResponseEntity<>(new ResponMessage("noemail"), HttpStatus.OK);
        }
        User user = new User(signUpForm.getName(), signUpForm.getUsername(), signUpForm.getEmail(), passwordEncoder.encode(signUpForm.getPassword()));
        Set<String> strRoles = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(
                            () -> new RuntimeException("Role not found")
                    );
                    roles.add(adminRole);
                    break;
                case "pm":
                    Role pmRole = roleService.findByName(RoleName.PM).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(pmRole);
                    break;
                default:
                    Role userRole = roleService.findByName(RoleName.USER).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(userRole);
            }
        });
        user.setRoles(roles);
        userServiceImpl.save(user);
        return new ResponseEntity<>(new ResponMessage("yes"), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(token,userPrinciple.getId(), userPrinciple.getName(), userPrinciple.getAuthorities()));
    }
    // Get thông tin người dùng
    @GetMapping("/getUser")
    public ResponseEntity<User> getUser(HttpServletRequest request){
        return ResponseEntity.ok(userServiceImpl.getInfor(request));
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<String> updateProfile(@RequestParam("request1") String request1,HttpServletRequest request) throws JsonProcessingException {
        User user = objectMapper.readValue(request1, User.class);
        return ResponseEntity.ok(userServiceImpl.updateProfile(user,request));
    }

    @PutMapping("/updateAvatar")
    public ResponseEntity<String> updateAvatar(@RequestParam("image") MultipartFile image,HttpServletRequest request) throws IOException {
        return ResponseEntity.ok(userServiceImpl.updateAvatar(request,image));
    }

    // Get AllUser by Admin
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll()
    {
        return ResponseEntity.ok(userServiceImpl.findAll());
    }
}
