package SistemaRiders;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Rider {

    private @Id @GeneratedValue Long id;
    private String nombrep;
    private String apellido;
    private String edad;
    private String CI;

    Rider(){}

    Rider(String nombrep, String apellido, String edad, String CI){
        this.nombrep=nombrep;
        this.apellido=apellido;
        this.edad=edad;
        this.CI=CI;
    }

    public String getNombre() {
        return this.nombrep + " " + this.apellido;
    }

    public void setNombre(String name) {
        String[] parts =name.split(" ");
        this.nombrep = parts[0];
        this.apellido = parts[1];
    }
}
