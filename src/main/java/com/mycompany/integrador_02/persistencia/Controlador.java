package com.mycompany.integrador_02.persistencia;

import com.mycompany.integrador_02.logica.Administrador;
import com.mycompany.integrador_02.logica.Barista;
import com.mycompany.integrador_02.logica.Cafe;
import com.mycompany.integrador_02.logica.Camarero;
import com.mycompany.integrador_02.logica.Pedido;
import com.mycompany.integrador_02.logica.Producto;
import com.mycompany.integrador_02.logica.Usuario;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controlador {

    ControladorDePersistencia cp = new ControladorDePersistencia();

    public void crarAdmin(Administrador ad) {
        cp.crearAdmin(ad);
    }

    public void editarProducto(Producto p) {
        cp.editarProducto(p);
    }

    public void editarCafe(Cafe c) {
        cp.editarCafe(c);
    }

    public void crearPedido(Pedido p) {
        cp.crearPedido(p);
    }

    public void crearUsuario(Usuario us) {
        cp.crearUsuario(us);
    }

    public Camarero buscarCamarero(int id) {
        return cp.buscarCamarero(id);
    }

    public boolean comprobarIngreso(String nombreUs, String contrasenia) {
        boolean ingreso = false;

        /*
      este metodo se encargara de buscar por nombre de usuario y luego por contraseña si ya existe el usuario en la bd.
      este metodo de validacion no es el mas optimo ya que trae todos los usarios de la db a la logica y los comprueba uno por uno 
       en lugar de hacer todo esto en la db.
      El 1er if se encarga de inicializar el objeto us, para que su metodo get.NombreUs() no arroje null.
         */
        List<Usuario> usuarios = new ArrayList<Usuario>();
        usuarios = cp.trarUsuarios();

        for (Usuario us : usuarios) {
            if (us.getNombreUs() == null) {
                System.out.println("------------EL NOMBRE DE USUARIO ES NULL");
            } else {
                if (us.getNombreUs().equals(nombreUs)) {
                    if (us.getContrasenia().equals(contrasenia)) {
                        ingreso = true;
                    } else {
                        ingreso = false;
                    }
                }
            }
        }
        return ingreso;
    }

    public String obtenerRol(String nombreUs) {

        String result = "";
        List<Usuario> usuarios = cp.trarUsuarios();

        for (Usuario usu : usuarios) {
            if (usu.getNombreUs() == null) {
                System.out.println("------------EL NOMBRE DE USUARIO ES NULL");
            } else {
                if (usu.getNombreUs().equals(nombreUs)) {
                    result = usu.getRol();
                    break;
                } else {
                    System.out.println("-----EL USUARIO NO EXISTE");
                }
            }
        }
        return result;
    }

    public boolean registrarUsuario(String nombreUs, String contra, String rol) {
        boolean registro = true;
        List<Usuario> usuarios = cp.trarUsuarios();

        for (Usuario us : usuarios) {
            try {
                if (us.getNombreUs() == null) {
                    System.out.println("------------EL NOMBRE DE USUARIO ES NULL");
                } else if (us.getNombreUs().equals(nombreUs)) {
                    System.out.println("-------------YA EXISTE EL NOMBRE DE USUARIO");
                    registro = false;
                }
            } catch (NullPointerException e) {
                System.err.println("---------ERROR AL ACCEDER AL NOMBRE DE USUARIO " + e.getMessage());
            }
        }

        if (registro) {
            try {
                Usuario usu = new Usuario();
                usu.setContrasenia(contra);
                usu.setNombreUs(nombreUs);
                usu.setRol(rol);
                cp.crearUsuario(usu);
                registro = true;
            } catch (Exception e) {
                System.err.println("------ERROR AL CREAR EL USUARIO " + e.getMessage());
            }
        }

        return registro;
    }

    public boolean comprobarRolExistente() {
        boolean elRolYaExiste = true;
        List<Usuario> usuarios = cp.trarUsuarios();

        for (Usuario u : usuarios) {
            if (u.getRol().equals("Administrador")) {
                elRolYaExiste = false;
            }
        }
        return elRolYaExiste;
    }

    public void eliminarUsuario(int idUsu) {
        cp.eliminarUsuario(idUsu);
    }

    public Integer obtenerId(String nombreUs) {
        int idUs = 0;
        List<Usuario> usuarios = cp.trarUsuarios();

        for (Usuario u : usuarios) {
            if (u.getNombreUs().equals(nombreUs)) {
                if (u.getRol().equals("Administrador") || u.getRol().equals("Barista") || u.getRol().equals("Camarero")) {
                    idUs = u.getId();
                }
            }
        }
        return idUs;
    }

    public void añadirProducto(String nombre, String descripcion, String precio, String disponibleString, String categoria, String nombreDeUsuario, String variedadDeCafe, String descripcionDelCafe) {

        boolean disponibleBool = false;
        if (disponibleString.equals("si")) {
            disponibleBool = true;
        } else {
            disponibleBool = false;
        }

        Barista unBarista = buscarUsuarioPorNombre(nombreDeUsuario).getUnBarista();

        Producto p = new Producto();
        Cafe cafe = new Cafe();
        cafe.setNombre(variedadDeCafe);
        cafe.setDescripcion(descripcionDelCafe);
        cafe.setUnBarista(unBarista);
        p.setUnCafe(cafe);
        p.setNombre(nombre);
        p.setDescripcion(descripcion);
        p.setCategoria(categoria);
        p.setPrecio(precio);
        p.setEstaDisponible(disponibleBool);
        p.setUnBarista(unBarista);
        cp.crearCafe(cafe);
        cp.crearProducto(p);
    }

    public Producto buscarProducto(int id_producto) {
        return cp.buscarProducto(id_producto);
    }

    public List<Producto> buscarProductos() {
        return cp.buscarProductos();
    }

    public void editarPedido(Pedido pedido) {
        cp.editarPedido(pedido);
    }

    public List<Pedido> buscarPedidos() {
        return cp.buscarPedidos();
    }

    public Usuario buscarUsuario(int id_usuario) {
        return cp.buscarUsuario(id_usuario);
    }

    public void crearCamarero(Camarero camarero) {
        cp.crearCamarero(camarero);
    }

    public void eliminarPedido(int idPedido) {
        cp.eliminarPedido(idPedido);
    }

    public Usuario buscarUsuarioPorNombre(String nombreDeUsuario) {
        List<Usuario> usuarios = cp.trarUsuarios();
        Usuario usuarioEncontrado = null;
        for (Usuario usu : usuarios) {
            if (usu.getNombreUs().equals(nombreDeUsuario)) {
                usuarioEncontrado = usu;
                break;
            }
        }
        return usuarioEncontrado;
    }

    public void añadirBarista(String nombre, String apellido, String dni, String genero, String fechaDeIngreso, String diasDeTrabajo, String horariosDeTrabajo, String habilidadArteLatte, String telefono, String salario, String nombreDeUsuario, String fechaNac) {

        Boolean habilidadArteLatteBool = false;
        if (habilidadArteLatte != null) {
            habilidadArteLatteBool = true;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;
        try {
            fecha = formatter.parse(fechaNac);
        } catch (ParseException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }

        Usuario us = buscarUsuarioPorNombre(nombreDeUsuario);
        Barista barista = new Barista();
        barista.setNombre(nombre);
        barista.setApellido(apellido);
        barista.setDni(dni);
        barista.setGenero(genero);
        barista.setFechNac(fecha);
        barista.setFechaIngreso(fechaDeIngreso);
        barista.setDiasTrabajo(diasDeTrabajo);
        barista.setHorariosTrabajo(horariosDeTrabajo);
        barista.setHabilidadArteLatte(habilidadArteLatteBool);

        barista.setTelefono(telefono);
        barista.setSueldo(salario);
        barista.setUnUsuario(us);
        cp.crearBarista(barista);

    }

    public void eliminarBarista(String nombreUsuario) {
        Usuario us = buscarUsuarioPorNombre(nombreUsuario);

        Barista baristaEncontrado = us.getUnBarista();
        cp.eliminarBarista(baristaEncontrado.getId());
    }

    public void agregarUnCamarero(String nombre, String apellido, String dni, String genero, String fechaDeIngreso, String diasDeTrabajo, String horariosDeTrabajo, String mesasQueAtiende, String zonaDeTrabajo, String telefono, String salario, String nombreDeUsuario, String fechaDeNac) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;
        try {
            fecha = formatter.parse(fechaDeNac);
        } catch (ParseException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }

        Usuario us = buscarUsuarioPorNombre(nombreDeUsuario);
        Camarero camarero = new Camarero();
        camarero.setNombre(nombre);
        camarero.setApellido(apellido);
        camarero.setDni(dni);
        camarero.setFechNac(fecha);
        camarero.setGenero(genero);
        camarero.setTelefono(telefono);
        camarero.setFechaIngreso(fechaDeIngreso);
        camarero.setDiasTrabajo(diasDeTrabajo);
        camarero.setHorariosTrabajo(horariosDeTrabajo);
        camarero.setMesasQueAtiende(mesasQueAtiende);
        camarero.setZonaDeTrabajo(zonaDeTrabajo);
        camarero.setSueldo(salario);
        camarero.setUnUsuario(us);
        cp.crearCamarero(camarero);
    }

    public void eliminarCamarero(String nombreUsuario) {
        Camarero camareroEncontrado = buscarUsuarioPorNombre(nombreUsuario).getUnCamarero();
        cp.eliminarCamarero(camareroEncontrado.getId());

    }

    public void eliminarProducto(int idProducto) {
        cp.eliminarProducto(idProducto);
    }

    public Cafe buscarCafe(int idCafe) {
        return cp.buscarCafe(idCafe);
    }

    public void editarUnBarista(String nombre, String apellido, String dni, String genero, String fechaDeIngreso, String diasDeTrabajo, String horariosDeTrabajo, String habilidadArteLatte, String salario, String telefono, String fechaNac, int baristaId) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;
        try {
            fecha = formatter.parse(fechaNac);
        } catch (ParseException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        Boolean habilidadArteLatteBool = false;
        if (habilidadArteLatte != null) {
            habilidadArteLatteBool = true;
        }
        Barista b = cp.buscarBaristaPorId(baristaId);
        b.setNombre(nombre);
        b.setApellido(apellido);
        b.setDni(dni);
        b.setGenero(genero);
        b.setFechNac(fecha);
        b.setFechaIngreso(fechaDeIngreso);
        b.setDiasTrabajo(diasDeTrabajo);
        b.setHorariosTrabajo(horariosDeTrabajo);
        b.setSueldo(salario);
        b.setTelefono(telefono);
        b.setHabilidadArteLatte(habilidadArteLatteBool);
        cp.editarBarista(b);
    }

    public void editarUnCamarero(String nombre, String apellido, String dni, String genero, String fechaDeIngreso, String diasDeTrabajo, String horariosDeTrabajo, String mesasQueAtiende, String zonaDeTrabajo, String salario, String telefono, String nombreDeUsuario, String fechaNac, int camareroId) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;
        try {
            fecha = formatter.parse(fechaNac);

        } catch (ParseException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }

        Camarero camarero = cp.buscarCamarero(camareroId);
        camarero.setNombre(nombre);
        camarero.setApellido(apellido);
        camarero.setDni(dni);
        camarero.setFechNac(fecha);
        camarero.setGenero(genero);
        camarero.setTelefono(telefono);
        camarero.setFechaIngreso(fechaDeIngreso);
        camarero.setDiasTrabajo(diasDeTrabajo);
        camarero.setHorariosTrabajo(horariosDeTrabajo);
        camarero.setMesasQueAtiende(mesasQueAtiende);
        camarero.setZonaDeTrabajo(zonaDeTrabajo);
        camarero.setSueldo(salario);

        cp.editarCamarero(camarero);
    }

    public void editarUnProducto(String nombre, String descripcion, String precio, String disponibleString, String categoria, String nombreDeUsuario, String variedadDeCafe, String descripcionDelCafe, int idProducto, int idCafe) {

        boolean disponibleBool = false;
        if (disponibleString.equals("si")) {
            disponibleBool = true;
        } else {
            disponibleBool = false;
        }

        Producto pro = cp.buscarProducto(idProducto);
        Cafe cafeEncontrado = cp.buscarCafe(idCafe);
        Barista baristaEncontrado = buscarUsuarioPorNombre(nombreDeUsuario).getUnBarista();
        cafeEncontrado.setNombre(variedadDeCafe);
        cafeEncontrado.setDescripcion(descripcionDelCafe);
        cafeEncontrado.setUnBarista(baristaEncontrado);
        pro.setNombre(nombre);
        pro.setDescripcion(descripcion);
        pro.setPrecio(precio);
        pro.setEstaDisponible(disponibleBool);
        pro.setCategoria(categoria);
        pro.setUnBarista(baristaEncontrado);
        pro.setUnCafe(cafeEncontrado);

        cp.editarProducto(pro);
        cp.editarCafe(cafeEncontrado);
    }

}
