
package com.mycompany.integrador_02.servlets;

import com.mycompany.integrador_02.logica.Camarero;
import com.mycompany.integrador_02.persistencia.Controlador;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "svCamarero", urlPatterns = {"/svCamarero"})
public class svCamarero extends HttpServlet {

    Controlador control = new Controlador();
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombreDeUsuario = request.getParameter("nombreDeUsuario");
        Camarero camarero = control.buscarUsuarioPorNombre(nombreDeUsuario).getUnCamarero();
         HttpSession sesion = request.getSession();
        sesion.setAttribute("camareroEditar",camarero);
        
        response.sendRedirect("editarCamarero.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        
        if(accion.equals("agregar")){      
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String dni = request.getParameter("dni");
        String genero = request.getParameter("genero");
        String fechaDeIngreso = request.getParameter("fechaIngreso");
        String diasDeTrabajo = request.getParameter("diasDeTrabajo");
        String horariosDeTrabajo = request.getParameter("horariosDeTrabajo");
        String mesasQueAtiende = request.getParameter("mesasQueAtiende");
        String zonaDeTrabajo = request.getParameter("zonaDeTrabajo");
        String salario = request.getParameter("salario");
        String telefono = request.getParameter("telefono");
        String nombreDeUsuario = request.getParameter("nombreDeUsuario");
        String fechaDeNac = request.getParameter("fechaNac");
        
        control.agregarUnCamarero(nombre,apellido,dni,genero,fechaDeIngreso,diasDeTrabajo,horariosDeTrabajo,mesasQueAtiende,zonaDeTrabajo,salario,telefono,nombreDeUsuario, fechaDeNac);
        response.sendRedirect("administrador.jsp");
        }else if(accion.equals("eliminar")){
            String nombreUsuario = request.getParameter("nombreUsuario");
            control.eliminarCamarero(nombreUsuario);
            response.sendRedirect("administrador.jsp");
        }else if(accion.equals("editar")){
            int camareroId = Integer.parseInt(request.getParameter("camareroId"));
            String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String dni = request.getParameter("dni");
        String genero = request.getParameter("genero");
        String fechaDeIngreso = request.getParameter("fechaIngreso");
        String diasDeTrabajo = request.getParameter("diasDeTrabajo");
        String horariosDeTrabajo = request.getParameter("horariosDeTrabajo");
        String mesasQueAtiende = request.getParameter("mesasQueAtiende");
        String zonaDeTrabajo = request.getParameter("zonaDeTrabajo");
        String salario = request.getParameter("salario");
        String telefono = request.getParameter("telefono");
        String nombreDeUsuario = request.getParameter("nombreDeUsuario");
        String fechaNac = request.getParameter("fechaNac");
        
        control.editarUnCamarero(nombre,apellido,dni,genero,fechaDeIngreso,diasDeTrabajo,horariosDeTrabajo,mesasQueAtiende,zonaDeTrabajo,salario,telefono,nombreDeUsuario, fechaNac, camareroId);
        response.sendRedirect("administrador.jsp");
        }
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
