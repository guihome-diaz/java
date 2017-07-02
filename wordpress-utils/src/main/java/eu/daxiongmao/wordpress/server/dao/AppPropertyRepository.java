package eu.daxiongmao.wordpress.server.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import eu.daxiongmao.wordpress.server.model.AppProperty;

@Repository
public interface AppPropertyRepository extends PagingAndSortingRepository<AppProperty, Long> {

    /**
     * To get a property by its key name.
     *
     * @param key
     *            search key
     * @return corresponding value or NULL
     */
    AppProperty findByKey(String key);
}
