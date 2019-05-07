package am.jobspace.admin.security;

import am.jobspace.common.model.User;
import am.jobspace.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Optional<User> byEmail = userRepository.findByEmail(s);
        if (byEmail == null) {
            throw new UsernameNotFoundException("user with " + s + " username does not exists");
        }
        return new SpringUser(byEmail.get());
    }
}
