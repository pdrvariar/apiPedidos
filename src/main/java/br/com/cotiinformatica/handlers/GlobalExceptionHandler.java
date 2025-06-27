package br.com.cotiinformatica.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import br.com.cotiinformatica.domain.exceptions.PedidoNaoEncontradoException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException exception,
			WebRequest request) {

		var erros = exception.getBindingResult().getFieldErrors().stream()
				.map(error -> "Campo: '" + error.getField() + "' : " + error.getDefaultMessage())
				.collect(Collectors.toList());

		var body = new HashMap<String, Object>();
		body.put("status", HttpStatus.BAD_REQUEST.value());
		body.put("erros", erros);

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(PedidoNaoEncontradoException.class)
	public ResponseEntity<Map<String, Object>> handlePedidoNaoEncontradoException(
			PedidoNaoEncontradoException exception, WebRequest request) {
		var body = new HashMap<String, Object>();
		body.put("status", HttpStatus.NOT_FOUND.value());
		body.put("erros", exception.getMessage());

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(PropertyReferenceException.class)
	public ResponseEntity<Map<String, Object>> handlePropertyReferenceException(PropertyReferenceException exception,
			WebRequest request) {

		Map<String, Object> body = new HashMap<>();
		body.put("status", HttpStatus.BAD_REQUEST.value());
		body.put("erros", "Campo de ordenação inválido: '" + exception.getPropertyName() + "'");

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
}
