package org.giwi.geotracker.exception;

/**
 * The type Business exception.
 */
public class BusinessException extends RuntimeException {
    private int statusCode;

    /**
     * Instantiates a new Business exception.
     */
    public BusinessException() {
        super();
        statusCode = 500;
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param e the e
     */
    public BusinessException(BusinessException e) {
        super(e);
        statusCode = e.getStatusCode();
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param code the code
     */
    public BusinessException(int code) {
        super();
        statusCode = code;
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param message the message
     */
    public BusinessException(String message) {
        super(message);
        statusCode = 500;
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        statusCode = 500;
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param message the message
     * @param code    the code
     */
    public BusinessException(String message, int code) {
        super(message);
        statusCode = code;
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param cause the cause
     * @param code  the code
     */
    public BusinessException(Throwable cause, int code) {
        super(cause);
        statusCode = code;
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param cause the cause
     */
    public BusinessException(Throwable cause) {
        super(cause);
        statusCode = 500;
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param message the message
     * @param cause   the cause
     * @param code    the code
     */
    public BusinessException(String message, Throwable cause, int code) {
        super(message, cause);
        statusCode = code;
    }

    /**
     * Gets status code.
     *
     * @return the status code
     */
    public int getStatusCode() {
        return statusCode;
    }
}
