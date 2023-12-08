package br.com.alievi.autopeca.dao;

import br.com.alievi.autopeca.model.PecaFornecedor;
import br.com.alievi.autopeca.model.Peca;
import br.com.alievi.autopeca.model.Fornecedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class PecaFornecedorDAO {
    private Connection connection;

    public PecaFornecedorDAO() {
        this.connection = ConexaoBD.getConnection();
    }

    public void adicionar(PecaFornecedor pecaFornecedor) {
        String sql = "INSERT INTO pecas_fornecedores (peca_id, fornecedor_id, preco) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pecaFornecedor.getPeca().getId());
            stmt.setInt(2, pecaFornecedor.getFornecedor().getId());
            stmt.setDouble(3, pecaFornecedor.getValor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM pecas_fornecedores WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PecaFornecedor> listarPorFornecedor(int fornecedorId) {
        List<PecaFornecedor> pecasFornecedor = new ArrayList<>();
        String sql = "SELECT * FROM pecas_fornecedores WHERE fornecedor_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, fornecedorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PecaFornecedor pecaFornecedor = new PecaFornecedor(
                        rs.getInt("peca_id"),
                        rs.getInt("fornecedor_id"),
                        rs.getDouble("preco")
                );
                pecaFornecedor.setId(rs.getInt("id"));
                pecasFornecedor.add(pecaFornecedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pecasFornecedor;
    }

    public List<PecaFornecedor> listarMenorPreco() {
        List<PecaFornecedor> lista = new ArrayList<>();

        String sql = " SELECT pf.id, pf.peca_id, pf.fornecedor_id, min_preco.min_preco AS preco " +
                     "FROM pecas_fornecedores pf "+
                     "INNER JOIN (SELECT peca_id, MIN(preco) as min_preco FROM pecas_fornecedores GROUP BY peca_id) as min_preco " +
                     "ON pf.peca_id = min_preco.peca_id AND pf.preco = min_preco.min_preco";


        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                PecaFornecedor pecaFornecedor = new PecaFornecedor(rs.getInt("peca_id"), rs.getInt("fornecedor_id"), rs.getDouble("preco"));

                lista.add(pecaFornecedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
