package ag.pinguin.issuetracker.developer.service;

import ag.pinguin.issuetracker.common.exception.NotFoundException;
import ag.pinguin.issuetracker.developer.domain.Developer;
import ag.pinguin.issuetracker.developer.repository.DeveloperRepository;
import ag.pinguin.issuetracker.issue.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private IssueRepository issueRepository;

    @Override
    @Transactional
    public Long create(Developer developer) {
        return developerRepository.save(developer).getId();
    }

    @Override
    @Transactional
    public Long update(Developer developer) {
        return developerRepository.saveAndFlush(developer).getVersion();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        developerRepository.deleteById(id);
        issueRepository.removeAssignedDeveloper(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Developer findById(Long id) {
        return developerRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Developer> findByName(String name, Pageable pageable) {
        return developerRepository.findByNameContaining(name, pageable);
    }
}
