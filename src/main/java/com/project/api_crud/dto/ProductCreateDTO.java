package com.project.api_crud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDTO {

    @NotBlank(message = "O nome do produto é obrigatório")
    @Size(message = "O nome do produto não pode ultrapassar de 50 caracteres")
    private String name;

    @NotBlank(message = "A descrição do produto é obrigatória")
    @Size(message = "A descrição do produto não pode ultrapassar de 100 caracteres")
    private String description;

    @NotNull(message = "O preço do produto é obrigatório")
    @Positive(message = "Preço do produto deve ser maior que 0")
    private Double price;

    @NotNull(message = "A Quantidade do produto é obrigatória")
    @Positive(message = "Stock do produto não pode ser 0")
    private Integer stock;
}
