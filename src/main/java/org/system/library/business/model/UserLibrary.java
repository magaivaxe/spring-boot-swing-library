package org.system.library.business.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserLibrary implements UserDetails {

  private String username;
  private String passwordEncoded;
  private Boolean enabled;
  private Set<UserLibraryAuthorities> authorities;

  public void update(UserLibrary userLibrary) {
    username = userLibrary.username;
    passwordEncoded = userLibrary.passwordEncoded;
    enabled = userLibrary.enabled;
    authorities = userLibrary.authorities;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
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