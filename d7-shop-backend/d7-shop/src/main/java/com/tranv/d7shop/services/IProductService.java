package com.tranv.d7shop.services;

import com.tranv.d7shop.dtos.ProductDTO;
import com.tranv.d7shop.dtos.ProductImageDTO;
import com.tranv.d7shop.exceptions.DataNotFoundException;
import com.tranv.d7shop.models.Product;
import com.tranv.d7shop.models.ProductImage;
import com.tranv.d7shop.reponse.ProductRepose;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws DataNotFoundException;

    Product getProductById(long id) throws Exception;

    Page<ProductRepose> getAllProducts(PageRequest pageRequest);

    Product updateProduct(long productId, ProductDTO productDTO) throws Exception;

    void deleteProductById(long id);

    boolean existsByName(String productName);

    ProductImage createProductImage(long productId, ProductImageDTO productImageDTO) throws Exception;
}
