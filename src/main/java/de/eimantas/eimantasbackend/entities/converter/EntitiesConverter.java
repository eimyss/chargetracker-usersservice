package de.eimantas.eimantasbackend.entities.converter;

import de.eimantas.eimantasbackend.entities.User;
import de.eimantas.eimantasbackend.entities.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class EntitiesConverter {

  @Autowired
  private ModelMapper modelMapper;


  private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());


  public User getUserFromDTO(UserDTO dto) {

    if (dto != null) {
      User user = modelMapper.map(dto, User.class);
      return user;

    }
    logger.info("dto is null");
    return null;


  }

  public UserDTO getDtoFromUser(User user) {

    if (user != null) {
      UserDTO dto = modelMapper.map(user, UserDTO.class);
      return dto;

    }
    logger.info("user is null");
    return null;
  }

}
