package ag.pinguin.issuetracker.developer.service;

import ag.pinguin.issuetracker.developer.dto.DeveloperDto;

/**
 * Developer Service
 *
 * @author Mehdi Chitforoosh
 */
public interface DeveloperService {

    Long create(DeveloperDto developer);

    void updateBasic(DeveloperDto developer);

    void delete(Long id);

}
