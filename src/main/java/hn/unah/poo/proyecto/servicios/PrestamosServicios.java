package hn.unah.poo.proyecto.servicios;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import hn.unah.poo.proyecto.dtos.PrestamosDTO;
import hn.unah.poo.proyecto.modelos.Prestamos;
import hn.unah.poo.proyecto.modelos.Tabla_Amortizacion;
import hn.unah.poo.proyecto.repositorios.AmortizacionRepositorio;
import hn.unah.poo.proyecto.repositorios.ClienteRepositorio;
import hn.unah.poo.proyecto.repositorios.PrestamosRepositorio;
import hn.unah.poo.proyecto.modelos.Cliente;

@Service
public class PrestamosServicios {
    
    @Autowired
    private PrestamosRepositorio prestamosRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private AmortizacionRepositorio amortizacionRepositorio;

    ModelMapper modelMapper;

    @Value("${prestamos.tasa.vehicula}")
    private BigDecimal tasaVehicular;

    @Value("${prestamos.tasa.personal}")
    private BigDecimal tasaPersonal;

    @Value("${prestamos.tasa.hipotecario}")
    private BigDecimal tasaHipotecario;

   
    public List<Prestamos> buscarPorDni(String dni) {
        return prestamosRepositorio.findPrestamosByClienteDni(dni);
    }

    public Prestamos buscarPorId(int id) {

        if (prestamosRepositorio.existsById(id)){
            return prestamosRepositorio.findById(id).get();
        }
        return null;

    }

    public String crearPrestamos(PrestamosDTO prestamosDTO, String dni) {
        modelMapper = new ModelMapper();
        Prestamos prestamosParaAgregar = modelMapper.map(prestamosDTO, Prestamos.class);
        switch (prestamosDTO.getTipoPrestamo()) {
            case V:
                prestamosParaAgregar.setTasa_interes(tasaVehicular);
                break;
        
            case P:
                prestamosParaAgregar.setTasa_interes(tasaPersonal);
                break;

            case H:
                prestamosParaAgregar.setTasa_interes(tasaHipotecario);
        }
        prestamosRepositorio.saveAndFlush(prestamosParaAgregar);
        if (clienteRepositorio.existsById(dni)){
            Cliente cliente = clienteRepositorio.findById(dni).get();
            cliente.getPrestamos().add(prestamosParaAgregar);
            clienteRepositorio.save(cliente);
        }
        return "Agregado Correctamente";
    }

    public String agregarPrestamoExistente_A_Cliente(String dni, int idPrestamo) {
        if (clienteRepositorio.existsById(dni) && prestamosRepositorio.existsById(idPrestamo)){
            Cliente cliente = clienteRepositorio.findById(dni).get();
            Prestamos prestamos = prestamosRepositorio.findById(idPrestamo).get();
            cliente.getPrestamos().add(prestamos);
            clienteRepositorio.save(cliente);
            return "Se agrego prestamo a cliente";
        }
        return "No existe una de las entidades";
    }

    public String sumarPendientesCuotasDelPrestamo(int id) {
        if(prestamosRepositorio.existsById(id)){
        List<Tabla_Amortizacion> listaAmortizacions = prestamosRepositorio.findById(id).get().getTabla_amortizacion();
        BigDecimal sumaTotal = BigDecimal.ZERO;
        for (Tabla_Amortizacion elementosTabla_Amortizacion : listaAmortizacions) {
            if (elementosTabla_Amortizacion.getEstado()=='P'){
                sumaTotal = sumaTotal.add(elementosTabla_Amortizacion.getCapital());
            }
        }
        return sumaTotal.toString();
    }
    return "Este prestamo no existe";
    }

    public String pagarUltimaCuota(int id) {
        if(prestamosRepositorio.existsById(id)){
            Prestamos prestamoAPagar = prestamosRepositorio.findById(id).get();
            List<Tabla_Amortizacion> tablaDeCuotas = prestamoAPagar.getTabla_amortizacion();
            Tabla_Amortizacion pagoMasReciente = tablaDeCuotas.stream()
            .filter(tA -> tA.getEstado()=='P')
            .max(Comparator.comparing(Tabla_Amortizacion::getFechaVencimiento).reversed())
            .orElse(null); 
            if (pagoMasReciente == null){
                return "No hay pagos pendientes";
            }
            pagoMasReciente.setEstado('A');
             amortizacionRepositorio.save(pagoMasReciente);
             return "Se pago con exito";
        }
        return "No encontramos el prestamo";
    }

}
