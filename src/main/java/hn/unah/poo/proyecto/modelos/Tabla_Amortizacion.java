package hn.unah.poo.proyecto.modelos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tabla_amortizacion")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Tabla_Amortizacion {
    
    @Id
    private int idTablaAmortizacion;

    @Column(name = "numerocuota")
    private int numeroCuota;

    private BigDecimal interes;

    private BigDecimal capital;

    private BigDecimal saldo;

    private char estado;

    @Column(name = "fechavencimiento")
    private LocalDate fechaVencimiento;

    @ManyToOne
    @JoinColumn(name = "idprestamo", referencedColumnName = "idprestamo")
    @JsonIgnore
    private Prestamos prestamos;
    
}
