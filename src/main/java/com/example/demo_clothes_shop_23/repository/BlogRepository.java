package com.example.demo_clothes_shop_23.repository;

import com.example.demo_clothes_shop_23.entities.Blog;
import com.example.demo_clothes_shop_23.entities.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    Page<Blog> findByStatusOrderByCreatedAt(Boolean status, Pageable pageable);

    Blog findByIdAndSlugAndStatus(Integer id, String slug, Boolean status);

    @Query("SELECT b FROM Blog b JOIN b.tags t WHERE t.id = ?1 AND b.status = ?2")
    Page<Blog> findByTagIdAndStatus(Integer tagId, Boolean status, Pageable pageable);

    @Query("SELECT b FROM Blog b JOIN b.tags t WHERE t.id = ?1 AND b.status = ?2 ORDER BY b.createdAt DESC")
    List<Blog> findByTagIdAndStatusOrderByCreatedAtDesc(Integer tagId, Boolean status);
}