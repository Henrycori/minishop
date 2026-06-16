package com.tecsup.minishop.service;

import com.tecsup.minishop.model.Product;
import com.tecsup.minishop.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    // REFACTOR: Extracción de constantes para evitar "Magic Strings" y "Magic Numbers"
    private static final Long VALID_ID = 1L;
    private static final Long NOT_FOUND_ID = 99L;
    private static final String PRODUCT_NAME = "Auriculares Sony";
    private static final double VALID_PRICE = 320.00;
    private static final int VALID_STOCK = 15;

    @Test
    @DisplayName("Debe guardar un producto válido correctamente")
    void shouldSaveValidProduct() {
        // ARRANGE - Código más limpio usando las constantes del refactor
        Product input = Product.builder()
                .name(PRODUCT_NAME)
                .price(VALID_PRICE)
                .stock(VALID_STOCK)
                .build();
                
        Product expected = Product.builder()
                .id(VALID_ID)
                .name(PRODUCT_NAME)
                .price(VALID_PRICE)
                .stock(VALID_STOCK)
                .build();
                
        when(productRepository.save(any(Product.class))).thenReturn(expected);

        // ACT
        Product result = productService.save(input);

        // ASSERT - Corregido de vuelta a verde
        assertThat(result.getId()).isEqualTo(VALID_ID);
        assertThat(result.getName()).isEqualTo(PRODUCT_NAME);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el precio es cero o negativo")
    void shouldThrowExceptionWhenPriceIsInvalid() {
        // ARRANGE
        Product product = Product.builder()
                .name("Producto inválido")
                .price(0.0)
                .stock(5)
                .build();

        // ACT & ASSERT
        assertThatThrownBy(() -> productService.save(product))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El precio debe ser mayor a cero");
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el stock es negativo")
    void shouldThrowExceptionWhenStockIsNegative() {
        // ARRANGE
        Product product = Product.builder()
                .name("Producto sin stock")
                .price(100.00)
                .stock(-1)
                .build();

        // ACT & ASSERT
        assertThatThrownBy(() -> productService.save(product))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El stock no puede ser negativo");
    }

    @Test
    @DisplayName("Debe retornar todos los productos")
    void shouldReturnAllProducts() {
        // ARRANGE
        List<Product> products = List.of(
                Product.builder().id(1L).name("Producto A").price(100.0).stock(5).build(),
                Product.builder().id(2L).name("Producto B").price(200.0).stock(3).build()
        );
        when(productRepository.findAll()).thenReturn(products);

        // ACT
        List<Product> result = productService.findAll();

        // ASSERT
        assertThat(result).hasSize(2);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el producto no existe por ID")
    void shouldThrowExceptionWhenProductNotFound() {
        // ARRANGE - Uso de la constante extraída para legibilidad
        when(productRepository.findById(NOT_FOUND_ID)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> productService.findById(NOT_FOUND_ID))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Producto no encontrado con id: " + NOT_FOUND_ID);
    }
}