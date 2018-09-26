package de.eimantas.eimantasbackend.controller;

import de.eimantas.eimantasbackend.TestUtils;
import de.eimantas.eimantasbackend.entities.User;
import de.eimantas.eimantasbackend.entities.converter.EntitiesConverter;
import de.eimantas.eimantasbackend.entities.dto.UserDTO;
import de.eimantas.eimantasbackend.repo.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class UserControllerTest {

  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

  private MockMvc mockMvc;

  @SuppressWarnings("rawtypes")
  private HttpMessageConverter mappingJackson2HttpMessageConverter;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private EntitiesConverter entitiesConverter;


  private User usr;

  private KeycloakAuthenticationToken mockPrincipal;


  // @Autowired
  // private ExpenseController controller;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  void setConverters(HttpMessageConverter<?>[] converters) {

    this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
        .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

    assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
  }

  @Before
  public void setup() throws Exception {


    userRepository.deleteAll();
    // auth stuff
    mockPrincipal = Mockito.mock(KeycloakAuthenticationToken.class);
    Mockito.when(mockPrincipal.getName()).thenReturn("test");

    KeycloakPrincipal keyPrincipal = Mockito.mock(KeycloakPrincipal.class);
    RefreshableKeycloakSecurityContext ctx = Mockito.mock(RefreshableKeycloakSecurityContext.class);

    User usr = TestUtils.getUser();

    AccessToken token = Mockito.mock(AccessToken.class);
    Mockito.when(token.getSubject()).thenReturn("KEY-1");
    Mockito.when(ctx.getToken()).thenReturn(token);
    Mockito.when(keyPrincipal.getKeycloakSecurityContext()).thenReturn(ctx);
    Mockito.when(mockPrincipal.getPrincipal()).thenReturn(keyPrincipal);

    this.mockMvc = webAppContextSetup(webApplicationContext).build();
    userRepository.save(usr);
  }

  @Test
  public void getUser() throws Exception {
    // given(controller.principal).willReturn(allEmployees);
    mockMvc.perform(get("/user/get/" + "KEY-1").principal(mockPrincipal)).andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print()).andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.name", is("test@test.de")));
  }


  @Test
  public void testSaveUser() throws Exception {

    User userNew = TestUtils.getUser();
    userNew.setJoindate(null);
    userNew.setName("saved");
    userNew.setUsername("saved");
    userNew.setEmail("test@saved.de");

    UserDTO dto = entitiesConverter.getDtoFromUser(userNew);

    String bookmarkJson = json(dto);


    // given(controller.principal).willReturn(allEmployees);
    mockMvc.perform(post("/user/save").content(bookmarkJson).contentType(contentType).principal(mockPrincipal)).andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print()).andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.name", is("saved")));
  }


  @Test
  public void getCurrentUser() throws Exception {
    // given(controller.principal).willReturn(allEmployees);
    mockMvc.perform(get("/user/me").principal(mockPrincipal)).andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print()).andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.name", is("test@test.de")));
  }

  @SuppressWarnings("unchecked")
  protected String json(Object o) throws IOException {
    MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
    this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
    return mockHttpOutputMessage.getBodyAsString();
  }

}
