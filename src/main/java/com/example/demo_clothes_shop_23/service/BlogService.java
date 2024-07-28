package com.example.demo_clothes_shop_23.service;

import com.example.demo_clothes_shop_23.entities.Blog;
import com.example.demo_clothes_shop_23.entities.Tag;
import com.example.demo_clothes_shop_23.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    public Page<Blog> getByStatusOrderByCreatedAt(Boolean status, int page, int pageSize){
        PageRequest pageRequest = PageRequest.of(page-1, pageSize, Sort.by("createdAt").descending());
        return blogRepository.findByStatusOrderByCreatedAt(status, pageRequest);
    }

    public Blog getByIdAndSlugAndStatus(Integer id, String slug, Boolean status){
        return blogRepository.findByIdAndSlugAndStatus(id, slug, status);
    }

    public Page<Blog> getByTagIdAndStatus(Integer tagId, Boolean status, int page, int pageSize){
        PageRequest pageRequest = PageRequest.of(page-1, pageSize, Sort.by("createdAt").descending());
        return blogRepository.findByTagIdAndStatus(tagId, status, pageRequest);
    }

    public List<Blog> getByTagIdAndStatusOrderByCreatedAtDesc(Integer tagId, Boolean status){
        return blogRepository.findByTagIdAndStatusOrderByCreatedAtDesc(tagId, status)
            .stream()
            .limit(3)
            .toList();
    }

}
