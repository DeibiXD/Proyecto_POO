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
import hn.unah.poo.proyecto.servicios.ClienteServicios;

@RestController
@RequestMapping("api/cliente")
public class ClienteControlador {

    @Autowired
    private ClienteServicios clienteServicios;

    @PostMapping("/crear/nuevo")
    public String crearNuevoCliente(@RequestBody ClienteDTO dni) {
        return this.clienteServicios.crearCliente(dni);
    }
    
     @GetMapping("/obtener/dni")
    public ClienteDTO obtenerPorId(@RequestParam(name="dni") String dni) {
        return this.clienteServicios.obtenerPorDni(dni);
        
}

    @GetMapping("/obtener")
        public List<ClienteDTO> obtenerTodos(){
        return this.clienteServicios.obtenerTodos();
        }

}
