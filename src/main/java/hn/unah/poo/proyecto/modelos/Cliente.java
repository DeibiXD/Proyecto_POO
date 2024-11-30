package hn.unah.poo.proyecto.modelos;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    @Column(length = 20)
    private String dni;

    private String nombre;

    private String apellido;

    private String telefono;

    private String correo;

    private BigDecimal sueldo;

    @OneToOne(mappedBy = "cliente")
    private Direccion direccion;

    @ManyToMany
    @JoinTable(
        name = "cliente_prestamos",
        joinColumns = { @JoinColumn(name="dni")},
        inverseJoinColumns = {@JoinColumn(name="idprestamo")}
    )
    private List<Prestamos> prestamos;
}
