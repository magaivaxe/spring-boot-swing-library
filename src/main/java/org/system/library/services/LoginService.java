package org.system.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.system.library.business.mapping.UserMapping;
import org.system.library.repository.UsersRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LoginService implements UserDetailsService {

  private final UsersRepository usersRepository;
  private final UserMapping userMapping;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = Optional.ofNullable(usersRepository.findByUsername(username));
    var usersEntity = user.orElseThrow(() -> new UsernameNotFoundException(username));
    return userMapping.usersEntityToUserLibrary(usersEntity);
  }
}