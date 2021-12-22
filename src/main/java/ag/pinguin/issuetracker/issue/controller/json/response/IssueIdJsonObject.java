package ag.pinguin.issuetracker.issue.controller.json.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class IssueIdJsonObject {

    @JsonProperty(value = "id")
    private final Long id;

    public IssueIdJsonObject(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
