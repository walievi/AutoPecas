package br.com.alievi.autopeca.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import br.com.alievi.autopeca.dao.PecaDAO;
import br.com.alievi.autopeca.dao.PecaFornecedorDAO;
import br.com.alievi.autopeca.model.PecaFornecedor;
import java.sql.SQLException;

public class PecaFornecedorController {
    @FXML
    private ComboBox<String> comboPecas;
    @FXML
    private TableView<PecaFornecedor> tabelaPecasFornecedor;
    @FXML
    private TableColumn<PecaFornecedor, String> colunaNomePeca;
    @FXML
    private TableColumn<PecaFornecedor, Double> colunaValor;
    @FXML
    private TextField valorField;

    private int idFornecedor;
    private PecaDAO pecaDAO;
    private PecaFornecedorDAO pecaFornecedorDAO;
    private ObservableList<String> listaNomesPecas;
    private ObservableList<PecaFornecedor> listaPecasFornecedor;

    public PecaFornecedorController() {
        pecaDAO = new PecaDAO();
        pecaFornecedorDAO = new PecaFornecedorDAO();
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
        carregarComboBoxPecas();
        atualizarTabela();
    }

    @FXML
    private void initialize() {
        colunaNomePeca.setCellValueFactory(new PropertyValueFactory<>("nomePeca"));
        colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

        listaNomesPecas = FXCollections.observableArrayList();
        comboPecas.setItems(listaNomesPecas);

        listaPecasFornecedor = FXCollections.observableArrayList();
        tabelaPecasFornecedor.setItems(listaPecasFornecedor);
    }

    @FXML
    private void adicionarPecaFornecedor() {
        String nomePecaSelecionada = comboPecas.getValue();
        if (nomePecaSelecionada != null && !valorField.getText().isEmpty()) {
            try {
                int idPecaSelecionada = pecaDAO.obterIdPelaNome(nomePecaSelecionada);
                double valor = Double.parseDouble(valorField.getText());
                PecaFornecedor pecaFornecedor = new PecaFornecedor(idPecaSelecionada, idFornecedor, valor);
                pecaFornecedorDAO.adicionar(pecaFornecedor);
                atualizarTabela();
            } catch (NumberFormatException e) {
                exibirMensagemErro("Número inválido", "O valor informado não é um número válido!");
            } catch (SQLException e) {
                exibirMensagemErro("Erro inesperado", "Erro no Banco de Dados.");
            }
        }
    }

    @FXML
    private void excluirPecaFornecedor() {
        PecaFornecedor pecaFornecedorSelecionada = tabelaPecasFornecedor.getSelectionModel().getSelectedItem();
        if (pecaFornecedorSelecionada != null) {
            pecaFornecedorDAO.excluir(pecaFornecedorSelecionada.getId());
            atualizarTabela();
        }
    }

    private void carregarComboBoxPecas() {
        listaNomesPecas.clear();
        listaNomesPecas.addAll(pecaDAO.listarNomesPecas());
    }

    private void atualizarTabela() {
        listaPecasFornecedor.clear();
        listaPecasFornecedor.addAll(pecaFornecedorDAO.listarPorFornecedor(idFornecedor));
    }

    private void exibirMensagemErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
