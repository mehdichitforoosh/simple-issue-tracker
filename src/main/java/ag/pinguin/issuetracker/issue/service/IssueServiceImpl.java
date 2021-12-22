package ag.pinguin.issuetracker.issue.service;

import ag.pinguin.issuetracker.issue.domain.Bug;
import ag.pinguin.issuetracker.issue.domain.Story;
import ag.pinguin.issuetracker.issue.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
