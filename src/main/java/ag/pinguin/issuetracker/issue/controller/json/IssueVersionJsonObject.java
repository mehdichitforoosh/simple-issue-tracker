package ag.pinguin.issuetracker.issue.controller.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Issue Version Json Object
 *
 * @author Mehdi Chitforoosh
 */
public final class IssueVersionJsonObject {

    @JsonProperty(value = "version")
    private final Long version;

    public IssueVersionJsonObject(Long version) {
        this.version = version;
    }

    public Long getVersion() {
        return version;
    }
}
