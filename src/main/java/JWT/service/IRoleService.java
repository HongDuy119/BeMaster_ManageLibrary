package JWT.service;

import JWT.enity.Role;
import JWT.enity.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(RoleName roleName);
}
