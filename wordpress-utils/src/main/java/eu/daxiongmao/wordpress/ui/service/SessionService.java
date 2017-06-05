package eu.daxiongmao.wordpress.ui.service;

import org.springframework.stereotype.Component;

import eu.daxiongmao.wordpress.ui.dto.UserSessionDTO;

/**
 * UI service to manage session's settings.
 * 
 * @author Guillaume Diaz
 * @version 1.0 - June 2017
 */
@Component
public class SessionService {

    private final UserSessionDTO userSessionDTO = new UserSessionDTO();

    public UserSessionDTO getUserSessionDTO() {
        return userSessionDTO;
    }

}
