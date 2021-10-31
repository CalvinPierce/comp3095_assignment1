package ca.gbc.comp3095.recipe.repositories;

import ca.gbc.comp3095.recipe.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Set<Role> findByName(String user);
}
