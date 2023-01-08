package xyz.poorya.onlineshop.service;

import org.springframework.stereotype.Service;
import xyz.poorya.onlineshop.domain.Cart.Cart;
import xyz.poorya.onlineshop.domain.Cart.CartItem;
import xyz.poorya.onlineshop.domain.Product.Product;
import xyz.poorya.onlineshop.domain.User.UserEntity;
import xyz.poorya.onlineshop.exceptions.CartException;
import xyz.poorya.onlineshop.repo.CartItemRepository;
import xyz.poorya.onlineshop.repo.CartRepository;
import xyz.poorya.onlineshop.repo.ProductRepository;
import xyz.poorya.onlineshop.repo.UserRepository;
import xyz.poorya.onlineshop.repo.rest.UserRestRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {
    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private CartItemRepository cartItemRepository;
    private UserRestRepository userRestRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository, UserRestRepository userRestRepository,
                       UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRestRepository = userRestRepository;
        this.userRepository = userRepository;
    }

    public void addToCart(String productId, int quantity, String username) {

        Optional<Product> productByID = productRepository.findById(productId);

        if (!productByID.isPresent()) {
            throw new CartException("Requested item doesn't exists");
        }

        Product product = productByID.get();

        if (quantity > product.getQuantity()) {
            throw new CartException("Requested quantity isn't available");
        }
        CartItem cartItem = new CartItem(quantity, product);
        cartItemRepository.save(cartItem);

        Optional<UserEntity> user = userRepository.findByUsername(username);
        Optional<Cart> cartByUsername = cartRepository.findCartByUser(user.get());
        if (cartByUsername.isEmpty()) {
            Cart cart = new Cart();
            ArrayList<CartItem> cartItems = new ArrayList<>();
            cartItems.add(cartItem);
            cart.setTotal(product.getPrice() * quantity);
            cart.setCartItems(cartItems);
            cart.setUser(user.get());
            cartRepository.save(cart);
            return;

        }

        Cart cart = cartByUsername.get();
        cart.getCartItems().add(cartItem);
        cart.setTotal(product.getPrice() * quantity);
        cartRepository.save(cart);
    }
}