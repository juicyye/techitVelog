package techit.velog.global.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import techit.velog.domain.user.dto.UserReqDto;
import techit.velog.domain.user.entity.User;
import techit.velog.domain.user.repository.UserRepository;
import techit.velog.global.security.AccountContext;

import java.util.List;
import java.util.Optional;

import static techit.velog.domain.user.dto.UserReqDto.*;

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
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));

        return new AccountContext(accountDto, authorities);
    }
}
