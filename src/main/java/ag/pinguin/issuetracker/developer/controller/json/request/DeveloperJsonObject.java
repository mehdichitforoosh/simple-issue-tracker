package ag.pinguin.issuetracker.developer.controller.json.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Developer json object
 *
 * @author Mehdi Chitforoosh
 */
public final class DeveloperJsonObject {

    private final String name;

    @JsonCreator
    public DeveloperJsonObject(@JsonProperty(value = "name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
