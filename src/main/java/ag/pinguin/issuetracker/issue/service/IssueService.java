package ag.pinguin.issuetracker.issue.service;

import ag.pinguin.issuetracker.issue.domain.Bug;
import ag.pinguin.issuetracker.issue.domain.Story;

public interface IssueService {

    Long createStory(Story story);

    Long createBug(Bug bug);
}
