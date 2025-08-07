package com.project.api_crud.service;

import com.project.api_crud.dto.ProductUpdateDTO;
import com.project.api_crud.exception.ConflictException;
import com.project.api_crud.exception.NotFoundConflictException;
import com.project.api_crud.model.Product;
import com.project.api_crud.repository.ProductRepository;
import com.project.api_crud.dto.ProductCreateDTO;
import com.project.api_crud.dto.ProductResponseDTO;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    // 1             Registra novo produto
    public ProductResponseDTO registerNewProduct(ProductCreateDTO productCreateDTO) {
        // 2  verifica se unicidade do nome do produto
        if (productRepository.findByName(productCreateDTO.getName()).isPresent()) {
            throw new ConflictException("Nome do produto '"+productCreateDTO.getName()+"' já está registrado");
        }
        // 3 cria a entidade product a partid do DTO
        Product product = new Product();
        product.setName(productCreateDTO.getName());
        product.setDescription(productCreateDTO.getDescription());
        product.setPrice(productCreateDTO.getPrice());
        product.setStock(productCreateDTO.getStock());

        //4 salva no banco
        Product savedProduct = productRepository.save(product);

        return toResponseDTO(savedProduct);
    }

    //              lista todos os produtos disponiveis
    public List<ProductResponseDTO> findAllProducts() {
        List<Product> products = productRepository.findAll();

       return products.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    //              procura produto pelo ID
    public ProductResponseDTO findProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundConflictException("Produto com ID: "+id+ "não encontrado"));

        return toResponseDTO(product);

    }
    //              atualiza um produto
    public ProductResponseDTO updateProduct(Long id, ProductUpdateDTO updateDTO) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new NotFoundConflictException("Produto com ID: "+id+ "não encontrado"));


        updateDTO.getName().ifPresent(existingProduct::setName);
        updateDTO.getDescription().ifPresent(existingProduct::setDescription);
        updateDTO.getPrice().ifPresent(price -> {
            if (price.compareTo(BigDecimal.valueOf(0.01)) < 0) {
                throw new ValidationException("O preço deve ser maior que zero");
            }
            existingProduct.setPrice(price);
        });
        updateDTO.getStock().ifPresent(existingProduct::setStock);

        Product updatedProduct = productRepository.save(existingProduct);

        return toResponseDTO(updatedProduct);

    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundConflictException("Produto com ID: "+id+ "não encontrado");
        }
        productRepository.deleteById(id);
    }

    // conversao do objeto em dto de resposta
    private ProductResponseDTO toResponseDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

}
