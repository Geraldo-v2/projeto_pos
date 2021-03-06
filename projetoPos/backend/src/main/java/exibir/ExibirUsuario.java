package exibir;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.UsuarioDao;
import model.Usuario;

import java.util.ArrayList;
import java.util.List;


public class ExibirUsuario extends HttpServlet{
  
  @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        final PrintWriter saida = resp.getWriter();
        final UsuarioDao dao = new UsuarioDao();
        final Gson gson = new Gson();
        String usuarioJsonStr = "";

        List<Usuario> lstUser = new ArrayList<>();

        try {
            int id =Integer.parseInt(req.getParameter("id"));
            lstUser = dao.pesquisar(id);
        } catch (SQLException e) {            
            e.printStackTrace();
        }
        
        for (Usuario user : lstUser) 
        {
           usuarioJsonStr += gson.toJson(user);
        }

        saida.println(usuarioJsonStr);

        saida.flush();
        saida.close();
    }
    
    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp)
     throws ServletException, IOException {
         Usuario user = new Usuario();

        user.setCpf(Integer.parseInt(req.getParameter("cpf")));
        user.setNome(req.getParameter("nome"));
        user.setEmail(req.getParameter("email"));
        user.setSenha(req.getParameter("senha"));
        user.setNascimento(req.getParameter("nascimento"));
        user.setSexo(req.getParameter("sexo"));
       
         UsuarioDao ud = new UsuarioDao();

         try{
             ud.cadastrar(user);
             System.out.println(" sucesso!");
         }catch (SQLException e){
             e.printStackTrace();
         }
     }
    
}