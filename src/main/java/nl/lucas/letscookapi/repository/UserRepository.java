package nl.lucas.letscookapi.repository;

import nl.lucas.letscookapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
