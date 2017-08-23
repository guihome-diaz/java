package eu.daxiongmao.wordpress.ui.convert;

import java.util.ArrayList;
import java.util.List;

import eu.daxiongmao.wordpress.server.model.AppProperty;
import eu.daxiongmao.wordpress.ui.dto.AppPropertyFx;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppPropertyConvertor {

    /** Private factory. */
    public AppPropertyConvertor() {
    }

    public static List<AppProperty> fxPropToDb(final ObservableList<AppPropertyFx> fxList) {
        final List<AppProperty> dbList = new ArrayList<>();
        fxList.forEach(fxProp -> dbList.add(fxPropToDb(fxProp)));
        return dbList;
    }

    public static AppProperty fxPropToDb(final AppPropertyFx fxProp) {
        final AppProperty dbProp = new AppProperty(fxProp.getKey(), fxProp.getValue(), fxProp.getDescription());
        if (fxProp.getId() != null && fxProp.getId() > 0) {
            dbProp.setId(fxProp.getId());
        }
        return dbProp;
    }

    public static ObservableList<AppPropertyFx> dbPropToFx(final List<AppProperty> dbProps) {
        final ObservableList<AppPropertyFx> fxProps = FXCollections.observableArrayList();
        dbProps.forEach(dbProp -> fxProps.add(dbPropToFx(dbProp)));
        return fxProps;
    }

    public static AppPropertyFx dbPropToFx(final AppProperty dbProp) {
        final AppPropertyFx fxProp = new AppPropertyFx(dbProp.getKey(), dbProp.getValue(), dbProp.getDescription());
        if (dbProp.getId() != null) {
            fxProp.setId(dbProp.getId());
        }
        return fxProp;
    }

}
