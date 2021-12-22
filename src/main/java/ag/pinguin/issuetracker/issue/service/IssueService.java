package ag.pinguin.issuetracker.issue.service;

import ag.pinguin.issuetracker.issue.dto.BugDto;
import ag.pinguin.issuetracker.issue.dto.StoryDto;

public interface IssueService {

    Long createStory(StoryDto storyDto);

    Long createBug(BugDto bugDto);

    void updateStory(StoryDto storyDto);

    void updateBug(BugDto bugDto);

}
