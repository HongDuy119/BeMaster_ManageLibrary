package JWT.service.impl;

import JWT.enity.Role;
import JWT.enity.RoleName;
import JWT.repository.IRoleRepository;
import JWT.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Service
@Transactional
@Slf4j
public class RoleServiceImpl implements IRoleService {
    @Autowired // inject RolsService
    IRoleRepository iRoleRepository;

    @Override
    public Optional<Role> findByName(RoleName roleName) {
        return iRoleRepository.findByName(roleName);
    }
}
