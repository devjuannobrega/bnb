package br.gov.bnb.s489.utils.exception;

public class XmlValidationException extends RuntimeException {
    public XmlValidationException(String message) {
        super(message);
    }
    public XmlValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
