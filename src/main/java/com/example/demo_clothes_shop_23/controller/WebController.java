package com.example.demo_clothes_shop_23.controller;

import com.example.demo_clothes_shop_23.entities.*;
import com.example.demo_clothes_shop_23.model.enums.SizeType;
import com.example.demo_clothes_shop_23.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final ProductService productService;
    private final ReviewService reviewService;
    private final ImageService imageService;
    private final CategoryService categoryService;
    private final ColorService colorService;
    private final SizeService sizeService;
    private final BlogService blogService;
    private final CommentService commentService;
    private final TagService tagService;
    private final DiscountService discountService;
    private final BannerService bannerService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("allDiscounts", discountService.getDiscountByActive(true));
        model.addAttribute("allBanners", bannerService.getBannerByStatus(true));
        model.addAttribute("hotSales", productService.getAllByPriceDifferenceAsc(true));
        model.addAttribute("newArrivals", productService.getByStatusOrderByCreatedAtDesc(true));
        model.addAttribute("clothesBanner", productService.getOneProductByCategoryId(5).getPoster());
        model.addAttribute("shoesBanner", productService.getOneProductByCategoryId(12).getPoster());
        model.addAttribute("accessoriesBanner", productService.getOneProductByCategoryId(16).getPoster());
        model.addAttribute("latestBlog", blogService.getByTagIdAndStatusOrderByCreatedAtDesc(2,true));
        return "web/index";
    }

    @GetMapping("/sign-in")
    public String signIn(Model model) {
        return "web/sign-in";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        return "web/sign-up";
    }

    @GetMapping("/discount/{id}")
    public String discount(
        @PathVariable int id,
        @RequestParam(required = false,defaultValue = "1") int page,
        @RequestParam(required = false,defaultValue = "24") int pageSize,
        Model model
    ) {
        model.addAttribute("discount", discountService.getDiscountById(id));
        Page<Product> pageData = productService.getByDiscount_IdAndStatus(id, true, page, pageSize);
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage",page);
        return "web/discount";
    }

    /*Trang chi tiết sản phẩm*/
    @GetMapping("/product/{id}/{slug}")
    public String product(@PathVariable int id, @PathVariable String slug, Model model) {
        Product product = productService.findProductByIdAndSlugAndStatus(id,slug,true);
        model.addAttribute("product",product);
        model.addAttribute("reviews",reviewService.findByProduct_IdOrderByCreatedAtDesc(id));

        //Sắp xếp size
        if (product.getSizes()!=null){
            Set<Size> sizes = product.getSizes();
            Set<Size> sortedSizes = new TreeSet<>(Comparator.comparingInt(Size::getOrders));
            sortedSizes.addAll(sizes);
            model.addAttribute("sizes",sortedSizes);

            // Kiểm tra nếu có size với type
            boolean hasSizeType1 = product.getSizes().stream().anyMatch(size -> size.getType().toString().equals("CLOTHES_SIZE"));
            model.addAttribute("hasSizeType1", hasSizeType1);
            boolean hasSizeType2 = product.getSizes().stream().anyMatch(size -> size.getType().toString().equals("SHOES_SIZE"));
            model.addAttribute("hasSizeType2", hasSizeType2);
        }

        //Sắp xếp màu
        Set<Color> colors = product.getColors();
        Set<Color> sortedColor = new TreeSet<>(Comparator.comparingInt(Color::getId));
        sortedColor.addAll(colors);
        model.addAttribute("colors",sortedColor);

        //Tính giá sau khi discount
        model.addAttribute("newPrice",product.getNewPrice());

        //Lấy thông tin màu để lấy hình ảnh
            List<Image> images = imageService.getAllByColor_IdAndProduct_Id(sortedColor.iterator().next().getId(),product.getId());
            model.addAttribute("images",images);

        //Danh sách gợi ý sản phẩm
            model.addAttribute("ListProductDeCu",productService.findByCategoryIdOrderByCreatedAtDescExcludingProductId(product.getCategory().getId(), product.getId()));
        return "web/shop-details";
    }

    /*Trang danh sách và lọc sản phẩm*/
    @GetMapping("/product-shop")
    public String productShop(
        Model model,
        @RequestParam(required = false,defaultValue = "1") int page,
        @RequestParam(required = false,defaultValue = "12") int pageSize,
        @RequestParam(required = false) Integer sizeId,
        @RequestParam(required = false) Integer colorId,
        @RequestParam(required = false) String nameKeyword,
        @RequestParam(required = false) Integer categoryParentId,
        @RequestParam(required = false) Integer categoryChildId,
        @RequestParam(required = false, defaultValue = "asc") String sortProduct,
        @RequestParam(required = false) Double startPrice,
        @RequestParam(required = false) Double endPrice
    ) {
        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("categories",categories);
        model.addAttribute("colors",colorService.findAllColors());
        model.addAttribute("shoesSizes",sizeService.findSizeByTypeOrderByOrdersAsc(SizeType.SHOES_SIZE));
        model.addAttribute("clothesSize",sizeService.findSizeByTypeOrderByOrdersAsc(SizeType.CLOTHES_SIZE));
        Page<Product> pageData = productService.findAllProductsWithSpec(page,pageSize,sizeId,colorId,true,nameKeyword,categoryParentId,categoryChildId,sortProduct,startPrice,endPrice);
        model.addAttribute("pageData",pageData);
        model.addAttribute("currentPage",page);
        return "web/shop";
    }

    /*Trang danh sách blog*/
    @GetMapping("/blog")
    public String blog(
        Model model,
        @RequestParam(required = false,defaultValue = "1") int page,
        @RequestParam(required = false,defaultValue = "9") int pageSize
    ) {
        Page<Blog> pageData = blogService.getByStatusOrderByCreatedAt(true,page,pageSize);
        model.addAttribute("pageData",pageData);
        model.addAttribute("currentPage",page);
        return "web/blog";
    }

    /*Trang chi tiết blog*/
    @GetMapping("/blog/{id}/{slug}")
    public String blogDetail(@PathVariable int id, @PathVariable String slug, Model model) {
        Blog blog = blogService.getByIdAndSlugAndStatus(id,slug,true);
        model.addAttribute("blog", blog);
        model.addAttribute("comments", commentService.getByBlog_Id(id));
        return "web/blog-details";
    }

    /*Trang chi tiết blog*/
    @GetMapping("/blog/tag/{id}")
    public String blogTag(
        Model model,
        @PathVariable int id,
        @RequestParam(required = false,defaultValue = "1") int page,
        @RequestParam(required = false,defaultValue = "9") int pageSize
    ) {
        Page<Blog> pageData = blogService.getByTagIdAndStatus(id,true,page,pageSize);
        model.addAttribute("tag", tagService.getTagById(id));
        model.addAttribute("pageData",pageData);
        model.addAttribute("currentPage",page);
        return "web/blog-tag";
    }

}