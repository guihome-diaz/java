package eu.daxiongmao.prv.server.dao.mapper.converter;

import org.springframework.stereotype.Component;

import eu.daxiongmao.prv.server.dao.model.VehicleDB;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.metadata.Type;

/**
 * To extract database ID.<br>
 * Orika custom converter, see http://orika-mapper.github.io/orika-docs/converters.html
 * @author gdiaz
 *
 */
@Component("vehicleDbToLongConverter")
public class VehicleDbToLongConverter extends CustomConverter<VehicleDB, Long>  {

    @Override
    public Long convert(final VehicleDB source, final Type<? extends Long> destinationType) {
        if (source != null && source.getId() != null) {
            return source.getId();
        } else {
            return null;
        }
    }

}
