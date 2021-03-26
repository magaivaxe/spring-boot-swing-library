package org.system.library.business.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.system.library.repository.entity.AuthoritiesEntity;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class UserLibrary implements UserDetails {

  private String username;
  private String passwordEncoded;
  private Boolean enabled;
  private Set<UserLibraryAuthorities> authorities;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
//    return authorities.stream()
//      .map(auth -> new UserLibraryAuthorities(auth.getAuthority()))
//      .collect(Collectors.toSet());
    return authorities;
  }

  public String getPassword() {
    return passwordEncoded;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return enabled;
  }

  @Override
  public boolean isAccountNonLocked() {
    return enabled;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return enabled;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Getter
  @RequiredArgsConstructor
  public static class UserLibraryAuthorities implements GrantedAuthority {
    private final String authority;
  }
}