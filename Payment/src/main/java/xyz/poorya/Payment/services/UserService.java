package xyz.poorya.Payment.services;

import xyz.poorya.Payment.domain.UserEntity;
import xyz.poorya.Payment.domain.Wallet;
import xyz.poorya.Payment.dto.UserQueueDto;
import xyz.poorya.Payment.repo.UserRepo;
import xyz.poorya.Payment.repo.WalletRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepo userRepo;

    private WalletRepo walletRepo;

    public UserService(UserRepo userRepo, WalletRepo walletRepo) {
        this.userRepo = userRepo;
        this.walletRepo = walletRepo;
    }

    public UserEntity createUser(UserQueueDto userQueueDto) {
        UserEntity user = new UserEntity();
        user.setUsername(userQueueDto.getUsername());
        user.setEmail(userQueueDto.getEmail());
        user.setLName(userQueueDto.getLName());
        user.setFName(userQueueDto.getFName());
        user.setPhoneNumber(userQueueDto.getPhoneNumber());

        Wallet wallet = new Wallet();
        String walletNumber = getANumericString(10);
        wallet.setNumber(walletNumber);
        wallet.setBalance(1000);
        user.setWallet(wallet);
        UserEntity savedUser =  userRepo.save(user);
        walletRepo.save(wallet);

        return savedUser;

    }

    public UserEntity updateUser(UserQueueDto userQueueDto){
        Optional<UserEntity> userByUsername = userRepo.findByUsername(userQueueDto.getUsername()) ;
        UserEntity user = userByUsername.get();
        user.setUsername(userQueueDto.getUsername());
        user.setEmail(userQueueDto.getEmail());
        user.setLName(userQueueDto.getLName());
        user.setFName(userQueueDto.getFName());
        user.setPhoneNumber(userQueueDto.getPhoneNumber());

        UserEntity savedUser =  userRepo.save(user);

        return savedUser;
    }

    public boolean userExists(String username){
        return userRepo.existsByUsername(username);
    }

    static String getANumericString(int n) {

        String numericString = "0123456789";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            int index
                    = (int) (numericString.length()
                    * Math.random());

            sb.append(numericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
