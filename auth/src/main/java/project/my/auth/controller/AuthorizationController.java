package project.my.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.my.auth.entity.UserRole;
import project.my.auth.request.SignupRequest;
import project.my.auth.response.JwtAuthenticationResponse;
import project.my.auth.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AuthorizationController {

    @Autowired
    private final AuthenticationService authenticationService;
    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("This is a test page");
    }

    @PostMapping("/signupKita")
    public ResponseEntity<JwtAuthenticationResponse> signupKita(@RequestBody SignupRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request, UserRole.KITA));
    }
}