package eu.daxiongmao.prv.server.dao.converter;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Java8 LocalDate converter to JPA.<br>
 * Source: http://www.thoughts-on-java.org/persist-localdate-localdatetime-jpa/
 *
 * @author Thorben (thoughts on java)
 */
@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(final LocalDate locDate) {
        return (locDate == null ? null : Date.valueOf(locDate));
    }

    @Override
    public LocalDate convertToEntityAttribute(final Date sqlDate) {
        return (sqlDate == null ? null : sqlDate.toLocalDate());
    }

}
