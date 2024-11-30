package hn.unah.poo.proyecto.controladores;

<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    

=======
public class ClienteControlador {
    
>>>>>>> f0a38e9b069671a5b4ab0251d0873b3d0cf4c0f4
}
