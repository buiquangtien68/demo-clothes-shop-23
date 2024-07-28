package com.example.demo_clothes_shop_23.repository;

import com.example.demo_clothes_shop_23.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    public List<Comment> findByBlog_Id(Integer blogId);
}
