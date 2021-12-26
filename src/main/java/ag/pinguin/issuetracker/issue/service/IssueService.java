package ag.pinguin.issuetracker.issue.service;

import ag.pinguin.issuetracker.issue.domain.Bug;
import ag.pinguin.issuetracker.issue.domain.Issue;
import ag.pinguin.issuetracker.issue.domain.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Issue Service
 *
 * @author Mehdi Chitforoosh
 */
public interface IssueService {

    Long createStory(Story story);

    Long createBug(Bug bug);

    Long updateStory(Story story);

    Long updateBug(Bug bug);

    void delete(Long id);

    Issue findById(Long id);

    Page<Issue> findByTitle(String title, Pageable pageable);

}
