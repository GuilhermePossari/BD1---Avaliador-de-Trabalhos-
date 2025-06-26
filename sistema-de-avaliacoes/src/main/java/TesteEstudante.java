import com.sistema.util.Conexao;
import com.sistema.dao.EstudanteDAO;
import com.sistema.dao.UsuarioDAO;
import com.sistema.model.Estudante;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;




public class TesteEstudante {
    public static void main(String[] args) {
        try (Connection conn = Conexao.getConnection()) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
            EstudanteDAO estudanteDAO = new EstudanteDAO(conn);

            Estudante e = new Estudante(0, "João", "Silva", "joao@email.com", "senha123");

            usuarioDAO.inserir(e); // salva e retorna id (ajuste para retornar o id)
            e.setId(e.getId());    // recuperar id se for gerado automaticamente

            estudanteDAO.inserir(e);

            System.out.println("Estudante inserido com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
