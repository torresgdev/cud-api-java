package com.project.api_crud;

import com.project.api_crud.dto.ProductResponseDTO;
import com.project.api_crud.dto.ProductCreateDTO;
import com.project.api_crud.dto.ProductUpdateDTO;
import com.project.api_crud.exception.ConflictException;
import com.project.api_crud.exception.NotFoundConflictException;
import com.project.api_crud.model.Product;
import com.project.api_crud.repository.ProductRepository;
import com.project.api_crud.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Collections;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Registro de product com sucesso")
    void testingRegisterNewProduct_Success() {
        ProductCreateDTO create = new ProductCreateDTO("sofá", "bom para descansar", new BigDecimal("450.54"), 4);

        Product savedProduct = new Product(1L, "sofá", "bom para descansar", new BigDecimal("450.54"), 4, LocalDateTime.now(), LocalDateTime.now());

        ProductResponseDTO expectedResponseDTO = new ProductResponseDTO(1L, "sofá", "bom para descansar", new BigDecimal("450.54"), 4, savedProduct.getCreatedAt(),savedProduct.getUpdatedAt());

        when(productRepository.findByName(create.getName())).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductResponseDTO resulDTO = productService.registerNewProduct(create);

        verify(productRepository, times(1)).save(any(Product.class));

        assertEquals(expectedResponseDTO.getId(), resulDTO.getId());
        assertEquals(expectedResponseDTO.getName(), resulDTO.getName());
        assertEquals(expectedResponseDTO.getDescription(), resulDTO.getDescription());
        assertEquals(expectedResponseDTO.getPrice(), resulDTO.getPrice());
        assertEquals(expectedResponseDTO.getStock(), resulDTO.getStock());


    }
}
