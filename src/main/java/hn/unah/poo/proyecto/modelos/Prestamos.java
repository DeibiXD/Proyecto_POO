package hn.unah.poo.proyecto.modelos;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import hn.unah.poo.proyecto.Enums.tipoPrestamoEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "prestamos")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Prestamos {
    @Id
    @Column(name = "idprestamo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPrestamo;
    //El monto total para pagar el prestamo
    private BigDecimal monto;

    //Este valor es en anios
    private int plazo;

    @Column(name = "tasa_interes")
    //Por Selecion de tipo prestamo de enum
    private BigDecimal tasa_interes;

    //Por Formula
    private BigDecimal cuota;

    //Es P si quedan pagos, es A si ya estan todos pagodos
    private char estado;

    @Column(name = "tipoprestamo")
    @Enumerated(EnumType.STRING)
    private tipoPrestamoEnum tipoPrestamo;

    @OneToMany(mappedBy = "prestamos",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Tabla_Amortizacion> tabla_amortizacion;

    @ManyToMany(mappedBy = "prestamos",cascade =CascadeType.ALL)
    @JsonIgnoreProperties({"prestamos"})
    private List<Cliente> clientes;
}
