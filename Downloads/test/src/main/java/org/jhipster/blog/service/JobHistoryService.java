package org.jhipster.blog.service;

import java.util.Optional;
import org.jhipster.blog.domain.JobHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.jhipster.blog.domain.JobHistory}.
 */
public interface JobHistoryService {
    /**
     * Save a jobHistory.
     *
     * @param jobHistory the entity to save.
     * @return the persisted entity.
     */
    JobHistory save(JobHistory jobHistory);

    /**
     * Updates a jobHistory.
     *
     * @param jobHistory the entity to update.
     * @return the persisted entity.
     */
    JobHistory update(JobHistory jobHistory);

    /**
     * Partially updates a jobHistory.
     *
     * @param jobHistory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<JobHistory> partialUpdate(JobHistory jobHistory);

    /**
     * Get all the jobHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JobHistory> findAll(Pageable pageable);

    /**
     * Get the "id" jobHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JobHistory> findOne(Long id);

    /**
     * Delete the "id" jobHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
