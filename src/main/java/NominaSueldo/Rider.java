package NominaSueldo;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Rider {

    private @Id @GeneratedValue Long id;
    private String nombre;
    private String edad;
    private String CI;

    Rider(){}

    Rider(String nombre, String edad, String CI){
        this.nombre=nombre;
        this.edad=edad;
        this.CI=CI;
    }
}
