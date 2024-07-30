package cat.foundation.bank.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDetails {
	
	private LocalDate timeStamp;
	private String message;
	private HttpStatus status;
	
}
