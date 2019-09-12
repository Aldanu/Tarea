package SistemaRiders;

public class RiderNotFoundException extends RuntimeException {

    RiderNotFoundException(Long id) {
        super("No se logro encontrar el rider " + id);
    }
}