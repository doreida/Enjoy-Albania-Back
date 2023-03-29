package net.sparklab.AirBNBReservation.data;

import net.sparklab.AirBNBReservation.model.Role;
import net.sparklab.AirBNBReservation.model.Users;
import net.sparklab.AirBNBReservation.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class LoadData implements CommandLineRunner {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    public LoadData( BCryptPasswordEncoder bCryptPasswordEncoder,UserRepository userRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }


    private void saveUsers() {

        if (userRepository.count() == 0) {
            Users userAdmin = new Users();
            userAdmin.setEmail("admin@gmail.com");
            userAdmin.setName("Admin");
            userAdmin.setSurname("Admin");
            userAdmin.setUsername("admin");
            String encodedPassword = bCryptPasswordEncoder.encode("admin");
            userAdmin.setPassword(encodedPassword);
            userAdmin.setRole(Role.ADMIN);
            userAdmin.setEnabled(true);
            userRepository.save(userAdmin);

            Users user = new Users();
            user.setEmail("project_manager@gmail.com");
            user.setName("Project_Manager");
            user.setSurname("Project_Manager");
            user.setUsername("project");
            String passwordEncode = bCryptPasswordEncoder.encode("0123");
            user.setPassword(passwordEncode);
            user.setRole(Role.USER);
            user.setEnabled(true);

            userRepository.save(user);

        }
    }

    @Override
    public void run(String... args) throws Exception {
        saveUsers();
    }
}
