package ag.pinguin.issuetracker.developer.repository;

import ag.pinguin.issuetracker.developer.domain.Developer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Developer Jpa Repository
 *
 * @author Mehdi Chitforoosh
 */
@Repository
public interface DeveloperRepository extends CrudRepository<Developer, Long> {

}
