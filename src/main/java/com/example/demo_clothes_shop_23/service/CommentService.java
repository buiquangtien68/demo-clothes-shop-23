package com.example.demo_clothes_shop_23.service;

import com.example.demo_clothes_shop_23.entities.Comment;
import com.example.demo_clothes_shop_23.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getByBlog_Id(Integer blogId) {
        return commentRepository.findByBlog_Id(blogId);
    }
}
