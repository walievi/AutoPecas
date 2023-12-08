package br.com.alievi.autopeca.model;

import br.com.alievi.autopeca.dao.FornecedorDAO;
import br.com.alievi.autopeca.dao.PecaDAO;

import java.sql.SQLException;

public class PecaFornecedor {
    private int id;
    private Peca peca;
    private Fornecedor fornecedor;
    private double valor;
    private PecaDAO pecaDAO;
    private FornecedorDAO fornecedorDAO;

    public PecaFornecedor(int pecaId, int fornecedorId, double valor) throws SQLException {
        pecaDAO = new PecaDAO();
        fornecedorDAO = new FornecedorDAO();

        this.peca = pecaDAO.buscarPorId(pecaId);
        this.fornecedor = fornecedorDAO.buscarPorId(fornecedorId);
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getLucro() {
        return peca.getPreco() - getValor();
    }

    public double getValorVenda() {
        return peca.getPreco();
    }

    public String getNomePeca() {
        return peca != null ? peca.getNome() : "";
    }

    public String getNomeFornecedor() {
        return fornecedor != null ? fornecedor.getNome() : "";
    }
}
