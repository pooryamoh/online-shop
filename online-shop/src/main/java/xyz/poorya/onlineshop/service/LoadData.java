package xyz.poorya.onlineshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import xyz.poorya.onlineshop.domain.Product.Category;
import xyz.poorya.onlineshop.domain.Product.Product;
import xyz.poorya.onlineshop.domain.User.Location;
import xyz.poorya.onlineshop.domain.User.Role;
import xyz.poorya.onlineshop.dto.UserDto;
import xyz.poorya.onlineshop.rabbitmq.RabbitMQSender;
import xyz.poorya.onlineshop.repo.CategoryRepository;
import xyz.poorya.onlineshop.repo.ProductRepository;
import xyz.poorya.onlineshop.repo.RoleRepository;
import xyz.poorya.onlineshop.repo.UserRepository;

import java.util.ArrayList;

@Service
public class LoadData {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    RabbitMQSender rabbitMQSender;
    @Autowired
    private UserRepository userRepository;


    public void loadData(){
        Role role_admin = roleRepository.save(new Role("ADMIN"));
        Role role_user = roleRepository.save(new Role("USER"));
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role_admin);

        UserDto userDto = UserDto.builder()
                .username("admin")
                .fName("admin")
                .lName("adminia")
                .email("admin@gmail.com")
                .phoneNumber("+98 9999999999")
                .location(new Location("London", "UK", 180D, 190D))
                .password("adminadmin!@")
                .build();


        if (!userRepository.existsByUsername(userDto.getUsername())) {
            userService.createUser(userDto, roles);
        }

        roles.clear();

        userDto = UserDto.builder()
                .username("ali")
                .fName("Ali")
                .lName("Ashrafi")
                .email("ali.ashrafi@gmail.com")
                .phoneNumber("+98 9999999999")
                .location(new Location("Birmingham", "UK", 180D, 190D))
                .password("aliashrafi!@")
                .build();

        roles.add(role_user);

        if (!userRepository.existsByUsername(userDto.getUsername())) {
            userService.createUser(userDto, roles);
        }



        //Add Category
        Category category = new Category();
        category.setName("Digital devices");
        Category categoryDigital = categoryRepository.save(category);

        category = new Category();
        category.setName("Clothes");
        Category categoryClothes = categoryRepository.save(category);

        category = new Category();
        category.setName("Video Games");
        Category categoryGame = categoryRepository.save(category);

        //Add Product
        Product product = new Product();
        product.setCategory(categoryDigital);
        product.setName("PS5");
        product.setQuantity(10);
        product.setPrice(10);
        product = productRepository.save(product);


        product = new Product();
        product.setCategory(categoryDigital);
        product.setName("ROG Zephyrus M16");
        product.setQuantity(10);
        product.setPrice(10);
        product = productRepository.save(product);


        product = new Product();
        product.setCategory(categoryClothes);
        product.setName("Polo Male shirt");
        product.setQuantity(10);
        product.setPrice(10);
        product = productRepository.save(product);

        product = new Product();
        product.setCategory(categoryClothes);
        product.setName("Northface Jacket");
        product.setQuantity(10);
        product.setPrice(10);
        product = productRepository.save(product);

        product = new Product();
        product.setCategory(categoryGame);
        product.setName("Elden Ring");
        product.setQuantity(10);
        product.setPrice(10);
        product = productRepository.save(product);

        product = new Product();
        product.setCategory(categoryGame);
        product.setName("Rust");
        product.setQuantity(10);
        product.setPrice(10);
        product = productRepository.save(product);


    }
}
