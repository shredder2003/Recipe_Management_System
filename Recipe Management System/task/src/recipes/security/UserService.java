package recipes.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    //private final DTOmapper dTOmapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signup(User user){
        log.info( "signup(+) email={} newpass={}",user.getEmail(),user.getPassword() );
        if (user.getEmail()==null||user.getEmail().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Email is null!");
        }else if(userRepository.findUserByEmailIgnoreCase(user.getEmail().toLowerCase(Locale.ROOT)).isEmpty()){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEmail( user.getEmail().toLowerCase(Locale.ROOT) );
            userRepository.save(user);
            log.info( "signup(-) username={} newpass={}",user.getEmail(),user.getPassword() );
            return user;
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User exists!");
        }
    }


}
