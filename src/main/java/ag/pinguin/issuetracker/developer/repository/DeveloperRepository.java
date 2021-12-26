package ag.pinguin.issuetracker.developer.repository;

import ag.pinguin.issuetracker.developer.domain.Developer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Developer Jpa Repository
 *
 * @author Mehdi Chitforoosh
 */
@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {

    Page<Developer> findByNameContaining(String name, Pageable pageable);

}
