package hn.unah.poo.proyecto.dtos;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    
    
    private String dni;

    private String nombre;

    private String apellido;

    private String telefono;

    private String correo;

    private BigDecimal sueldo;

    private DireccionDTO direccionDTO;

    private List<PrestamosDTO> prestamosDTO;

    
}
