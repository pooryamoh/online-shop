package xyz.poorya.onlineshop.repo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.poorya.onlineshop.domain.User.Location;
import xyz.poorya.onlineshop.domain.User.Role;
import xyz.poorya.onlineshop.domain.User.UserEntity;
import xyz.poorya.onlineshop.repo.rest.UserRestRepository;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;


//@DataMongoTest
//@ExtendWith({SpringExtension.class})
//@TestPropertySource(properties = "spring.mongodb.embedded.version=3.4.5")
//public class UserRepoTest {
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    MongoTemplate mongoTemplate;
//    @Autowired
//    private RoleRepository roleRepository;
//    @Autowired
//    private UserRestRepository userRestRepository;
//
//    @Test
//    @DisplayName("[Integration] Should save user and associated role")
//    public void Should_Save_User() {
//        // given
//        Role role_admin = roleRepository.save(new Role("ADMIN"));
//        Role role_user = roleRepository.save(new Role("USER"));
//        ArrayList<Role> roles = new ArrayList<>();
//        roles.add(role_admin);
//
//        UserEntity user = UserEntity.builder()
//                .username("poorya")
//                .fName("Poorya")
//                .lName("Mohammadi")
//                .email("pm1375pm@gmail.com")
//                .phoneNumber("+98 9367518767")
//                .location(new Location("Yazd", "UK", 180D, 190D))
//                .password("poorya1997poorya*&")
//                .role(roles)
//                .build();
//
//
//        UserEntity savedUser = userRepository.save(user);
//        assertThat(savedUser.getId()).isNotNull();
//    }
//
//    @Test
//    @DisplayName("[Integration] Should save user and associated role")
//    public void Should_Delete_User() {
//        // given
//        Role role_admin = roleRepository.save(new Role("ADMIN"));
//        Role role_user = roleRepository.save(new Role("USER"));
//        ArrayList<Role> roles = new ArrayList<>();
//        roles.add(role_admin);
//
//        UserEntity user = UserEntity.builder()
//                .username("poorya")
//                .fName("Poorya")
//                .lName("Mohammadi")
//                .email("pm1375pm@gmail.com")
//                .phoneNumber("+98 9367518767")
//                .location(new Location("Yazd", "UK", 180D, 190D))
//                .password("poorya1997poorya*&")
//                .role(roles)
//                .build();
//
//
//        UserEntity savedUser = userRepository.save(user);
//        assertThat(savedUser.getId()).isNotNull();
//        userRepository.deleteById(savedUser.getId());
//
//        assertThat(userRepository.findById(savedUser.getId()).isEmpty()).isEqualTo(true);
//
//    }
//
//
//}
