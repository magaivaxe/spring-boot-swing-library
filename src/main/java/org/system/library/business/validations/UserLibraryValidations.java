package org.system.library.business.validations;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.CharBuffer;

@RequiredArgsConstructor
@Component
public class UserLibraryValidations {

  private final PasswordEncoder passwordEncoder;

  public boolean passwordMatcher(char[] enteredPassword, String storedEncodedPassword) {
    return passwordEncoder.matches(CharBuffer.wrap(enteredPassword), storedEncodedPassword);
  }
}