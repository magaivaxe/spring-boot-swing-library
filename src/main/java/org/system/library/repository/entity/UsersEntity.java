package org.system.library.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UsersEntity {

  @Id
  private String username;
  @Column(nullable = false)
  private String password;
  @Column(nullable = false)
  private Boolean enabled;

  @OneToMany(mappedBy = "usersEntity", fetch = FetchType.EAGER)
  private Set<AuthoritiesEntity> authorities = new HashSet<>();
}