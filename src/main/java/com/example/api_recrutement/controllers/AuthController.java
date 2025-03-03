package com.example.api_recrutement.controllers;

        import com.example.api_recrutement.models.*;
        import com.example.api_recrutement.services.AuthService;
        import com.example.api_recrutement.services.UserService;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

        import java.util.HashMap;
        import java.util.Map;


@RestController
        @RequestMapping("/api/auth")
        public class AuthController {
            public final AuthService authService;
            public final UserService userService;

            @Autowired
            public AuthController(AuthService authService, UserService userService) {
                this.authService = authService;
                this.userService = userService;
            }

            private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

            @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
            public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
                AuthResponse response = authService.login(request);
                return ResponseEntity.ok(response);
            }

            @PostMapping("/register")
            public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
                try {
                    authService.register(registerRequest);
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "User registered successfully");
                    return ResponseEntity.ok(response);
                } catch (Exception e) {
                    Map<String, String> errorResponse = new HashMap<>();
                    errorResponse.put("error", e.getMessage());
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
                }
            }


        }