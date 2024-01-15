    package clotheswarehouse.clotheswarehouse.controller;

    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;

    import clotheswarehouse.clotheswarehouse.model.form.RegistrationForm;
    import clotheswarehouse.clotheswarehouse.repository.UserRepository;
    import org.slf4j.Logger; // Import for Logger
    import org.slf4j.LoggerFactory;

    @Controller
    @RequestMapping("/register")
    public class RegistrationController {
        private UserRepository userRepository;
        private PasswordEncoder passwordEncoder;

        private Logger logger = LoggerFactory.getLogger(RegistrationController.class);


        public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
        }

        @GetMapping
        public String showRegistrationForm() {
            return "authentication/register";
        }

        @PostMapping
        public String registerUserAccount(RegistrationForm form) {
            userRepository.save(form.toUser(passwordEncoder));
            return "redirect:/";
        }
    }
