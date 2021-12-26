package ag.pinguin.issuetracker.developer.controller.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Developer Id Json Object
 *
 * @author Mehdi Chitforoosh
 */
public final class DeveloperIdJsonObject {

    @JsonProperty(value = "id")
    private final Long id;

    public DeveloperIdJsonObject(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
