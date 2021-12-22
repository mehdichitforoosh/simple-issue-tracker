package ag.pinguin.issuetracker.issue.service;

import ag.pinguin.issuetracker.issue.domain.Bug;
import ag.pinguin.issuetracker.issue.domain.Story;
import ag.pinguin.issuetracker.issue.dto.BugDto;
import ag.pinguin.issuetracker.issue.dto.StoryDto;
import ag.pinguin.issuetracker.issue.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class IssueServiceImpl implements IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Override
    @Transactional
    public Long createStory(StoryDto storyDto) {
        var story = new Story(storyDto.getTitle(), storyDto.getDescription(), LocalDateTime.now(), storyDto.getStatus());
        return issueRepository.save(story).getIssueId();
    }

    @Override
    @Transactional
    public Long createBug(BugDto bugDto) {
        var bug = new Bug(bugDto.getTitle(), bugDto.getDescription(), LocalDateTime.now(), bugDto.getPriority(), bugDto.getStatus());
        return issueRepository.save(bug).getIssueId();
    }

    @Override
    @Transactional
    public void updateStory(StoryDto storyDto) {
        issueRepository.findById(storyDto.getIssueId())
                .ifPresent(issue -> {
                    Story story = (Story) issue;
                    story.setTitle(storyDto.getTitle());
                    story.setDescription(storyDto.getDescription());
                    story.setStatus(storyDto.getStatus());
                    issueRepository.save(story);
                });
    }

    @Override
    @Transactional
    public void updateBug(BugDto bugDto) {
        issueRepository.findById(bugDto.getIssueId())
                .ifPresent(issue -> {
                    Bug bug = (Bug) issue;
                    bug.setTitle(bugDto.getTitle());
                    bug.setDescription(bugDto.getDescription());
                    bug.setPriority(bugDto.getPriority());
                    bug.setStatus(bugDto.getStatus());
                    issueRepository.save(bug);
                });
    }
}
