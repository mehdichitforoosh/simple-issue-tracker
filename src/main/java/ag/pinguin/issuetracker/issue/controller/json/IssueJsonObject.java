package ag.pinguin.issuetracker.issue.controller.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Issue Json Object
 *
 * @author Mehdi Chitforoosh
 */
public class IssueJsonObject {

    private final Long id;
    private final String title;
    private final String description;
    private final Type type;
    private final Long version;

    @JsonCreator
    public IssueJsonObject(@JsonProperty(value = "id") Long id
            , @JsonProperty(value = "title") String title
            , @JsonProperty(value = "description") String description
            , @JsonProperty(value = "type") Type type
            , @JsonProperty(value = "version") Long version) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Type getType() {
        return type;
    }

    public Long getVersion() {
        return version;
    }

    public enum Type {
        STORY, BUG
    }
}
