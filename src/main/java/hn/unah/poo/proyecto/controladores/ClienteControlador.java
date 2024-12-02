package hn.unah.poo.proyecto.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hn.unah.poo.proyecto.dtos.ClienteDTO;
import hn.unah.poo.proyecto.modelos.Cliente;
import hn.unah.poo.proyecto.servicios.ClienteServicios;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/cliente")
@Tag(name = "Cliente", description = "Metodos relacionados con Clientes")
public class ClienteControlador {

    @Autowired
    private ClienteServicios clienteServicios;

    @PostMapping("/crear/nuevo")
    @Operation(summary = "Crea un nuevo Cliente", description = "Permite crear un cliente con todas sus caracteristicas.")
    public String crearNuevoCliente(@RequestBody ClienteDTO dni) {
        return this.clienteServicios.crearCliente(dni);
    }
    
    
    @GetMapping("/obtener/dni")
    @Operation(summary = "Obtiene un cliente por su nmero de DNI", description = "Este retorna todos los dettales de nuestro Cliente el cual buscamos por su DNI")
    public Cliente obtenerPorId(@RequestParam(name="dni") String dni) {
        return this.clienteServicios.obtenerPorDni(dni);
        
}

    @GetMapping("/obtener")
    @Operation(summary = "Este metodo obtiene todos los clientes y sus datos", description = "Nos retorna todos los clientes y sus Datos como direccion, prestamos,etc")
        public List<Cliente> obtenerTodos(){
        return this.clienteServicios.obtenerTodos();
        }

}
