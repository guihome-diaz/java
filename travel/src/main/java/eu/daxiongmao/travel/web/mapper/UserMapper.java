package eu.daxiongmao.travel.web.mapper;

import eu.daxiongmao.travel.model.db.User;
import eu.daxiongmao.travel.model.dto.UserDTO;

/**
 * DB to DTO mapper and vice-versa # USER
 * @author Guillaume Diaz
 * @version 1.0 2019/12
 * @since application creation
 */
public class UserMapper {

    private UserMapper() {
        // private factory
    }

    /**
     * To convert an entity to DTO.
     * @param dbEntity entity to convert
     * @return corresponding DTO
     */
    public static UserDTO dbToEntity(final User dbEntity) {
        final UserDTO dto = new UserDTO();
        dto.setFirstName(dbEntity.getFirstName());
        dto.setLastName(dbEntity.getLastName());
        dto.setLangCode(dbEntity.getLangCode());
        dto.setUsername(dbEntity.getUsername());
        dto.setEmail(dbEntity.getEmail());
        dto.setPhoneNumber(dbEntity.getPhoneNumber());
        dto.setStatus(dbEntity.getStatus());
        dto.setEmailConfirmationDate(dbEntity.getEmailConfirmationDate());
        dto.setPasswordLastChangeDate(dbEntity.getPasswordLastChangeDate());
        dto.setIsActive(dbEntity.getIsActive());
        dto.setVersion(dbEntity.getVersion());
        return dto;
    }

}
