package org.jhipster.blog.repository;

import java.util.List;
import java.util.Optional;
import org.jhipster.blog.domain.Job;
import org.springframework.data.domain.Page;

public interface JobRepositoryWithBagRelationships {
    Optional<Job> fetchBagRelationships(Optional<Job> job);

    List<Job> fetchBagRelationships(List<Job> jobs);

    Page<Job> fetchBagRelationships(Page<Job> jobs);
}
