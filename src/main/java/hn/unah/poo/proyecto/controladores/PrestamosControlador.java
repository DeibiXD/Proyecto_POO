package hn.unah.poo.proyecto.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.unah.poo.proyecto.dtos.PrestamosDTO;
import hn.unah.poo.proyecto.modelos.Prestamos;
import hn.unah.poo.proyecto.servicios.PrestamosServicios;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/prestamos")
public class PrestamosControlador {
    
    @Autowired
    private PrestamosServicios prestamosServicios;

    @PostMapping("/crear")
    public String crearPrestamos(@RequestBody PrestamosDTO prestamosDTO, @RequestParam(name = "dni",defaultValue = "0")String dni) {
        return prestamosServicios.crearPrestamos(prestamosDTO,dni);
    }

    @GetMapping("/buscar/dni")
    public List<Prestamos> buscarPorDni(@RequestParam(name = "dni") String dni) {
        return prestamosServicios.buscarPorDni(dni);
    }

    @GetMapping("/buscar/id")
    public Prestamos buscarPorId(@RequestParam(name = "id") int id) {
        return prestamosServicios.buscarPorId(id);
    }
    
    @PostMapping("/agregar/cliente")
    public String agregarPrestamoExistente_A_Cliente(
        @RequestParam(name="dni") String dni,
        @RequestParam(name="id")int idPrestamo) {
        
        return prestamosServicios.agregarPrestamoExistente_A_Cliente(dni,idPrestamo) ;
    }
    
    @GetMapping("/obtener/saldo")
    public String sumarPendientesCuotasDelPrestamo(@RequestParam(name = "id") int id) {
        return prestamosServicios.sumarPendientesCuotasDelPrestamo(id);
    }
    
    @PostMapping("/pagar/cuota")
    public String pagarUltimaCuota(@RequestParam(name = "id")int id){
        return prestamosServicios.pagarUltimaCuota(id);
    }
    
}
