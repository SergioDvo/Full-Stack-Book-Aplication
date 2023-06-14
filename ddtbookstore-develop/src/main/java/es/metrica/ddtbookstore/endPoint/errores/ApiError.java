package es.metrica.ddtbookstore.endPoint.errores;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiError {
	private String message;
	private LocalDateTime errorDate;
	private Integer code;

	public ApiError() {

	}

	public ApiError(String message, LocalDateTime errorDate, Integer code) {
		super();
		this.message = message;
		this.errorDate = errorDate;
		this.code = code;
	}
}
