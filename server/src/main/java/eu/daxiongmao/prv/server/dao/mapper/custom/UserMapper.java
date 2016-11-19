package eu.daxiongmao.prv.server.dao.mapper.custom;

import org.springframework.stereotype.Component;

import eu.daxiongmao.prv.server.dao.mapper.GenericMapper;
import eu.daxiongmao.prv.server.dao.model.UserDB;
import eu.daxiongmao.prv.server.model.business.User;

@Component("userMapper")
public class UserMapper extends GenericMapper<UserDB, User> {

    public UserMapper() {
        super();
    }

}
