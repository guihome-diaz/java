package eu.daxiongmao.prv.server.dao.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.daxiongmao.prv.server.dao.model.UserDB;

@Transactional
public interface UserRepository extends JpaRepository<UserDB, Long> {

    UserDB findByEmail(String email);
}
