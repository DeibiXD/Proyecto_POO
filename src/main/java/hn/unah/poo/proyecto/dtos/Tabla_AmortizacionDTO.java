package hn.unah.poo.proyecto.dtos;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tabla_AmortizacionDTO {
   private int numeroCuota;
   
   private BigDecimal interes;

   private BigDecimal capital;

   private BigDecimal saldo;

   private LocalDate fechaVencimiento;

   private int id_tabla_amortizacion;

   private char estado;
}
