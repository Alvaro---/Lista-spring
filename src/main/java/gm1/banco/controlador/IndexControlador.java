package gm1.banco.controlador;

import gm1.banco.modelo.Cuenta;
import gm1.banco.servicio.CuentaServicio;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ViewScoped
public class IndexControlador {
    @Autowired
    CuentaServicio cuentaServicio;
    private List<Cuenta> cuentas;
    private Cuenta cuentaSeleccionada;

    private static final Logger logger =
            LoggerFactory.getLogger(IndexControlador.class);

    @PostConstruct
    public void init(){
        cargarDatos();
    }

    public void cargarDatos(){
        this.cuentas = cuentaServicio.listarCuentas();
        cuentas.forEach((cuenta)->logger.info(cuenta.toString()));
    }

    public void agregarCuenta(){
        this.cuentaSeleccionada = new Cuenta();
    }

    public void guardarCuenta (){
        logger.info("Cuenta a guardar: "+this.cuentaSeleccionada);
        //Agregar
        if(this.cuentaSeleccionada.getIdCuenta() == null ){
            this.cuentaServicio.guardarCuenta(this.cuentaSeleccionada);
            this.cuentas.add(this.cuentaSeleccionada);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cuenta Agregada"));
        }
        //Modificar
        else{
            this.cuentaServicio.guardarCuenta(this.cuentaSeleccionada);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cuenta Actualizada"));

        }
        // Ocultar ventana
        PrimeFaces.current().executeScript("PF('ventanaModalCuenta').hide()");
        //Actualizamos la tabla
        PrimeFaces.current().ajax().update("forma-cuentas:mensajes",
                "forma-cuentas:cuentas-tabla");
        //reset de la tabla
        this.cuentaSeleccionada = null;
    }

    public void eliminarCuenta () {
        logger.info("Cuenta a eliminar ", this.cuentaSeleccionada);
        this.cuentaServicio.eliminarCuenta(this.cuentaSeleccionada);
        //eliminar de la lista
        this.cuentas.remove(this.cuentaSeleccionada);
        //reset de la tabla
        this.cuentaSeleccionada = null;
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Cuenta eliminada"));
        PrimeFaces.current().ajax().update("forma-cuentas:mensajes",
                "forma-cuentas:cuentas-tabla");
    }
}
