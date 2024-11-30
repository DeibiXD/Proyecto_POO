package hn.unah.poo.proyecto.servicios;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hn.unah.poo.proyecto.dtos.ClienteDTO;
import hn.unah.poo.proyecto.dtos.DireccionDTO;
import hn.unah.poo.proyecto.modelos.Cliente;
import hn.unah.poo.proyecto.modelos.Direccion;
import hn.unah.poo.proyecto.modelos.Prestamos;
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

    public Cliente obtenerPorDni(String dni){
        modelMapper = new ModelMapper();
        Optional<Cliente> cliente = clienteRepositorio.findById(dni);
        if (cliente.isPresent()){
            Cliente clienteAgregar = cliente.get();
            //ClienteDTO clienteDto =  this.modelMapper.map(clienteAgregar, ClienteDTO.class);
            //Direccion direccion = cliente.get().getDireccion();
            //DireccionDTO direccionDTO = this.modelMapper.map(direccion, DireccionDTO.class);
            //System.out.println(clienteDto.toString() + direccionDTO.toString());
            
            return clienteAgregar;
        }
       return null;
    }

    public List<Cliente> obtenerTodos(){
        return clienteRepositorio.findAll();
}
}
