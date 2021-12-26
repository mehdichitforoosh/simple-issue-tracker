package ag.pinguin.issuetracker.common.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Page Json Object
 *
 * @param <T>
 * @author Mehdi Chitforoosh
 */
public class PageJsonObject<T> {

    @JsonProperty(value = "totalItems", required = true)
    private final Long totalItems;

    @JsonProperty(value = "items", required = true)
    private final List<T> items;

    public PageJsonObject(Long totalItems, List<T> items) {
        this.totalItems = totalItems;
        this.items = items;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public List<T> getItems() {
        return items;
    }
}
