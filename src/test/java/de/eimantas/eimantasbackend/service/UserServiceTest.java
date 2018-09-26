package de.eimantas.eimantasbackend.service;

import de.eimantas.eimantasbackend.TestUtils;
import de.eimantas.eimantasbackend.entities.User;
import de.eimantas.eimantasbackend.repo.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class UserServiceTest {

  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

  private MockMvc mockMvc;


  @Inject
  private UserRepository userRepository;

  @Inject
  private UserService userService;

  @Autowired
  private WebApplicationContext webApplicationContext;


  private User usr;


  private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
  private KeycloakAuthenticationToken mockPrincipal;

  @Before
  public void setup() throws Exception {


    userRepository.deleteAll();
    // auth stuff
    mockPrincipal = Mockito.mock(KeycloakAuthenticationToken.class);
    Mockito.when(mockPrincipal.getName()).thenReturn("test@test.de");


    this.mockMvc = webAppContextSetup(webApplicationContext).build();

    usr = TestUtils.getUser();

    userRepository.save(usr);

  }


  @Test
  public void getUserByKeyCloackId() throws Exception {

    User usr = userService.getUserFromID("KEY-1");
    assertThat(usr).isNotNull();
    assertThat(usr.getName()).isEqualTo("test@test.de");

  }

  @Test
  public void testSaveUser() throws Exception {
    User userNew = TestUtils.getUser();
    userNew.setJoindate(null);
    userNew.setName("saved");
    userNew.setKeycloackId("KEYID-2");
    userNew.setUsername("saved");
    userNew.setEmail("test@saved.de");
    User saved = userService.saveUser(userNew);
    assertThat(saved).isNotNull();
    assertThat(saved.getJoindate()).isNotNull();
    assertThat(saved.getId()).isNotNull();
    assertThat(saved.getName()).isEqualTo(userNew.getName());
    User usrretrieved = userRepository.findByUsername("saved");
    logger.info(usrretrieved.toString());

  }


}
