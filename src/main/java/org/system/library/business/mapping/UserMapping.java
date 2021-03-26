package org.system.library.business.mapping;

import org.mapstruct.*;
import org.system.library.business.model.UserLibrary;
import org.system.library.repository.entity.AuthoritiesEntity;
import org.system.library.repository.entity.UsersEntity;

import java.util.Set;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
  unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapping {

  @Mappings({
    @Mapping(target = "username", source = "username"),
    @Mapping(target = "password", source = "passwordEncoded"),
    @Mapping(target = "enabled", source = "enabled"),
    @Mapping(target = "authorities", source = "authorities"),
  })
  UsersEntity userLibraryToUsersEntity(UserLibrary userLibrary);

  @Mappings({
    @Mapping(target = "username", source = "username"),
    @Mapping(target = "passwordEncoded", source = "password"),
    @Mapping(target = "enabled", source = "enabled"),
    @Mapping(target = "authorities", source = "authorities"),
  })
  UserLibrary usersEntityToUserLibrary(UsersEntity usersEntity);

  @IterableMapping(qualifiedByName = "toLibraryAuthorities")
  Set<UserLibrary.UserLibraryAuthorities> authoritiesToLibraryAuthorities(Set<AuthoritiesEntity> authoritiesEntity);

  @IterableMapping(qualifiedByName = "toGrantedAuthorities")
  Set<AuthoritiesEntity> libraryAuthoritiesToAuthorities(Set<UserLibrary.UserLibraryAuthorities> authoritiesEntity);

  @Named("toLibraryAuthorities")
  UserLibrary.UserLibraryAuthorities toUserAuthoritiesWithoutData(AuthoritiesEntity authoritiesEntity);

  @Named("toGrantedAuthorities")
  @Mapping(target = "id", ignore = true)
  AuthoritiesEntity toEntityWithoutData(UserLibrary.UserLibraryAuthorities userLibraryAuthorities);
}