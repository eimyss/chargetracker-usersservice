package de.eimantas.eimantasbackend.controller;

import de.eimantas.eimantasbackend.controller.exceptions.ErrorDesc;
import de.eimantas.eimantasbackend.controller.exceptions.NonExistingEntityException;
import de.eimantas.eimantasbackend.entities.User;
import de.eimantas.eimantasbackend.entities.converter.EntitiesConverter;
import de.eimantas.eimantasbackend.entities.dto.UserDTO;
import de.eimantas.eimantasbackend.service.SecurityService;
import de.eimantas.eimantasbackend.service.UserService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.security.Principal;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/user")
public class UserController {
  private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());


  @Inject
  private UserService userService;

  @Inject
  private SecurityService securityService;

  @Inject
  private EntitiesConverter converter;

  public UserController() {

  }

  @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @CrossOrigin(origins = "*")
  public UserDTO getUserById(Principal principal, @PathVariable String id) {
    logger.info("getting user for id: " + id);
    User user = userService.getUserFromID(id);

    return converter.getDtoFromUser(user);

  }

  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  @CrossOrigin(origins = "*")
  public UserDTO getCurrentUser(Principal principal) {

    KeycloakAuthenticationToken userAuth = (KeycloakAuthenticationToken) principal;
    String userId = securityService.getUserIdFromPrincipal(userAuth);
    User user = userService.getUserFromID(userId);
    return converter.getDtoFromUser(user);

  }


  @ExceptionHandler(NonExistingEntityException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public @ResponseBody
  ErrorDesc handleException(NonExistingEntityException e) {
    return new ErrorDesc(e.getMessage());
  }


}


