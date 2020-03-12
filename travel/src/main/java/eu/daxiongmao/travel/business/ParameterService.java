package eu.daxiongmao.travel.business;

import eu.daxiongmao.travel.business.cache.InnerCache;
import eu.daxiongmao.travel.dao.ParameterRepository;
import eu.daxiongmao.travel.model.db.Parameter;
import eu.daxiongmao.travel.model.dto.ParameterDTO;
import eu.daxiongmao.travel.model.exception.UnauthorizedException;
import eu.daxiongmao.travel.model.mapper.ParameterMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * To interact with application's settings.
 * @author Guillaume Diaz
 * @version 1.0 - 2020/01
 */
@Service
@Log4j2
public class ParameterService {

    /** Delay to respect between 2 cache refresh, in seconds. This prevents multi-threads issues */
    private final static long DELAY_BETWEEN_REFRESH_IN_SECONDS = 30;

    private final ParameterRepository parameterRepository;
    private final ParameterMapper parameterMapper;
    private final InnerCache<String, Parameter> cache;

    @Autowired
    public ParameterService(ParameterRepository parameterRepository, ParameterMapper parameterMapper) {
        this.parameterRepository = parameterRepository;
        this.parameterMapper = parameterMapper;

        // ****** Init cache ******
        cache = new InnerCache<>(log, DELAY_BETWEEN_REFRESH_IN_SECONDS, () -> {
            // Get DB values
            final List<Parameter> dbValues = parameterRepository.findAll();
            // Update local cache
            final Map<String, Parameter> valuesToCache = new HashMap<>(dbValues.size());
            if (dbValues != null && !dbValues.isEmpty()) {
                dbValues.forEach((dbParam) -> {
                    valuesToCache.put(dbParam.getParamName(), dbParam);
                });
            }
            return valuesToCache;
        });
    }



    /**
     * To retrieve a parameter by its name.
     * This will use the local cache
     * @param paramName search parameter
     * @param viewSensitiveParam boolean => "true" to view sensitive parameters ; "false" to hide sensitive parameters such as password. This is highly recommended for controllers
     * @return corresponding DTO or null
     */
    public Optional<ParameterDTO> getByName(final String paramName, boolean viewSensitiveParam) {
        if (StringUtils.isBlank(paramName)) {
            return Optional.empty();
        }
        // Retrieve value from cache
        final Parameter param = cache.getCachedValues().get(paramName);
        if (param == null) {
            return Optional.empty();
        }
        // Validity check (must be enabled)
        if (!param.getIsActive()) {
            log.warn("Data leak avoidance|Someone tried to access a disabled parameter {}", paramName);
            return Optional.empty();
        }
        // security check
        if (param.getIsSensitive() && !viewSensitiveParam) {
            log.warn("Data leak avoidance|Someone asked to view the sensitive parameter {} without authorization: nothing has been returned", paramName);
            throw new UnauthorizedException("This information is restricted, you are not allowed to view '" + paramName + "'. Please contact our support.");
        }
        // Conversion to DTO
        return Optional.ofNullable(parameterMapper.dbEntityToDto(param));
    }

    /**
     * To retrieve all parameters at once
     * @param viewSensitiveParams boolean => "true" to view sensitive parameters ; "false" to hide sensitive parameters such as password. This is highly recommended for controllers
     * @return corresponding DTOs or null
     */
    public List<ParameterDTO> getAll(boolean viewSensitiveParams) {
        // Retrieve value from cache
        final List<Parameter> dbParameters = new ArrayList<>(cache.getCachedValues().values());

        // Convert to DTOs
        final List<ParameterDTO> dtos = new ArrayList<>();
        for (Parameter dbParam : dbParameters) {
            // Validity check (must be enabled)
            if (!dbParam.getIsActive()) {
                continue;
            }
            // security check, skip sensitive params if required
            if (dbParam.getIsSensitive() && !viewSensitiveParams) {
                continue;
            }
            dtos.add(parameterMapper.dbEntityToDto(dbParam));
        }
        return dtos;
    }

}
