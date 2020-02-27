package eu.daxiongmao.travel.api.v1;

import eu.daxiongmao.travel.api.error.ApiValidationError;
import eu.daxiongmao.travel.business.ParameterService;
import eu.daxiongmao.travel.model.dto.ParameterDTO;
import eu.daxiongmao.travel.model.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/parameters")
public class ParameterController {

    private final ParameterService parameterService;

    public ParameterController(ParameterService parameterService) {
        this.parameterService = parameterService;
    }

    @GetMapping("/getAll")
    public List<ParameterDTO> getAll() {
        return parameterService.getAll(false);
    }

    @GetMapping("/getByName/{paramName}")
    public ParameterDTO getParamByName(@PathVariable String paramName) {
        if (StringUtils.isBlank(paramName)) {
            throw new BadRequestException(new ApiValidationError(null, null, "paramName", "cannot be blank"));
        }

        return parameterService.getByName(paramName, false);
    }

}
