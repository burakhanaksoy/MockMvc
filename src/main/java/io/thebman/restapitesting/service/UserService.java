package io.thebman.restapitesting.service;

import io.thebman.restapitesting.repository.UserRepository;
import io.thebman.restapitesting.view.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private List<User> userlist;

//            new ArrayList<>(Arrays.asList(
//            new User(1,"Burakhan Aksoy",
//                    "burak@burak.com","Male",
//                    "123456789"),
//            new User(2,"Saadettin Teksoy",
//                    "burak@burak.com","Male",
//                    "123456789"),
//            new User(3,"James Bond",
//                    "burak@burak.com","Male",
//                    "123456789"),
//            new User(4,"Noname Guy",
//                    "burak@burak.com","Female",
//                    "123456789")
//    );

    public User getUser(int id){
        return userRepository.getUserById(id);
    }

    public List<User> getUsers(){
        userlist = new ArrayList<>();
        for(User user : userRepository.findAll()){
            userlist.add(user);
        }
        return userlist;
    }

    public User addUser(User user){
        for(User u: userRepository.findAll()){
            if(u.getName().equals(user.getName())){
                return new User();
            }
        }
        userRepository.save(user);
        return user;
    }


}
