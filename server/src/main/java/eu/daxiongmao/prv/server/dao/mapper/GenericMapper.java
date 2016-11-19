package eu.daxiongmao.prv.server.dao.mapper;

import java.util.HashMap;
import java.util.Map;

import ma.glasnost.orika.CustomMapper;

/**
 * @author plhote
 */
public abstract class GenericMapper<A,B> extends CustomMapper<A, B>{

    private final Map<String,String> fields = new HashMap<String, String>();

    public void addField(final String fieldA, final String fieldB) {
        fields.put(fieldA, fieldB);
    }

    public Map<String,String> getFields() {
        return fields;
    }
}
