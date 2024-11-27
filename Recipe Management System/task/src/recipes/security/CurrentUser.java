package recipes.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CurrentUser {

    public UserDetailsImpl get() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if(authentication==null || authentication.getPrincipal().equals("anonymousUser")){
            return null;
        }else {
            return (UserDetailsImpl) authentication.getPrincipal();
        }
    }

    public String userName(){
        UserDetailsImpl userDetailsImpl = get();
        if(userDetailsImpl==null) return ""; else return userDetailsImpl.getUsername();
    }

}
