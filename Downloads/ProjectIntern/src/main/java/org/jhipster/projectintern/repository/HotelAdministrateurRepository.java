package org.jhipster.projectintern.repository;

import org.jhipster.projectintern.domain.HotelAdministrateur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HotelAdministrateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HotelAdministrateurRepository extends JpaRepository<HotelAdministrateur, Long> {}
