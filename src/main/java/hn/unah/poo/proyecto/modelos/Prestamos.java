package hn.unah.poo.proyecto.modelos;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "prestamos")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Prestamos {
    @Id
    @Column(name = "idprestamo")
    private int idPrestamo;

    private BigDecimal monto;

    private int plazo;

    @Column(name = "tasa_interes")
    private BigDecimal tasa_interes;

    private BigDecimal cuota;

    private char estado;

    @Column(name = "tipoprestamo")
    private char tipoPrestamo;

    @OneToMany(mappedBy = "prestamos")
    private List<Tabla_Amortizacion> tabla_amortizacion;

    @ManyToMany(mappedBy = "prestamos")
    private List<Cliente> clientes;
}
