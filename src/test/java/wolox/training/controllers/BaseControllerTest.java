package wolox.training.controllers;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class BaseControllerTest {

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  private WebApplicationContext context;
  public void buildMvc(){
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();
  }

  public ResultActions deleteAndExpect(String url, ResultMatcher status, String content)
      throws Exception {
    return deleteWithStatus(url, status).andExpect(content().json(content));
  }

  public ResultActions getAndExpect(String url, ResultMatcher status, String content)
      throws Exception {
    return getWithStatus(url, status).andExpect(content().json(content));
  }

  public ResultActions deleteWithStatus(String url, ResultMatcher status) throws Exception {
    return methodWithNoContent(delete(url), url).andExpect(status);
  }

  public ResultActions getWithStatus(String url, ResultMatcher status) throws Exception {
    return methodWithNoContent(get(url), url).andExpect(status);
  }

  public ResultActions putWithStatus(String url, String content, ResultMatcher status)
      throws Exception {
    return methodWithContent(put(url), url, content).andExpect(status);
  }

  public ResultActions postWithStatus(String url, String content, ResultMatcher status)
      throws Exception {
    return methodWithContent(post(url), url, content).andExpect(status);
  }

  private ResultActions methodWithContent(MockHttpServletRequestBuilder method, String url,
      String content) throws Exception {
    return mockMvc.perform(method
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(content)
    );
  }

  private ResultActions methodWithNoContent(MockHttpServletRequestBuilder method, String url)
      throws Exception {
    return mockMvc.perform(method
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
    );
  }
}
