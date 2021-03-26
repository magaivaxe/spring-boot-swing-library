package org.system.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.system.library.businessmodel.UserLibrary;
import org.system.library.repository.UsersRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LoginService implements UserDetailsService {

  private final UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = Optional.ofNullable(usersRepository.findByUsername(username));
    return new UserLibrary(user.orElseThrow(() -> new UsernameNotFoundException(username)));
  }
}