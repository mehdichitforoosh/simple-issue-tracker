package ag.pinguin.issuetracker.developer.controller.json.response;

import com.fasterxml.jackson.annotation.JsonProperty;

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
