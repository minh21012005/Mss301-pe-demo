package fu.he191060.employee.common;

import fu.he191060.employee.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponseDTO> handleBusinessException(BusinessException exception) {
        return ResponseEntity.status(exception.getHttpStatus())
                .body(ApiResponseDTO.error(exception.getApiStatus()));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiResponseDTO> handleInvalidInput(Exception exception) {
        return ResponseEntity.badRequest()
                .body(ApiResponseDTO.error(2));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO> handleUnexpectedException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDTO.error(0));
    }
}
