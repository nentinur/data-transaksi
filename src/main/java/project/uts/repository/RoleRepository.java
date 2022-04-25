package project.uts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.uts.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
