package com.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Unit tests of {@link HomeController}.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {HomeControllerTest.HomeControllerTestConfig.class})
public class HomeControllerTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Configuration
    public static class HomeControllerTestConfig {
        @Bean
        public HomeController homeController() {
            return new HomeController();
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
    public void showWelcomePage() throws Exception {
        ResultMatcher status = MockMvcResultMatchers.status().isOk();
        ResultMatcher view = MockMvcResultMatchers.view().name("welcome");

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status)
                .andExpect(view);
    }

    @Test
    public void showContactsPage() throws Exception {
        ResultMatcher status = MockMvcResultMatchers.status().isOk();
        ResultMatcher view = MockMvcResultMatchers.view().name("contacts");

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/contacts"))
                .andExpect(status)
                .andExpect(view);
    }
}