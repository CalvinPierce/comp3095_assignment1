package ca.gbc.comp3095.recipe.repositories;

import ca.gbc.comp3095.recipe.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

//    @Query("SELECT r FROM Role r WHERE r.name = ?1")
//    public Role findByName(String name);

    public Set<Role> findByName(String user);
}
