package ci.esatic.security;

import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SecurityController {

    @GetMapping("/notAuthorize")
    public String notAuthorize()
    {
        return "403";
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

}
