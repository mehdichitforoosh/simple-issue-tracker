package ag.pinguin.issuetracker.developer.domain;

import javax.persistence.*;

/**
 * Developer Jpa Entity
 *
 * @author Mehdi Chitforoosh
 */
@Entity
@Table(name = "developers")
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Version
    private Long version;

    // Default Constructor
    public Developer() {
    }

    private Developer(Long id, String name, Long version) {
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

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Developer Builder
     */
    public static class Builder {

        private Long id;
        private String name;
        private Long version;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setVersion(Long version) {
            this.version = version;
            return this;
        }

        public Developer build() {
            return new Developer(this.id, this.name, this.version);
        }

    }
}
