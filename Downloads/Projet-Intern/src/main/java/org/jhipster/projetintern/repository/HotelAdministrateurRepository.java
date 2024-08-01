package org.jhipster.projetintern.repository;

import org.jhipster.projetintern.domain.HotelAdministrateur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HotelAdministrateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HotelAdministrateurRepository extends JpaRepository<HotelAdministrateur, Long> {}
