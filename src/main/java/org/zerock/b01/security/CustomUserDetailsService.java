package org.zerock.b01.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(){
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    //사용자 정보를 저장하는 UserDetails 인터페이스 이를 구현한 User 클래스를 API로 제공한다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("loadUserByUsername : " + username);

        UserDetails userDetails = User.builder()
                .username("user1")
                .password(passwordEncoder.encode("1111"))
                .authorities("ROLE_USER")
                .build();

        return userDetails;
    }
}
