package guru.springframework.spring6restmvc.controller;


import guru.springframework.spring6restmvc.services.EmailService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/mail")
public class EmailSendController {
    private EmailService emailService;

    public EmailSendController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("")
    public String sendMail(@RequestParam(value = "file", required = false) MultipartFile[] file, String to,@RequestParam(required = false) String[] cc, String subject, String body) {
        return emailService.sendMail(file, to, cc, subject, body);
    }

}
