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


    private Optional<String> name;

    private Optional<String> description;

    private Optional<BigDecimal> price;

    private Optional<Integer> stock;
}