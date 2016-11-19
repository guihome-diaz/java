package eu.daxiongmao.prv.server.dao.mapper.converter;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.stereotype.Component;

import eu.daxiongmao.prv.server.util.DateTimeUtils;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

/**
 * Orika custom converter, see http://orika-mapper.github.io/orika-docs/converters.html
 * @author gdiaz
 *
 */
@Component("localDateToDateConverter")
public class LocalDateToDateConverter extends BidirectionalConverter<LocalDate, Date> {

    @Override
    public Date convertTo(final LocalDate source, final Type<Date> destinationType) {
        return DateTimeUtils.asDate(source);

    }

    @Override
    public LocalDate convertFrom(final Date source, final Type<LocalDate> destinationType) {
        return DateTimeUtils.asLocalDate(source);
    }

}
