package hn.unah.poo.proyecto.dtos;
import java.math.BigDecimal;

import hn.unah.poo.proyecto.Enums.tipoPrestamoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrestamosDTO {
    
    private BigDecimal monto;
    //En Anios
    private int plazo;

    private char estado;

    private tipoPrestamoEnum tipoPrestamo;
    
}
