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
import java.util.Optional;


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
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.findAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @Operation(summary = "Buscar produtos ID", description = "Busca Produto pelo ID indicado")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        ProductResponseDTO product = productService.findProductById(id);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Atualiza um produto", description = "Atualiza produto com novas informações passadas")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@Valid @RequestBody ProductUpdateDTO productUpdateDTO, @PathVariable Long id) {
        ProductResponseDTO product = productService.updateProduct(id, productUpdateDTO);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Delete um produto", description = "Deleta produto por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
         productService.deleteProduct(id);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
