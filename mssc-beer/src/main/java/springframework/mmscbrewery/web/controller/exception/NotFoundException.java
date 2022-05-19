package springframework.mmscbrewery.web.controller.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String err) {
        super(err);
    }
}