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
import java.util.Arrays;
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

    private static final BigDecimal TEST_PRICE = new BigDecimal("2500.59");

    @Test
    @DisplayName("Registro de product com sucesso")
    void testingRegisterNewProduct_Success() {

        // simula entrada do produto
        ProductCreateDTO create = new ProductCreateDTO("sofá", "bom para descansar", TEST_PRICE, 4);
        //simula produto salvo no banco
        Product savedProduct = new Product(1L, "sofá", "bom para descansar", TEST_PRICE, 4, LocalDateTime.now(), LocalDateTime.now());
        // reposta esperada salva no banco
        ProductResponseDTO expectedResponseDTO = new ProductResponseDTO(1L, "sofá", "bom para descansar", TEST_PRICE, 4, savedProduct.getCreatedAt(),savedProduct.getUpdatedAt());

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

    @Test
    @DisplayName("Deve lançar ConflictException quando o nome do produto já existe")
    void registerNewProduct_ProductExists_ThrowsException() {

        ProductCreateDTO create = new ProductCreateDTO("sofá", "bom para descansar", TEST_PRICE, 4);

        Product existingProduct = new Product(1L, "sofá", "bom para descansar", TEST_PRICE, 4, LocalDateTime.now(), LocalDateTime.now());


        when(productRepository.findByName(create.getName())).thenReturn(Optional.of(existingProduct));

        assertThrows(ConflictException.class, () -> {
            productService.registerNewProduct(create);
        });

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("buscar todos os Produtos")
    void testingSearchAllProd_Success() {

        Product saved = new Product(1L,"Test Prod", "desc", TEST_PRICE, 5, LocalDateTime.now(), LocalDateTime.now());
        Product saved1 = new Product(2L,"Test Prod1", "desc1", TEST_PRICE, 10, LocalDateTime.now(), LocalDateTime.now());

        List<Product> productList = Arrays.asList(saved1, saved);

        when(productRepository.findAll()).thenReturn(productList);

        List<ProductResponseDTO> expectedDTOs = productList.stream().map(p -> new ProductResponseDTO(p.getId(), p.getName(),p.getDescription(),p.getPrice(),p.getStock(),p.getCreatedAt(),p.getUpdatedAt()))
                .toList();
        List<ProductResponseDTO> resultDTOs = productService.findAllProducts();

        assertNotNull(resultDTOs);
        assertEquals(expectedDTOs.size(), resultDTOs.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando não há produtos cadastrados")
    void shouldReturnEmptyListWhenFindAll() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        List<ProductResponseDTO> resultDTOs = productService.findAllProducts();

        assertNotNull(resultDTOs);
        assertTrue(resultDTOs.isEmpty());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve Buscar um produto por ID com sucesso")
    void findProductsById_Success() {
        Long productId = 1L;
        Product product = new Product(productId ,"Cama", "bom para descanso", TEST_PRICE, 10, LocalDateTime.now(), LocalDateTime.now());

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductResponseDTO expectedDTO = new ProductResponseDTO(productId, "Cama", "bom para descanso", TEST_PRICE, 10, product.getCreatedAt(), product.getUpdatedAt());

        ProductResponseDTO resultDTO = productService.findProductById(productId);

        assertEquals(expectedDTO.getId(), resultDTO.getId());
        assertEquals(expectedDTO.getName(), resultDTO.getName());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    @DisplayName("Deve Lançar ProductNotFoundException quando o produto não for encontrado pelo ID")
    void findProductsById_NotFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NotFoundConflictException.class, () ->{
            productService.findProductById(productId);
        });

        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    @DisplayName("Deve atualizar um produto com Sucesso")
    void updateProduct_Success() {
        long productId = 1L;

        ProductUpdateDTO updateDTO = new ProductUpdateDTO(Optional.of("sofá novo"), Optional.of("descrição nova"), Optional.of(TEST_PRICE), Optional.of(20));
        Product existingProduct = new Product(productId, "sofá antigo", "descrição antiga", TEST_PRICE, 4, LocalDateTime.now(), LocalDateTime.now());
        Product updatedProduct = new Product(productId, "sofá novo", "descrição nova", TEST_PRICE, 20, existingProduct.getCreatedAt(), existingProduct.getUpdatedAt());

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        ProductResponseDTO expectedDTO = new ProductResponseDTO(productId, "sofá novo", "descrição nova", TEST_PRICE, 20, updatedProduct.getCreatedAt(), updatedProduct.getUpdatedAt());

        ProductResponseDTO resultDTO = productService.updateProduct(productId, updateDTO);

        assertEquals(expectedDTO.getName(), resultDTO.getName());
        assertEquals(expectedDTO.getDescription(), resultDTO.getDescription());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Deve lançar NotFoundException ao tentar atualizar um produto inexistente")
    void updatedeProduct_NotFound() {
        Long productId= 99L;
        ProductUpdateDTO updateDTO = new ProductUpdateDTO(Optional.of("sofá novo"), Optional.of("descrição nova"), Optional.of(new BigDecimal("500.00")), Optional.of(5));

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NotFoundConflictException.class, () -> {
            productService.updateProduct(productId, updateDTO);
        });

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Deletar um produto com sucesso")
    void deleteProduct_Success() {

        when(productRepository.existsById(1L)).thenReturn(true);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Lançar uma exceção ao tentar deletar um produto inexistente")
    void deleteProduct_NotFound() {

        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(false);

        assertThrows(NotFoundConflictException.class, () -> {
            productService.deleteProduct(productId);
        });

        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, never()).deleteById(productId);
    }


}
