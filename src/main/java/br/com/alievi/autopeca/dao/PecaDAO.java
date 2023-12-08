package br.com.alievi.autopeca.dao;

import br.com.alievi.autopeca.model.Peca;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PecaDAO {

    private Connection connection;

    public PecaDAO() {
        this.connection = ConexaoBD.getConnection();
    }

    public void adicionar(Peca peca) {
        String sql = "INSERT INTO pecas (nome, preco) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, peca.getNome());
            stmt.setDouble(2, peca.getPreco());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        peca.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Peca> listar() {
        List<Peca> pecas = new ArrayList<>();
        String sql = "SELECT * FROM pecas";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Peca peca = new Peca();
                peca.setId(rs.getInt("id"));
                peca.setNome(rs.getString("nome"));
                peca.setPreco(rs.getDouble("preco"));

                pecas.add(peca);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pecas;
    }

    public void atualizar(Peca peca) {
        String sql = "UPDATE pecas SET nome = ?, preco = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, peca.getNome());
            stmt.setDouble(2, peca.getPreco());
            stmt.setInt(3, peca.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM pecas WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Peca buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM pecas WHERE id = ?";
        Peca peca = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    peca = new Peca();
                    peca.setId(rs.getInt("id"));
                    peca.setNome(rs.getString("nome"));
                    peca.setPreco(rs.getDouble("preco"));
                }
            }
        }
        return peca;
    }

    public int obterIdPelaNome(String nomePeca) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT id FROM pecas WHERE nome = ?";
            stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, nomePeca);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> listarNomesPecas() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<String> nomesPecas = new ArrayList<>();

        try {
            String sql = "SELECT nome FROM pecas";
            stmt = this.connection.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String nomePeca = rs.getString("nome");
                nomesPecas.add(nomePeca);
            }

            return nomesPecas;
        } catch (SQLException e) {
            e.printStackTrace();
            return nomesPecas;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
