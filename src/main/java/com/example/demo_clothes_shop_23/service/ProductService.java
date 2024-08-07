package com.example.demo_clothes_shop_23.service;

import com.example.demo_clothes_shop_23.entities.Product;
import com.example.demo_clothes_shop_23.repository.CategoryRepository;
import com.example.demo_clothes_shop_23.repository.ProductRepository;
import com.example.demo_clothes_shop_23.specifications.ProductSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final JdbcTemplate jdbcTemplate;
    private final ProductSpecifications productSpecifications;
    public final CategoryRepository categoryRepository;


    public Product findProductByIdAndSlugAndStatus(Integer id, String slug, Boolean status) {
        return productRepository.findProductByIdAndSlugAndStatus(id,slug,status);
    }

    //Tìm các sản phẩm liên quan nhưng không phải sản phẩm đang hiển thị
    public List<Product> findByCategoryIdOrderByCreatedAtDescExcludingProductId(Integer categoryId, Integer excludedMovieId) {
        return productRepository.findByCategoryIdOrderByCreatedAtDescExcludingProductId(categoryId,excludedMovieId).stream().limit(4).toList();
    }

    public List<Product> findByStatusOrderByNewPrice(){
        return productRepository.findByStatusOrderByNewPrice(true);
    }
    public Page<Product> findAllByStatusOrderByNewPrice(Boolean status, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page-1, pageSize, Sort.by("createdAt").descending());
        return productRepository.findAllByStatusOrderByNewPrice(status,pageRequest);
    }

    public void updatePosters() {
        String sqlUpdatePoster = "UPDATE products p " +
                "SET p.poster = (" +
                "    SELECT i.img_url " +
                "    FROM images i " +
                "    WHERE i.product_id = p.id " +
                "    ORDER BY i.id ASC " +
                "    LIMIT 1" +
                ") ";

        jdbcTemplate.execute(sqlUpdatePoster);

        String sqlDefaultPoster = "UPDATE products SET poster = 'https://placehold.co/400x700?text=NULL' WHERE poster IS NULL";
        jdbcTemplate.execute(sqlDefaultPoster);
    }

    public Page<Product> findAllProductsWithSpec(
        int page,
        int pageSize,
        Integer sizeId,
        Integer colorId,
        Boolean status,
        String nameKeyword,
        Integer categoryParentId,
        Integer categoryChildId,
        String sortProduct,
        Double startPrice,
        Double endPrice
    ) {
        Specification<Product> spec = ProductSpecifications
            .findProducts(
                sizeId,
                colorId,
                status,
                nameKeyword,
                categoryParentId,
                categoryChildId,
                sortProduct,
                startPrice,
                endPrice
            );
        PageRequest pageRequest = PageRequest.of(page-1, pageSize);
        return productRepository.findAll(spec,pageRequest);
    }

    public List<Product> getAllByPriceDifferenceAsc(Boolean status){
        return productRepository.findAllByPriceDifferenceAsc(status)
            .stream()
            .limit(4)
            .toList();
    }

    public List<Product> getByStatusOrderByCreatedAtDesc(Boolean status){
        return productRepository.findByStatusOrderByCreatedAtDesc(status)
            .stream()
            .limit(4)
            .toList();
    }

    public Product getOneProductByCategoryId(Integer categoryId) {
        return productRepository.findByCategoryId(categoryId)
            .stream()
            .findFirst()
            .orElse(null);
    }

    public Page<Product> getByDiscount_IdAndStatus(Integer discountId, Boolean status, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page-1, pageSize, Sort.by("createdAt").descending());
        return productRepository.findByDiscount_IdAndStatus(discountId, status, pageRequest);
    }

    public Product getById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }


    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
