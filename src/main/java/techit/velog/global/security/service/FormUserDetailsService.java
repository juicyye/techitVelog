package techit.velog.global.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import techit.velog.domain.user.entity.User;
import techit.velog.domain.user.repository.UserRepository;
import techit.velog.global.dto.AccountDto;
import techit.velog.global.dto.PrincipalDetails;


import java.util.Optional;



@RequiredArgsConstructor
@Component("userDetailsService")
public class FormUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> _user = userRepository.findByLoginId(username);
        if (_user.isEmpty()) {
            throw new UsernameNotFoundException("not found loginId ="+username);
        }
        User user = _user.get();
        AccountDto accountDto = AccountDto.toDto(user);

        return new PrincipalDetails(accountDto);
    }
}
