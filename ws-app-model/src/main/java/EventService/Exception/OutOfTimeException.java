package EventService.Exception;

public class OutOfTimeException extends Exception {
    private Long id;
    public OutOfTimeException(Long id ) {
        super("Error, acción fuera de rango de tiempo permitido para: " + id);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}