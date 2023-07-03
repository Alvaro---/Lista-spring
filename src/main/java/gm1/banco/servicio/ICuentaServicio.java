package gm1.banco.servicio;

import gm1.banco.modelo.Cuenta;

import java.util.List;

public interface ICuentaServicio {
    public List<Cuenta> listarCuentas();

    public Cuenta buscarCuentaPorId(Integer idCuenta);

    public void guardarCuenta(Cuenta cuenta);
    //Si el id es null hace un update, y si no un insert

    public void eliminarCuenta (Cuenta cuenta);
}
