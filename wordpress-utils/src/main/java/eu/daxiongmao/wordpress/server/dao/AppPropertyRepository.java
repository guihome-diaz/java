package eu.daxiongmao.wordpress.server.dao;

import org.springframework.data.repository.CrudRepository;

import eu.daxiongmao.wordpress.server.model.AppProperty;

public interface AppPropertyRepository extends CrudRepository<AppProperty, Long> {

    /**
     * To get a property by its key name.
     *
     * @param key
     *            search key
     * @return corresponding value or NULL
     */
    AppProperty findByKey(String key);
}
