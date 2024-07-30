package cat.foundation.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NotExists extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotExists(String message) {
		super(message);
	}
}
