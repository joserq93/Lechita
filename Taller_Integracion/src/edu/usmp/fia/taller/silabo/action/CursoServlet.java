package edu.usmp.fia.taller.silabo.action;
import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.usmp.fia.taller.common.bean.silabo.BibliografiaBean;
import edu.usmp.fia.taller.common.bean.silabo.CursoBean;
import edu.usmp.fia.taller.common.bean.silabo.SilaboBean;
import edu.usmp.fia.taller.common.dao.DAOFactory;
import edu.usmp.fia.taller.silabo.interfaces.*;

@WebServlet("/Curso")
public class CursoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CursoServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			DAOFactory dao = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
			DAOSilabo obj = dao.getSilaboDAO();
			Vector<CursoBean> object=obj.listarCurso();
			request.setAttribute("LIST_CURSO",object);
			getServletContext().getRequestDispatcher("/CatalogoCurso/Index.jsp").forward(request, response);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		try {
			DAOFactory dao = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
			DAOSilabo plan = dao.getSilaboDAO();
			String dato=request.getParameter("btn_funcion");
			String codigo = request.getParameter("id_curso");
			if (dato.equals("Agregar Curso")){
				String codigoCurso=request.getParameter("codigo");
				String nombre=request.getParameter("nombre");
				String tipo=request.getParameter("tipo");
				String sumilla=request.getParameter("sumilla");
				boolean v=plan.validarCurso(nombre,codigoCurso);
				if(v==true){
					String pre_cod=request.getParameter("pre_fac");
					plan.agregarCurso(pre_cod+codigoCurso,nombre,tipo,sumilla);
					Vector<CursoBean> object=plan.listarCurso();
					request.setAttribute("LIST_CURSO",object);
					request.setAttribute("Mensaje","Se agrego el CURSO correctamente");
					getServletContext().getRequestDispatcher("/CatalogoCurso/Index.jsp").forward(request, response);
				}else{
					request.setAttribute("Mensaje","Se repite el codigo del curso o el nombre del curso");
					getServletContext().getRequestDispatcher("/CatalogoCurso/mensaje.jsp").forward(request, response);
				}
				
			}else if (dato.equals("Eliminar Curso")){
				String id=request.getParameter("id");
				plan.eliminarCurso(id);
				Vector<CursoBean> object=plan.listarCurso();
				request.setAttribute("LIST_CURSO",object);
				request.setAttribute("Mensaje","Se elimino el curso correctamente");
				getServletContext().getRequestDispatcher("/CatalogoCurso/Index.jsp").forward(request, response);
			}else if (dato.equals("Modificar Curso")){
				String nombre=request.getParameter("nombre");
				String tipo=request.getParameter("tipo");
				String sumilla=request.getParameter("sumilla");
				String codigoCurso=request.getParameter("codigoCursoM");
				String codigoCursoN=request.getParameter("codigoCursoN");
				
				if(codigoCurso.equals(codigoCursoN)){
					plan.modificarCurso(codigoCursoN,codigoCurso,nombre,tipo,sumilla);
					Vector<CursoBean> object=plan.listarCurso();
					request.setAttribute("LIST_CURSO",object);
					getServletContext().getRequestDispatcher("/CatalogoCurso/Index.jsp").forward(request, response);
				}else{
					boolean v1=plan.validarCurso("",codigoCursoN);
					if(v1==true){
						System.out.print("No se repite Codigo Antiguo:: "+codigoCurso+"Codigo Nuevo::"+codigoCursoN);
						plan.modificarCurso(codigoCursoN,codigoCurso,nombre,tipo,sumilla);
						Vector<CursoBean> object=plan.listarCurso();
						request.setAttribute("LIST_CURSO",object);
						getServletContext().getRequestDispatcher("/CatalogoCurso/Index.jsp").forward(request, response);
					}else{
						System.out.print("Se repite codigo Antiguo:: "+codigoCurso+"Codigo Nuevo::"+codigoCursoN);
						request.setAttribute("Mensaje","Se repite el codigo del curso o el nombre del curso");
						getServletContext().getRequestDispatcher("/CatalogoCurso/mensaje.jsp").forward(request, response);
					}
				}
				
			}else if(dato.equals("Buscar Curso")){
				Vector<CursoBean> object1=plan.listarCurso();
				Vector<CursoBean> object2=plan.listarCursoxID(codigo);
				request.setAttribute("LIST_CURSO",object1);
				request.setAttribute("CURSO",object2);
				getServletContext().getRequestDispatcher("/CatalogoCurso/catalogo.jsp").forward(request, response);
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
}