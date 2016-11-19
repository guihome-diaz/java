package eu.daxiongmao.prv.server.dao.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import eu.daxiongmao.prv.server.dao.model.AppProperty;

@Transactional
public interface AppPropertyRepository extends JpaRepository<AppProperty, Long> {

    AppProperty findByKey(@Param("key") String key);

}
