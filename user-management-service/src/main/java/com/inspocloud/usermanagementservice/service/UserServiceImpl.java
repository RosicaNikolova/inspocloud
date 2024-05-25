package com.inspocloud.usermanagementservice.service;

import com.inspocloud.usermanagementservice.model.User;
import com.inspocloud.usermanagementservice.rabbit.RabbitSender;
import com.inspocloud.usermanagementservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RabbitSender rabbitSender;

    @Override
    public void createNewUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
        rabbitSender.sendDeleteUserToExchange(id);
    }

    @Override
    public void edtUser(User user) {

        Optional<User> optionalUser = userRepository.findById(user.getUserId());
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());

            // Save edited user
            userRepository.save(existingUser);
            // Send edit user operation to RabbitMQ
            rabbitSender.sendEditUserToExchange(user);
        } else {
            throw new RuntimeException("User not found with id: " + user.getUserId());
        }
    }
}
