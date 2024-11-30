package hn.unah.poo.proyecto.servicios;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hn.unah.poo.proyecto.dtos.ClienteDTO;
import hn.unah.poo.proyecto.modelos.Cliente;
import hn.unah.poo.proyecto.repositorios.ClienteRepositorio;


@Service
public class ClienteServicios {

   
    private ModelMapper modelMapper;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public String crearCliente(ClienteDTO nvoCliente){        
        if(this.clienteRepositorio.existsById(nvoCliente.getDni())){
            return "Ya existe el cliente";
        }
        modelMapper = new ModelMapper();        
        Cliente nvoClienteBd = this.modelMapper.map(nvoCliente, Cliente.class);
        
        this.clienteRepositorio.save(nvoClienteBd);
        return "Cliente almacenado satisfactoriamente";
    }
    
}
