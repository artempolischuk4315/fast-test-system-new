package ua.polishchuk.service.mapper.implementation;

import org.springframework.stereotype.Component;
import ua.polishchuk.dto.RegistrationDto;
import ua.polishchuk.entity.Role;
import ua.polishchuk.entity.User;
import ua.polishchuk.service.mapper.EntityMapper;

import java.util.Objects;

@Component
public class RegistrationMapper implements EntityMapper<User, RegistrationDto> {
    @Override
    public RegistrationDto mapEntityToDto(User entity) {
        return Objects.isNull(entity) ? null : RegistrationDto.builder()
                .email(entity.getEmail())
                .password(entity.getPassword())
                .build();
    }

    @Override
    public User mapDtoToEntity(RegistrationDto dto) {
        return Objects.isNull(dto) ? null : User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .statistics(0)
                .roles(Role.ROLE_USER)
                .build();
    }
}
