package ag.pinguin.issuetracker.issue.repository;

import ag.pinguin.issuetracker.issue.domain.Issue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Mehdi Chitforoosh
 */
@Repository
public interface IssueRepository extends CrudRepository<Issue,Long> {

}
