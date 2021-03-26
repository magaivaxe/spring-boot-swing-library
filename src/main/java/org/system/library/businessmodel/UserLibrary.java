package org.system.library.businessmodel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.system.library.repository.entity.UsersEntity;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserLibrary implements UserDetails {

  private final UsersEntity usersEntity;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return usersEntity.getAuthorities().stream()
      .map(auth -> new UserLibraryAuthorities(auth.getAuthority()))
      .collect(Collectors.toSet());
  }

  @Override
  public String getPassword() {
    return usersEntity.getPassword();
  }

  @Override
  public String getUsername() {
    return usersEntity.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return usersEntity.getEnabled();
  }

  @Override
  public boolean isAccountNonLocked() {
    return usersEntity.getEnabled();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return usersEntity.getEnabled();
  }

  @Override
  public boolean isEnabled() {
    return usersEntity.getEnabled();
  }

  @Getter
  @RequiredArgsConstructor
  private static class UserLibraryAuthorities implements GrantedAuthority {
    private final String authority;
  }
}