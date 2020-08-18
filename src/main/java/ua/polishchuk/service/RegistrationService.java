package ua.polishchuk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.polishchuk.dto.RegistrationDto;
import ua.polishchuk.entity.User;
import ua.polishchuk.exception.UserAlreadyRegisteredException;
import ua.polishchuk.repository.UserRepository;
import ua.polishchuk.service.mapper.EntityMapper;

import javax.persistence.EntityExistsException;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final EntityMapper<User, RegistrationDto> mapper;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public RegistrationService(UserRepository userRepository,
                               EntityMapper<User, RegistrationDto> mapper, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.encoder = encoder;
    }

    public RegistrationDto saveNewUser(RegistrationDto registrationDto) {
        checkAlreadyRegistered(registrationDto);

        return mapper.mapEntityToDto(saveAndReturnEntity(registrationDto));
    }

    private User saveAndReturnEntity(RegistrationDto registrationDto) {
        return userRepository.save(mapper.mapDtoToEntity(encodePassword(registrationDto)));
    }

    private RegistrationDto encodePassword(RegistrationDto registrationDto) {
        registrationDto.setPassword(encoder.encode(registrationDto.getPassword()));
        return registrationDto;
    }

    private void checkAlreadyRegistered(RegistrationDto registrationDto) {
        userRepository.findByEmail(registrationDto.getEmail()).ifPresent(u -> {
            throw new UserAlreadyRegisteredException("User already registered with provided email " + registrationDto.getEmail());
        });
    }
}
