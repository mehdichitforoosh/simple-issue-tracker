package ag.pinguin.issuetracker.developer.controller.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Developer Version Json Object
 *
 * @author Mehdi Chitforoosh
 */
public final class DeveloperVersionJsonObject {

    @JsonProperty(value = "version")
    private final Long version;

    public DeveloperVersionJsonObject(Long version) {
        this.version = version;
    }

    public Long getVersion() {
        return version;
    }
}
