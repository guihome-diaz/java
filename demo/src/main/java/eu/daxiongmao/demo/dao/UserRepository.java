package eu.daxiongmao.demo.dao;

import eu.daxiongmao.demo.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * To interact with user table
 * @version 1.0
 * @since 2020-10
 * @author Guillaume Diaz
 */
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> getByFirstNameAndLastName(String firstName, String lastName);

    Optional<User> getByEmail(String email);

}
