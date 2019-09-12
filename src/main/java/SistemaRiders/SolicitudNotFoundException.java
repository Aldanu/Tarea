package SistemaRiders;

public class SolicitudNotFoundException extends RuntimeException {

    SolicitudNotFoundException(Long id) {
        super("No se logro encontrar la solicitud " + id);
    }
}