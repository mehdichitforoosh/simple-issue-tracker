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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    public Developer() {

    }

    public Developer(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
