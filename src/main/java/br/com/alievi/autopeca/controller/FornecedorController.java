package br.com.alievi.autopeca.controller;

import br.com.alievi.autopeca.dao.FornecedorDAO;
import br.com.alievi.autopeca.model.Fornecedor;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class FornecedorController {
    private Integer idFornecedorSelecionado = null;

    @FXML
    private TextField nomeField;
    @FXML
    private TextField contatoField;
    @FXML
    private TextField cnpjField;
    @FXML
    private TableView<Fornecedor> tabelaFornecedores;
    @FXML
    private TableColumn<Fornecedor, Integer> colunaId;
    @FXML
    private TableColumn<Fornecedor, String> colunaNome;
    @FXML
    private TableColumn<Fornecedor, String> colunaContato;
    @FXML
    private TableColumn<Fornecedor, String> colunaCnpj;
    @FXML
    private FornecedorDAO fornecedorDAO;

    public FornecedorController() {
        fornecedorDAO = new FornecedorDAO();
    }

    @FXML
    private void initialize() {
        colunaId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colunaNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        colunaContato.setCellValueFactory(cellData -> cellData.getValue().contatoProperty());
        colunaCnpj.setCellValueFactory(cellData -> cellData.getValue().cnpjProperty());

        atualizarTabela();

        tabelaFornecedores.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Fornecedor>() {
            @Override
            public void changed(ObservableValue<? extends Fornecedor> observable, Fornecedor oldValue, Fornecedor newValue) {
                if (newValue != null) {
                    idFornecedorSelecionado = newValue.getId();
                    nomeField.setText(newValue.getNome());
                    contatoField.setText(newValue.getContato());
                    cnpjField.setText(newValue.getCnpj());
                }
            }
        });
    }


    @FXML
    private void handleSalvar() {
        String nome = nomeField.getText();
        String contato = contatoField.getText();
        String cnpj = cnpjField.getText();

        if (idFornecedorSelecionado == null) {
            // Adiciona um novo fornecedor
            Fornecedor fornecedor = new Fornecedor(nome, contato, cnpj);
            fornecedorDAO.adicionar(fornecedor);
        } else {
            // Atualiza o fornecedor existente
            Fornecedor fornecedor = new Fornecedor(idFornecedorSelecionado, nome, contato, cnpj);
            fornecedorDAO.atualizar(fornecedor);
            idFornecedorSelecionado = null; // Reseta o ID selecionado após a atualização
        }

        limparCampos();
        atualizarTabela();
    }

    @FXML
    private void handleExcluir() {
        Fornecedor fornecedorSelecionado = tabelaFornecedores.getSelectionModel().getSelectedItem();
        if (fornecedorSelecionado != null) {
            fornecedorDAO.excluir(fornecedorSelecionado.getId());
            atualizarTabela();
        } else {
            exibirMensagemErro("Nenhuma Seleção", "Por favor, selecione um fornecedor na tabela.");
        }
    }

    @FXML
    private void handleListaPecas() {
        Fornecedor fornecedorSelecionado = tabelaFornecedores.getSelectionModel().getSelectedItem();
        if (fornecedorSelecionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PecaFornecedorView.fxml"));
                AnchorPane root = loader.load();

                PecaFornecedorController controller = loader.getController();

                controller.setIdFornecedor(fornecedorSelecionado.getId());

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                exibirMensagemErro("Erro", "Não é possivel acessar a lista antes de salvar o fornecedor");
            }
        } else {
            exibirMensagemErro("Nenhuma Seleção", "Por favor, selecione um fornecedor na tabela.");
        }
    }



    private void atualizarTabela() {
        tabelaFornecedores.getItems().setAll(fornecedorDAO.listar());
    }

    private void limparCampos() {
        nomeField.clear();
        contatoField.clear();
        cnpjField.clear();
        idFornecedorSelecionado = null; // Reseta o ID selecionado
    }

    private void exibirMensagemErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
