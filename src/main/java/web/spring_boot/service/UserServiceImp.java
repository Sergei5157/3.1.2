package web.spring_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.spring_boot.models.User;
import web.spring_boot.repository.UserRepository;


import java.util.List;

@Service
@Transactional
public class UserServiceImp implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getOne(long id) {
        return userRepository.getById(id);
    }

    @Override
    public void add(User user) {
        userRepository.save(user);
    }

    @Override
    public void remove(long id) {
       userRepository.deleteById(id);
    }

    @Override
    public void update(long id, User user) {
        User userUpdate = getOne(id);
        userUpdate.setFirstName(user.getFirstName());
        userUpdate.setLastName(user.getLastName());
        userUpdate.setAge(user.getAge());
        userUpdate.setPassword(user.getPassword());
        userUpdate.setRoles(user.getRoles());
        userUpdate.setEmail(user.getEmail());
    }

    @Override
    public User findByUserEmail(String name) {
        return userRepository.findByEmail(name);

    }
}
