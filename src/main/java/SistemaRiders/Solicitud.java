package SistemaRiders;

import ch.qos.logback.core.status.Status;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "SOLICITUD_RIDER")
public class Solicitud {

    private @Id @GeneratedValue Long id;

    private String descripcion;
    private Status status;

    Solicitud() {}

    Solicitud(String descripcion, Status status) {

        this.descripcion = descripcion;
        this.status = status;
    }
}
