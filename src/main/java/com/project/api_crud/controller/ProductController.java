package com.project.api_crud.controller;

import com.project.api_crud.model.Product;
import com.project.api_crud.service.ProductService;
import com.project.api_crud.dto.ProductResponseDTO;
import com.project.api_crud.dto.ProductCreateDTO;
import com.project.api_crud.dto.ProductUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @Operation(summary = "Cria um novo produto", description = "Endpoint para criação de novos produtos.")
    @PostMapping("/new")
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductCreateDTO createDTO) {
        ProductResponseDTO newProduct = productService.registerNewProduct(createDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @Operation(summary = "Buscar produtos", description = "Lista todos os produtos registrados")
    @GetMapping()
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(@Valid )



}
