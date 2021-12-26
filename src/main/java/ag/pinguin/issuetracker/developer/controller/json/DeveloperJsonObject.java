package ag.pinguin.issuetracker.developer.controller.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Developer json object
 *
 * @author Mehdi Chitforoosh
 */
public final class DeveloperJsonObject {

    private final Long id;
    private final String name;
    private final Long version;

    @JsonCreator
    public DeveloperJsonObject(@JsonProperty(value = "id") Long id
            , @JsonProperty(value = "name") String name
            , @JsonProperty(value = "version") Long version) {
        this.id = id;
        this.name = name;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getVersion() {
        return version;
    }
}
