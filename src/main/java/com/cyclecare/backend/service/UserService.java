package com.cyclecare.backend.service;

import com.cyclecare.backend.dto.UserDTO;
import com.cyclecare.backend.entity.User;
import com.cyclecare.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword()); // In production, hash the password!
        
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }
    
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }
    
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        // Don't send password back
        return dto;
    }
}
