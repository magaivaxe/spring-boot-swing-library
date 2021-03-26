package org.system.library.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "authorities")
public class AuthoritiesEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false, insertable = false, updatable = false)
  private String username;
  @Column(nullable = false)
  private String authority;

  @ManyToOne
  @JoinColumn(name = "username", referencedColumnName = "username")
  private UsersEntity usersEntity;

}