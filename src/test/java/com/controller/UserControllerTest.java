package com.controller;

import com.model.User;
import com.service.SecurityService;
import com.service.UserService;
import com.util.UserValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.Validator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {UserControllerTest.UserControllerTestConfig.class})
public class UserControllerTest {
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Configuration
    public static class UserControllerTestConfig {
        @Bean
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }

        @Bean
        public SecurityService securityService() {
            return Mockito.mock(SecurityService.class);
        }

        @Bean
        @Qualifier("userValidator")
        public Validator validator() {
            return new UserValidator(userService());
        }

        @Bean
        public UserController userController() {
            return new UserController(userService(), securityService(), validator());
        }

        @Bean
        public InternalResourceViewResolver viewResolver() {
            InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
            viewResolver.setViewClass(JstlView.class);
            viewResolver.setPrefix("/WEB-INF/views/pages/");
            viewResolver.setSuffix(".jsp");
            return viewResolver;
        }
    }

    @Test
    public void showLoginPageWithoutErrorAndMessage() throws Exception {
        ResultMatcher status = MockMvcResultMatchers.status().isOk();
        ResultMatcher view = MockMvcResultMatchers.view().name("login");

        this.mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(status)
                .andExpect(view);
    }

    @Test
    public void showLoginPageWithError() throws Exception {
        ResultMatcher status = MockMvcResultMatchers.status().isOk();
        ResultMatcher error = MockMvcResultMatchers.model().attribute("error", "Username or password is incorrect.");
        ResultMatcher view = MockMvcResultMatchers.view().name("login");

        this.mockMvc.perform(MockMvcRequestBuilders.get("/login").param("error", "Username or password is incorrect."))
                .andExpect(status)
                .andExpect(error)
                .andExpect(view);
    }

    @Test
    public void showLoginPageWithMessage() throws Exception {
        ResultMatcher status = MockMvcResultMatchers.status().isOk();
        ResultMatcher message = MockMvcResultMatchers.model().attribute("message", "Logged out successfully.");
        ResultMatcher view = MockMvcResultMatchers.view().name("login");

        this.mockMvc.perform(MockMvcRequestBuilders.get("/login").param("logout", "Logged out successfully."))
                .andExpect(status)
                .andExpect(message)
                .andExpect(view);
    }

    @Test
    public void showLoginPageWithErrorAndMessage() throws Exception {
        ResultMatcher status = MockMvcResultMatchers.status().isOk();
        ResultMatcher error = MockMvcResultMatchers.model().attribute("error", "Username or password is incorrect.");
        ResultMatcher message = MockMvcResultMatchers.model().attribute("message", "Logged out successfully.");
        ResultMatcher view = MockMvcResultMatchers.view().name("login");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("error", "Username or password is incorrect.");
        map.add("logout", "Logged out successfully.");

        this.mockMvc.perform(MockMvcRequestBuilders.get("/login").params(map))
                .andExpect(status)
                .andExpect(error)
                .andExpect(message)
                .andExpect(view);
    }

    @Test
    public void showRegistrationForm() throws Exception {
        User user = new User();

        ResultMatcher status = MockMvcResultMatchers.status().isOk();
        ResultMatcher model = MockMvcResultMatchers.model().attribute("userForm", user);
        ResultMatcher view = MockMvcResultMatchers.view().name("registration");

        this.mockMvc.perform(MockMvcRequestBuilders.get("/registration"))
                .andExpect(status)
                .andExpect(model)
                .andExpect(view);
    }

    @Test
    public void newUserRegistration_whenBindingResultHasErrors() throws Exception {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("12345678");
        user.setConfirmPassword("12345679"); //error

        ResultMatcher status = MockMvcResultMatchers.status().isOk();
        ResultMatcher view = MockMvcResultMatchers.view().name("registration");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", user.getUsername());
        map.add("password", user.getPassword());
        map.add("confirmPassword", user.getConfirmPassword());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/registration").params(map))
                .andExpect(status)
                .andExpect(view);

        verify(userService, never()).save(user);
        verify(securityService, never()).autoLogin(user.getUsername(), user.getConfirmPassword());
    }

    @Test
    public void newUserRegistration_whenBindingResultHasNotErrors() throws Exception {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("12345678");
        user.setConfirmPassword("12345678");

        ResultMatcher status = MockMvcResultMatchers.status().isFound();
        ResultMatcher view = MockMvcResultMatchers.view().name("redirect:/");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", user.getUsername());
        map.add("password", user.getPassword());
        map.add("confirmPassword", user.getConfirmPassword());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/registration").params(map))
                .andExpect(status)
                .andExpect(view);

        verify(userService, times(1)).save(user);
        verify(securityService, times(1)).autoLogin(user.getUsername(), user.getConfirmPassword());
    }

    @Test
    public void showAllUsersTest() throws Exception {
        List<User> users = new ArrayList<>();

        ResultMatcher status = MockMvcResultMatchers.status().isOk();
        ResultMatcher model = MockMvcResultMatchers.model().attribute("users", users);
        ResultMatcher view = MockMvcResultMatchers.view().name("users");

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status)
                .andExpect(model)
                .andExpect(view);
    }
}