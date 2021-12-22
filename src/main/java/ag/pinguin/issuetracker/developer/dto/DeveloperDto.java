package ag.pinguin.issuetracker.developer.dto;

/**
 * Developer data transfer object
 *
 * @author Mehdi Chitforoosh
 */
public final class DeveloperDto {

    private final Long id;
    private final String name;

    public DeveloperDto(String name) {
        this.id = null;
        this.name = name;
    }

    public DeveloperDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
