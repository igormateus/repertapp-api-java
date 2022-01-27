package io.github.igormateus.repertapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import io.github.igormateus.repertapp.model.AppUser;
import io.github.igormateus.repertapp.model.Band;

public interface BandRepository extends JpaRepository<Band, Long> {
    boolean existsByName(String name);

    Page<Band> findByMembers(AppUser user, Pageable page);
}
