@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String msg) { super(msg); }
}
