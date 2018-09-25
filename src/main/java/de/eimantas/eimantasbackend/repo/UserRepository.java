package de.eimantas.eimantasbackend.repo;

import de.eimantas.eimantasbackend.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin(origins = "*")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

  public User findByName(String name);

  public User findByKeycloackId(String keycloackId);

}

