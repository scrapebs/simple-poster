package com.denissinkov.simpleposter;

import com.denissinkov.simpleposter.controller.PosterController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("test-user")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/posts-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/posts-list-after.sql", "/create-user-after.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PosterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PosterController controller;

    @Test
    public void postsPageTest() throws Exception {
        this.mockMvc.perform(get("/post"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='navbarSupportedContent']/div").string("test-user"));
    }

    @Test
    public void postListTest() throws Exception {
        this.mockMvc.perform(get("/post"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='posts-list']/div").nodeCount(4));
    }

    @Test
    public void filterPostTest() throws Exception {
        this.mockMvc.perform(get("/post").param("filter", "denis"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='posts-list']/div").nodeCount(2))
                .andExpect(xpath("//*[@id='posts-list']/div[@data-id=1]").exists())
                .andExpect(xpath("//*[@id='posts-list']/div[@data-id=4]").exists());
    }

    @Test
    public void addPostToListTest() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/post")
                .file("file", "123".getBytes())
                .param("text", "text of the fifth post")
                .param("author", "2")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/post"));

        this.mockMvc.perform(get("/post"))
                .andExpect(xpath("//*[@id='posts-list']/div").nodeCount(5))
                .andExpect(xpath("//*[@id='posts-list']/div[@data-id=10]").exists())
                .andExpect(xpath("//*[@id='posts-list']/div[@data-id=10]/div/span").string("text of the fifth post"));

    }


}
