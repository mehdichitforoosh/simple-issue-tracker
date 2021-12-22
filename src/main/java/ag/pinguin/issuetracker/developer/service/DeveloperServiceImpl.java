package ag.pinguin.issuetracker.developer.service;

import ag.pinguin.issuetracker.developer.domain.Developer;
import ag.pinguin.issuetracker.developer.dto.DeveloperDto;
import ag.pinguin.issuetracker.developer.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Developer Service Implementation
 *
 * @author Mehdi Chitforoosh
 */
@Service
public class DeveloperServiceImpl implements DeveloperService {

    @Autowired
    private DeveloperRepository developerRepository;

    @Override
    @Transactional
    public Long create(DeveloperDto developerDto) {
        var developer = new Developer(developerDto.getName());
        return developerRepository.save(developer).getId();
    }

    @Override
    @Transactional
    public void updateBasic(DeveloperDto developerDto) {
        developerRepository.findById(developerDto.getId()).ifPresent(developer -> {
            developer.setName(developerDto.getName());
            developerRepository.save(developer);
        });
    }

    @Override
    @Transactional
    public void delete(Long id) {
        developerRepository.deleteById(id);
    }
}
