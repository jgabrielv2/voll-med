package med.voll.api.infra.exception;

public class TokenInvalidoException extends RuntimeException {
    public TokenInvalidoException(String msg) {
        super(msg);
    }
}
