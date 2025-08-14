package dev.danielsebastian.To_Do_List.service;

import dev.danielsebastian.To_Do_List.dto.user.UserRegisterRequest;
import dev.danielsebastian.To_Do_List.dto.user.UserResponse;
import dev.danielsebastian.To_Do_List.exception.DataAlreadyExistsException;
import dev.danielsebastian.To_Do_List.mapper.UserMapper;
import dev.danielsebastian.To_Do_List.model.User;
import dev.danielsebastian.To_Do_List.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Transactional
    public UserResponse registerUser(UserRegisterRequest userRequest){

        if (userRepository.findUserByEmail(userRequest.email()).isPresent()) {
            throw new DataAlreadyExistsException("This email already Exists");
        }

        User user = userMapper.toUser(userRequest);

        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));

        User saveUser = userRepository.save(user);

        return userMapper.toUserResponse(saveUser);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
