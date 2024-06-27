package com.backend.repository;

import com.backend.entity.Image;
import com.backend.entity.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> getImageByShoe(Shoe shoe);

    @Transactional
    void deleteAllByShoe(Shoe shoe);
}
