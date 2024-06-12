package com.magicmoments.backendapi.service.srv;

import com.magicmoments.backendapi.model.ERole;
import com.magicmoments.backendapi.model.Roles;
import com.magicmoments.backendapi.model.Users;
import com.magicmoments.backendapi.service.dto.UsersDto;
import com.magicmoments.backendapi.service.mappers.UsersMapper;
import com.magicmoments.backendapi.service.repositories.RolesRepository;
import com.magicmoments.backendapi.service.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    RolesRepository rolesRepository;

    public UsersDto registerNewUser(UsersDto usersDto) {
        Roles rol = rolesRepository.findByName(ERole.ROLE_USER);

        usersDto.getRoles().add(rol);

        Users user = usersRepository.save(usersMapper.toEntity(usersDto));

        usersDto.setId(user.getId());

        return usersDto;
    }
}
