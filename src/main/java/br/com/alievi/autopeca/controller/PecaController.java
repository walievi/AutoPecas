package br.com.alievi.autopeca.controller;

import br.com.alievi.autopeca.model.Peca;
import br.com.alievi.autopeca.dao.PecaDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class PecaController {
    private Integer idPecaSelecionada = null;

    @FXML
    private TextField nomeField;
    @FXML
    private TextField precoField;
    @FXML
    private TableView<Peca> tabelaPecas;
    @FXML
    private TableColumn<Peca, Integer> colunaId;
    @FXML
    private TableColumn<Peca, String> colunaNome;
    @FXML
    private TableColumn<Peca, Double> colunaPreco;
    private PecaDAO pecaDAO;

    public PecaController() {
        pecaDAO = new PecaDAO();
    }

    @FXML
    private void initialize() {
        colunaId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colunaNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        colunaPreco.setCellValueFactory(cellData -> cellData.getValue().precoProperty().asObject());

        atualizarTabela();

        tabelaPecas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Peca>() {
            @Override
            public void changed(ObservableValue<? extends Peca> observable, Peca oldValue, Peca newValue) {
                if (newValue != null) {
                    idPecaSelecionada = newValue.getId();
                    nomeField.setText(newValue.getNome());
                    precoField.setText(String.valueOf(newValue.getPreco()));
                }
            }
        });
    }

    @FXML
    private void handleSalvar() {
        try {
            String nome = nomeField.getText();
            double preco = Double.parseDouble(precoField.getText());

            if (idPecaSelecionada == null) {
                // Adiciona uma nova peça
                Peca peca = new Peca(nome, preco);
                pecaDAO.adicionar(peca);
            } else {
                // Atualiza a peça existente
                Peca peca = new Peca(idPecaSelecionada, nome, preco);
                pecaDAO.atualizar(peca);
                idPecaSelecionada = null; // Reseta o ID selecionado após a atualização
            }

            limparCampos();
            atualizarTabela();
        } catch (NumberFormatException e) {
            exibirMensagemErro("Erro de Formato", "Por favor, insira valores numéricos válidos para preço e quantidade.");
        }
    }


    @FXML
    private void handleExcluir() {
        Peca pecaSelecionada = tabelaPecas.getSelectionModel().getSelectedItem();
        if (pecaSelecionada != null) {
            pecaDAO.excluir(pecaSelecionada.getId());
            atualizarTabela();
        } else {
            exibirMensagemErro("Nenhuma Seleção", "Por favor, selecione uma peça na tabela.");
        }
    }

    private void atualizarTabela() {
        tabelaPecas.getItems().setAll(pecaDAO.listar());
    }

    private void limparCampos() {
        nomeField.clear();
        precoField.clear();
        idPecaSelecionada = null;
    }

    private void exibirMensagemErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}