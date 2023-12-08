package br.com.alievi.autopeca.controller;

import br.com.alievi.autopeca.dao.PecaFornecedorDAO;
import br.com.alievi.autopeca.model.PecaFornecedor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Window;
import java.io.IOException;

public class MenorPrecoController {

    @FXML
    private TableView<PecaFornecedor> tabelaPecasFornecedor;
    @FXML
    private TableColumn<PecaFornecedor, String> colunaNomePeca;
    @FXML
    private TableColumn<PecaFornecedor, String> colunaNomeFornecedor;
    @FXML
    private TableColumn<PecaFornecedor, Double> colunaValorVenda;
    @FXML
    private TableColumn<PecaFornecedor, Double> colunaValorCompra;
    @FXML
    private TableColumn<PecaFornecedor, Double> colunaLucro;
    @FXML
    private void abrirGerenciarPecas() {
        carregarTela("/view/PecaView.fxml");
    }
    @FXML
    private void abrirGerenciarFornecedores() {
        carregarTela("/view/FornecedorView.fxml");
    }
    @FXML
    private void abrirSobre() {
        carregarTela("/view/SobreView.fxml");
    }

    private void carregarTela(String fxmlPath) {
        try {
            VBox root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manipule a exceção conforme necessário
        }
    }


    public void initialize() {
        colunaNomePeca.setCellValueFactory(new PropertyValueFactory<>("nomePeca"));
        colunaNomeFornecedor.setCellValueFactory(new PropertyValueFactory<>("nomeFornecedor"));
        colunaValorVenda.setCellValueFactory(new PropertyValueFactory<>("valorVenda"));
        colunaValorCompra.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colunaLucro.setCellValueFactory(new PropertyValueFactory<>("lucro"));

        carregarDados();

        // Adiciona um listener para detectar quando a janela ganha foco
        tabelaPecasFornecedor.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
                if (newValue != null) {
                    newValue.windowProperty().addListener(new ChangeListener<Window>() {
                        @Override
                        public void changed(ObservableValue<? extends Window> observable, Window oldWindow, Window newWindow) {
                            if (newWindow instanceof Stage) {
                                ((Stage) newWindow).focusedProperty().addListener(new ChangeListener<Boolean>() {
                                    @Override
                                    public void changed(ObservableValue<? extends Boolean> observable, Boolean wasFocused, Boolean isFocused) {
                                        if (isFocused) {
                                            carregarDados();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    private void carregarDados() {
        PecaFornecedorDAO dao = new PecaFornecedorDAO();
        ObservableList<PecaFornecedor> lista = FXCollections.observableArrayList();

        lista.addAll(dao.listarMenorPreco());
        tabelaPecasFornecedor.setItems(lista);
    }
}
