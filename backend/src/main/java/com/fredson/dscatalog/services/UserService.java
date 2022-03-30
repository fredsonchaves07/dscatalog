package com.fredson.dscatalog.services;

import com.fredson.dscatalog.dto.RoleDTO;
import com.fredson.dscatalog.dto.UserDTO;
import com.fredson.dscatalog.dto.UserInsertDTO;
import com.fredson.dscatalog.entities.Role;
import com.fredson.dscatalog.entities.User;
import com.fredson.dscatalog.repositories.RoleRepository;
import com.fredson.dscatalog.repositories.UserRepository;
import com.fredson.dscatalog.services.exceptions.DatabaseException;
import com.fredson.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(UserDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageRequest) {
        Page<User> list = userRepository.findAll(pageRequest);
        return list.map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return new UserDTO(user.orElseThrow(() -> new ResourceNotFoundException("User not found!")));
    }

    @Transactional
    public UserDTO insert(UserInsertDTO userDTO) {
        User entity = new User();
        copyDTOToEntity(userDTO, entity);
        entity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        entity = userRepository.save(entity);
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO update(Long id, UserDTO userDTO) {
        try {
            User user = userRepository.getById(id);
            copyDTOToEntity(userDTO, user);
            return new UserDTO(userRepository.save(user));
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("User not found!");
        }
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException("User not found!");
        } catch (DataIntegrityViolationException exception) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDTOToEntity(UserDTO userDTO, User user) {
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.getRoles().clear();
        for (RoleDTO roleDTO : userDTO.getRoleDTOS()) {
            Role role = roleRepository.getById(roleDTO.getId());
            user.getRoles().add(role);
        }
    }
}
