package hn.unah.poo.proyecto.modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cliente")
public class Cliente {
    
    @Id
    private String dni;

    private String nombre;

    private String apellido;

    private String telefono;

    private String correo;

    private double sueldo;

    @OneToOne(mappedBy = "cliente")
    private Direccion direccion;
}
