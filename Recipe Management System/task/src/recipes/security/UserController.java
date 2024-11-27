package recipes.security;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/register")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public User postAuthSignUp(@RequestBody @Valid User user) {
        return userService.signup(user);
    }
}
