package ag.pinguin.issuetracker.issue.controller.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Issue Id Json Object
 *
 * @author Mehdi Chitforoosh
 */
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
