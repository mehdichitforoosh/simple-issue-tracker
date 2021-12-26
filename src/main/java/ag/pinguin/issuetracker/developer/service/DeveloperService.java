package ag.pinguin.issuetracker.developer.service;

import ag.pinguin.issuetracker.developer.domain.Developer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Developer Service
 *
 * @author Mehdi Chitforoosh
 */
public interface DeveloperService {

    Long create(Developer developer);

    Long update(Developer developer);

    void delete(Long id);

    Developer findById(Long id);

    Page<Developer> findByName(String name, Pageable pageable);

}
