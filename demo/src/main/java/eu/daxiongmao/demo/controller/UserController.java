package eu.daxiongmao.demo.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import eu.daxiongmao.demo.dao.UserRepository;
import eu.daxiongmao.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final UserRepository userRepository;

    /** Thread-safe JSON writer with pretty printing. */
    public static final ObjectWriter JSON_WRITER_PRETTY = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .writerWithDefaultPrettyPrinter();


    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String homepage(Model uiModel) {
        final List<User> users = new ArrayList<>();
        if (userRepository.count() > 0) {
            userRepository.findAll().forEach(users::add);
        }
        uiModel.addAttribute("users", users);
        return "index";
    }

    /**
     * Get user by email, if any
     * @param email searched email
     * @return result in JSON format (will be serialized by Spring automatically)
     */
    @GetMapping(value = "/getByEmail/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getByEmail(@NotBlank @PathVariable(name = "email") String email) throws JsonProcessingException {
        return JSON_WRITER_PRETTY.writeValueAsString(userRepository.getByEmail(email).orElse(null));
    }


    /**
     * To display the add user form
     */
    @GetMapping(value = "/signup")
    public String addUser(final User emptyUser) {
        // Parameter will be injected in thymleaf template automatically
        return "add-user";
    }

    /**
     * To create a new user in DB and redirect user accordingly.
     * <ul>
     *     <li>OK: User will see the new user's details as saved in DB</li>
     *     <li>KO: user will be redirected to registration form</li>
     * </ul>
     * @param newUser user to validate and add in DB, if OK
     * @param validationResult spring auto-validation result, based on DTO annotations
     * @param uiModel spring webMVC, UI model to update
     * @return name of the page to display. See "templates"
     */
    @PostMapping(value = "/addUser")
    public String addUser(@Valid User newUser, BindingResult validationResult, Model uiModel) {
        if (validationResult.hasErrors()) {
            // Validation failure: redirect user to registration form
            return "add-user";
        }

        final User dbUser = userRepository.save(newUser);
        uiModel.addAttribute("user", dbUser);
        return "redirect:/index";
    }

    // https://www.baeldung.com/spring-boot-crud-thymeleaf

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "update-user";
    }
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setUserId(id);
            return "update-user";
        }

        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }
}
