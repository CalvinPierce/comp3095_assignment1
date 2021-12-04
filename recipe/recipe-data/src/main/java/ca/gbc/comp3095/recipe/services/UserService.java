package ca.gbc.comp3095.recipe.services;

import ca.gbc.comp3095.recipe.model.User;
import ca.gbc.comp3095.recipe.repositories.RoleRepository;
import ca.gbc.comp3095.recipe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User getUserByUsername(String username){
        return userRepository.getUserByUsername(username);
    }

    public User getUserById(Long id){
        return userRepository.getUserById(id);
    }

    public void save(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoles(new HashSet<>(roleRepository.findByName("user")));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void saveNewPassword(User user){
        userRepository.save(user);
    }

    public void updateUser(User user){
        user.setRoles(new HashSet<>(roleRepository.findByName("user")));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void saveImage(Long id, MultipartFile file) throws IOException {
        User user = userRepository.getUserById(id);
        Byte[] bytes = new Byte[file.getBytes().length];
        int i = 0;
        for (Byte b : file.getBytes()){
            bytes[i++] = b;
        }
        user.setImage(bytes);

        userRepository.save(user);
    }

}
