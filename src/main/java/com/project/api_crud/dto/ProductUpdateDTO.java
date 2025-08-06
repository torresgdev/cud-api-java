package com.project.api_crud.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDTO {

    @Size(max = 50, message = "O nome do produto não pode ultrapassar de 50 caracteres")
    private Optional<String> name;

    @Size(max = 255, message = "A descrição do produto não pode ultrapassar de 255 caracteres")
    private Optional<String> description;

    @DecimalMin(value = "0.01", inclusive = true, message = "O preço deve ser maior que zero")
    private Optional<BigDecimal> price;

    private Optional<Integer> stock;
}