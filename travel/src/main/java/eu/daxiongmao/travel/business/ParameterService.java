package eu.daxiongmao.travel.business;

import eu.daxiongmao.travel.api.mapper.ParameterMapper;
import eu.daxiongmao.travel.dao.ParameterRepository;
import eu.daxiongmao.travel.model.db.Parameter;
import eu.daxiongmao.travel.model.dto.ParameterDTO;
import eu.daxiongmao.travel.model.exception.UnauthorizedException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * To interact with application's settings.
 * @author Guillaume Diaz
 * @version 1.0 - 2020/01
 */
@Service
@Log4j2
public class ParameterService {

    private final ParameterRepository parameterRepository;
    private final ParameterMapper parameterMapper;

    @Autowired
    public ParameterService(ParameterRepository parameterRepository, ParameterMapper parameterMapper) {
        this.parameterRepository = parameterRepository;
        this.parameterMapper = parameterMapper;
    }

    /**
     * To retrieve a parameter by its name.
     * @param paramName search parameter
     * @param viewSensitiveParam boolean => "true" to view sensitive parameter ; "false" to hide sensitive parameters such as password. This is highly recommended for controllers
     * @return corresponding DTO or null
     */
    public ParameterDTO getByName(final String paramName, boolean viewSensitiveParam) {
        if (StringUtils.isBlank(paramName)) {
            return null;
        }

        // **** Get DB value
        final Parameter dbParam = parameterRepository.findParameterByParamName(paramName);
        // not found
        if (dbParam == null) { return null; }
        // security check
        if (dbParam.getIsSensitive() && !viewSensitiveParam) {
            log.warn("Data leak avoidance|Someone asked to view the sensitive parameter {} without authorization: nothing has been returned", paramName);
            throw new UnauthorizedException("This information is restricted, you are not allowed to view '" + paramName + "'. Please contact our support.");
        }

        // **** Conversion to DTO
        return parameterMapper.dbEntityToDto(dbParam);
    }

    public List<ParameterDTO> getAll(boolean viewSensitiveParams) {
        // **** Get DB value
        final List<Parameter> dbParameters = parameterRepository.findAll();
        // not found
        if (dbParameters == null || dbParameters.isEmpty()) { return new ArrayList<>(); }

        // ***** Process results (convert to DTO)
        final List<ParameterDTO> params = new ArrayList<>();
        for (Parameter dbParam : dbParameters) {
            // security check, skip sensitive params if required
            if (dbParam.getIsSensitive() && !viewSensitiveParams) {
                continue;
            }
            params.add(parameterMapper.dbEntityToDto(dbParam));
        }

        return params;
    }

}
