package de.eimantas.eimantasbackend;

import de.eimantas.eimantasbackend.entities.User;

import java.time.LocalDate;

public class TestUtils {


  public static User getUser() {
    User user = new User();
    user.setEmail("test@test.de");
    user.setKeycloackId("KEY-1");
    user.setName("test@test.de");
    user.setUsername("test@test.de");
    user.setJoindate(LocalDate.now());
    return user;
  }

}
