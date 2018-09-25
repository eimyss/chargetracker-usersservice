package de.eimantas.eimantasbackend.service;

import de.eimantas.eimantasbackend.entities.User;
import de.eimantas.eimantasbackend.entities.converter.EntitiesConverter;
import de.eimantas.eimantasbackend.repo.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;


@Service
public class UserService {

  @Inject
  SecurityService securityService;
  @Inject
  UserRepository userRepository;


  @Inject
  EntitiesConverter converter;

  private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());


  public User getUserFromID(String keycloackId) {

    return userRepository.findByKeycloackId(keycloackId);

  }
}
