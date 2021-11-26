package ca.gbc.comp3095.recipe.repositories;

import ca.gbc.comp3095.recipe.model.Event;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.user.username LIKE %?1%")
    List<Event> findEventByUser(String userName);

    @Modifying
    @Query("UPDATE Event e SET e.name = :name WHERE e.id = :id")
    @Transactional
    int updateEvent(@Param("id") Long id, @Param("name") String name);
}
