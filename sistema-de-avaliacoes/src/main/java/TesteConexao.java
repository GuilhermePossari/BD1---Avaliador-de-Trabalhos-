import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.sistema.util.Conexao;

public class TesteConexao {
    public static void main(String[] args) {
        try {
            Connection conexao = Conexao.getConnection();
            System.out.println("Conectado com sucesso!");
            conexao.close();
        } catch (SQLException e) {
            System.err.println("Erro na conexão: " + e.getMessage());
        }
    }
}