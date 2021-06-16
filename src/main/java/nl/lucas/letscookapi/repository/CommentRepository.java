package nl.lucas.letscookapi.repository;

import nl.lucas.letscookapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
