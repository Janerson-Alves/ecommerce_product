package br.com.jaanalves.ecommerce_product.exceptions;

import br.com.jaanalves.ecommerce_product.dto.StandardErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // TRATA AS EXCEÇÕES DE REGRA DE NEGOCIO

    //Nome duplicado
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardErrorDTO> handlerIllegalArgument(IllegalArgumentException ex,
                                                                   HttpServletRequest request) {
        // Status BadRequest
        HttpStatus status = HttpStatus.BAD_REQUEST;
        // Instancia o Erro em DTO
        StandardErrorDTO error = new StandardErrorDTO(
                Instant.now(),
                status.value(),
                "Regra de negócio Violada",
                ex.getMessage(),
                request.getRequestURI()
        );
        // Retorna a mensagem de exceção
        return ResponseEntity.status(status).body(error);
    }

    // Regra de Negocio - erro dos campos anotados
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorDTO> handlerMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                          HttpServletRequest request) {
        // Status BadRequest
        HttpStatus status = HttpStatus.BAD_REQUEST;

        // Capturando a mensagem de erro da annotation
        String erroMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .findFirst()
                .orElse("Erro na validação dos campos.");

        // Instancia o Erro em DTO
        StandardErrorDTO error = new StandardErrorDTO(
                Instant.now(),
                status.value(),
                "Erro na validação de dados",
                erroMessage,
                request.getRequestURI()
        );
        // Retorna a mensagem de exceção
        return ResponseEntity.status(status).body(error);
    }
}
