package ag.pinguin.issuetracker.issue.repository;

import ag.pinguin.issuetracker.issue.domain.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Issue Jpa Repository
 *
 * @author Mehdi Chitforoosh
 */
@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    Page<Issue> findByTitleContaining(String title, Pageable pageable);

    @Query("select i from Issue i where issue_type = ?1")
    List<Issue> findByType(String type);

    @Modifying
    @Query("update Issue i set i.assignedDeveloper.id = ?2 where i.issueId = ?1")
    void addAssignedDeveloper(Long issueId, Long developerId);

    @Modifying
    @Query("update Issue i set i.assignedDeveloper = null where i.assignedDeveloper.id = ?1")
    void removeAssignedDeveloper(Long developerId);

}
