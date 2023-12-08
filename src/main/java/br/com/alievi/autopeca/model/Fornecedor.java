package br.com.alievi.autopeca.model;

import javafx.beans.property.*;

public class Fornecedor {
    private final IntegerProperty id;
    private final StringProperty nome;
    private final StringProperty contato;
    private final StringProperty cnpj;

    public Fornecedor() {
        this.id = new SimpleIntegerProperty(this, "id");
        this.nome = new SimpleStringProperty(this, "nome");
        this.contato = new SimpleStringProperty(this, "contato");
        this.cnpj = new SimpleStringProperty(this, "cnpj");
    }

    public Fornecedor(String nome, String contato, String cnpj) {
        this();
        setNome(nome);
        setContato(contato);
        setCnpj(cnpj);
    }

    public Fornecedor(Integer id, String nome, String contato, String cnpj) {
        this();
        setId(id);
        setNome(nome);
        setContato(contato);
        setCnpj(cnpj);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public String getContato() {
        return contato.get();
    }

    public void setContato(String contato) {
        this.contato.set(contato);
    }

    public StringProperty contatoProperty() {
        return contato;
    }

    public String getCnpj() {
        return cnpj.get();
    }

    public void setCnpj(String cnpj) {
        this.cnpj.set(cnpj);
    }

    public StringProperty cnpjProperty() {
        return cnpj;
    }
}
