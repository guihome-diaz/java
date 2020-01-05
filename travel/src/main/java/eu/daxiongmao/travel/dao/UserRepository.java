package eu.daxiongmao.travel.dao;

import eu.daxiongmao.travel.model.db.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * To interact with "users" table
 * @author Guillaume Diaz
 * @version 1.0 2019/11
 * @since application creation
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
