package com.example.DoAnJava.services;

import com.example.DoAnJava.Repository.BlogRepository;
import com.example.DoAnJava.models.Blog;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BlogService {

    private final BlogRepository blogRepository;

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }

    public Blog addBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public Blog updateBlog(@NotNull Blog blog) {
        Blog existingBlog = blogRepository.findById(blog.getId())
                .orElseThrow(() -> new IllegalStateException("Blog with ID " + blog.getId() + " does not exist."));
        existingBlog.setTitle(blog.getTitle());
        existingBlog.setContent(blog.getContent());
        existingBlog.setImageUrl(blog.getImageUrl());
        return blogRepository.save(existingBlog);
    }

    public void deleteBlogById(Long id) {
        if (!blogRepository.existsById(id)) {
            throw new IllegalStateException("Blog with ID " + id + " does not exist.");
        }
        blogRepository.deleteById(id);
    }
}
