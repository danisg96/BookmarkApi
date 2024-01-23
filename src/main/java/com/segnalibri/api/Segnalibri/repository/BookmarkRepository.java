package com.segnalibri.api.Segnalibri.repository;

import com.segnalibri.api.Segnalibri.model.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {

    public List<Bookmark> findAllByUserId(Integer id);

}
