package eu.daxiongmao.prv.server.dao.mapper.converter;

import org.springframework.stereotype.Component;

import eu.daxiongmao.prv.server.dao.model.UserDB;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.metadata.Type;

/**
 * To extract database ID.<br>
 * Orika custom converter, see http://orika-mapper.github.io/orika-docs/converters.html
 * @author gdiaz
 *
 */
@Component("userDbToLongConverter")
public class UserDbToLongConverter extends CustomConverter<UserDB, Long> {

    @Override
    public Long convert(final UserDB source, final Type<? extends Long> destinationType) {
        if (source != null && source.getId() != null) {
            return source.getId();
        } else {
            return null;
        }
    }


}
