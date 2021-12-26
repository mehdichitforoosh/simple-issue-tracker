package ag.pinguin.issuetracker.issue.service;

import ag.pinguin.issuetracker.common.exception.NotFoundException;
import ag.pinguin.issuetracker.issue.domain.Bug;
import ag.pinguin.issuetracker.issue.domain.Issue;
import ag.pinguin.issuetracker.issue.domain.Story;
import ag.pinguin.issuetracker.issue.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Issue Service Implementation
 *
 * @author Mehdi Chitforoosh
 */
@Service
public class IssueServiceImpl implements IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Override
    @Transactional
    public Long createStory(Story story) {
        return issueRepository.save(story).getIssueId();
    }

    @Override
    @Transactional
    public Long createBug(Bug bug) {
        return issueRepository.save(bug).getIssueId();
    }

    @Override
    @Transactional
    public Long updateStory(Story story) {
        return issueRepository.saveAndFlush(story).getVersion();
    }

    @Override
    @Transactional
    public Long updateBug(Bug bug) {
        return issueRepository.saveAndFlush(bug).getVersion();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        issueRepository.deleteById(id);
    }


    @Override
    @Transactional(readOnly = true)
    public Issue findById(Long id) {
        return issueRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Issue> findByTitle(String title, Pageable pageable) {
        return issueRepository.findByTitleContaining(title, pageable);
    }
}
