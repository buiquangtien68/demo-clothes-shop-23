package com.example.demo_clothes_shop_23.controller;

import com.example.demo_clothes_shop_23.entities.*;
import com.example.demo_clothes_shop_23.model.enums.SizeType;
import com.example.demo_clothes_shop_23.service.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final BlogService blogService;
    private final TagService tagService;
    private final ProductService productService;
    private final SizeService sizeService;
    private final ColorService colorService;
    private final CategoryService categoryService;
    private final QuantityService quantityService;

    //dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "admin/dashboard/dashboard";
    }

    //blog
    @GetMapping("/blogs")
    public String getBlogIndexPage(Model model) {
        model.addAttribute("blogs", blogService.getAll());
        return "admin/blog/blog-index";
    }
    @GetMapping("/blogs/own-blogs")
    public String getBlogOwnBlogPage(Model model) {
        model.addAttribute("blogs", blogService.getAllByUserIdOrderByCreatedAtDesc());
        return "admin/blog/blog-yourself";
    }
    @GetMapping("/blogs/create")
    public String getBlogCreatePage(Model model) {
        model.addAttribute("tags", tagService.getAll());
        return "admin/blog/blog-create";
    }

    @GetMapping("/blogs/{id}")
    public String getBlogDetailPage(@PathVariable int id, Model model) {
        model.addAttribute("blog",blogService.getBlogById(id));
        model.addAttribute("tags", tagService.getAll());
        return "admin/blog/blog-detail";
    }

    @GetMapping("/blogs/tags")
    public String getBlogTagPage(Model model) {
        List<Tag> tags = tagService.getAll();

        Map<Integer, List<Blog>> blogsByTagName = tags.stream()
            .collect(Collectors.toMap(
                Tag::getId,
                tag -> blogService.getByTagId(tag.getId())
            ));

        model.addAttribute("tags", tags);
        model.addAttribute("blogsByTagName", blogsByTagName);

        return "admin/blog/blog-tag";
    }

    //product
    @GetMapping("/products")
    public String getIndexPage(Model model) {
        model.addAttribute("products",productService.getAll());
        return "admin/product/product-index";
    }

    @GetMapping("/products/{id}")
    public String getDetailPage(@PathVariable int id, Model model) {
        model.addAttribute("product",productService.getById(id));

        //Sắp xếp màu
        Set<Color> colors = colorService.getAll();
        Set<Color> sortedColor = new TreeSet<>(Comparator.comparingInt(Color::getId));
        sortedColor.addAll(colors);

        List<Quantity> quantities = quantityService.getByProductId(id);
        Map<String, Integer> stockMap = quantities.stream()
            .filter(q -> q.getValue() > 0)
            .collect(Collectors.toMap(
                q -> q.getColor().getId() + "-" + q.getSize().getId(),
                Quantity::getValue
            ));

        model.addAttribute("stockMap", stockMap);
        model.addAttribute("sizeTypes", SizeType.values());
        model.addAttribute("colors",sortedColor);
        model.addAttribute("categoryParents",categoryService.getCategoriesWithNullParentId());

        return "admin/product/product-detail";
    }

    @GetMapping("/products/create")
    public String getCreatePage(Model model) {
        //Sắp xếp màu
        Set<Color> colors = colorService.getAll();
        Set<Color> sortedColor = new TreeSet<>(Comparator.comparingInt(Color::getId));
        sortedColor.addAll(colors);

        model.addAttribute("sizeTypes", SizeType.values());
        model.addAttribute("colors",sortedColor);
        model.addAttribute("categoryParents",categoryService.getCategoriesWithNullParentId());

        return "admin/product/product-create";
    }

}
