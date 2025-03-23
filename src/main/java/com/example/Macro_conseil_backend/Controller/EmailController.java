package com.example.Macro_conseil_backend.Controller;

import com.example.Macro_conseil_backend.Services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String email = request.get("email");
        String message = request.get("message");

        emailService.sendEmail(name, email, message);
        return ResponseEntity.ok("Email sent successfully!");
    }
}
