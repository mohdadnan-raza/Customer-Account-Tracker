package cat.foundation.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SomethingWentWrongException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SomethingWentWrongException(String message) {
		super(message);
	}
}
