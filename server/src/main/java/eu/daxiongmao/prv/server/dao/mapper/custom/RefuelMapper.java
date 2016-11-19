package eu.daxiongmao.prv.server.dao.mapper.custom;

import org.springframework.stereotype.Component;

import eu.daxiongmao.prv.server.dao.mapper.GenericMapper;
import eu.daxiongmao.prv.server.dao.model.RefuelDB;
import eu.daxiongmao.prv.server.model.business.Refuel;

@Component("refuelMapper")
public class RefuelMapper extends GenericMapper<RefuelDB, Refuel> {

    public RefuelMapper() {
        super();
        addField("vehicle", "vehicleId");
    }

}
