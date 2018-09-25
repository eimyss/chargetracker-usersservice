package de.eimantas.eimantasbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate joindate;
    private @NonNull
    @Column(unique = true)
    String name;
    private @NonNull
    String username;
    private String email;
    private String keycloackId;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", joindate=" + joindate +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", keycloackId='" + keycloackId + '\'' +
                '}';
    }
}
