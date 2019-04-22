package net.kprod.tooling.spring.commons.exception;

import org.springframework.http.HttpStatus;

/**
 * Standard Exception, may be used to rewrite any exception thrown at error processing level
 * Encapsulates {@link HttpStatus}, in order to return a status according error type
 */
public class HttpServiceException extends ServiceException {
    private HttpStatus status;

	public HttpServiceException(HttpStatus status) {
		super();
	}

	public HttpServiceException(HttpStatus status, String message, Throwable cause) {
		super(message, cause);
		this.status = status;
	}

	public HttpServiceException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}

	public HttpServiceException(HttpStatus status, Throwable cause) {
		super(cause);
		this.status = status;
	}

	/**
	 * Get HttpStatus object
	 * @return httpstatus
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * Get http reason
	 * @return reason text
	 */
	public String getReason() {
		return status.getReasonPhrase();
	}

	/**
	 * Get http code
	 * @return code
	 */
	public int getCode() {
		return status.value();
	}
}
