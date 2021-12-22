package ag.pinguin.issuetracker.issue.controller.json.request;

public abstract class IssueJsonObject {

    private final String title;
    private final String description;

    public IssueJsonObject(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
