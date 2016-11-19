package eu.daxiongmao.prv.server.dao.mapper.custom;

import org.springframework.stereotype.Component;

import eu.daxiongmao.prv.server.dao.mapper.GenericMapper;
import eu.daxiongmao.prv.server.dao.model.VehicleDB;
import eu.daxiongmao.prv.server.model.business.Vehicle;

@Component("vehicleMapper")
public class VehicleMapper extends GenericMapper<VehicleDB, Vehicle> {

    public VehicleMapper() {
        super();
        addField("user", "userId");
    }

}
