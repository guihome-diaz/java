package eu.daxiongmao.travel.business;

import eu.daxiongmao.travel.dao.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * To interact with application's settings.
 * @author Guillaume Diaz
 * @version 1.0 - 2020/01
 */
@Service
public class ParameterService {

    private final ParameterRepository parameterRepository;

    @Autowired
    public ParameterService(ParameterRepository parameterRepository) {
        this.parameterRepository = parameterRepository;
    }


}
