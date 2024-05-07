package com.tranv.d7shop.services.impl;

import com.tranv.d7shop.dtos.ProductDTO;
import com.tranv.d7shop.dtos.ProductImageDTO;
import com.tranv.d7shop.exceptions.DataNotFoundException;
import com.tranv.d7shop.exceptions.InvalidParamException;
import com.tranv.d7shop.models.Category;
import com.tranv.d7shop.models.Product;
import com.tranv.d7shop.models.ProductImage;
import com.tranv.d7shop.reponse.ProductRepose;
import com.tranv.d7shop.repository.CategoryRepository;
import com.tranv.d7shop.repository.ProductImageRepository;
import com.tranv.d7shop.repository.ProductRepository;
import com.tranv.d7shop.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    @Transactional
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category not found"));
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .description(productDTO.getDescription())
                .category(category)
                .build();

        productRepository.save(newProduct);
        return newProduct;
    }

    @Override
    public Product getProductById(long id) throws Exception {
        return productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Cannot find product with id: " + id));
    }

    @Override
    public Page<ProductRepose> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).map(ProductRepose::fromProduct);
    }

    @Override
    @Transactional
    public Product updateProduct(long productId, ProductDTO productDTO) throws Exception {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category not found"));
        Product existingProduct = getProductById(productId);
        if (existingProduct != null) {
            existingProduct.setName(productDTO.getName());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setCategory(category);
            productRepository.save(existingProduct);
        }

        return existingProduct;
    }

    @Override
    @Transactional
    public void deleteProductById(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(id);
        }
    }

    @Override
    public boolean existsByName(String productName) {
        return productRepository.existsByName(productName);
    }

    @Override
    @Transactional
    public ProductImage createProductImage(
            long productId,
            ProductImageDTO productImageDTO
    ) throws Exception {
        Product existingProduct = productRepository.findById(productImageDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find product with id: " + productImageDTO.getProductId()));
        ProductImage productImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        // không cho insert qúa 5 ảnh cho 1 sản phẩm
        int size = productImageRepository.findByProductId(productId).size();
        if (size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException(
                    "Number of images must be <= " + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        productImageRepository.save(productImage);
        return productImage;
    }
}
