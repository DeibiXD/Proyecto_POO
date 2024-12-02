package hn.unah.poo.proyecto.modelos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tabla_amortizacion")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tabla_Amortizacion {
    
    @Id
    @Column(name = "id_tabla_amortizacion")
    private int idTablaAmortizacion;

    @Column(name = "numerocuota")
    //Plazo de prestamo * 12
    //Inician en 0
    private int numeroCuota;

    private BigDecimal interes;

    //Siempre es majos que interes
    private BigDecimal capital;

    private BigDecimal saldo;

    private char estado;

    @Column(name = "fechavencimiento")
    private LocalDate fechaVencimiento;

    @ManyToOne
    @JoinColumn(name = "idprestamo", referencedColumnName = "idprestamo")
    @JsonIgnoreProperties({"tabla_amortizacion"})
    private Prestamos prestamos;
    
}
