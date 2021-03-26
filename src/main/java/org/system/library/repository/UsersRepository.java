package org.system.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.system.library.repository.entity.UsersEntity;

public interface UsersRepository extends JpaRepository<UsersEntity, String> {

  UsersEntity findByUsername(String username);
}