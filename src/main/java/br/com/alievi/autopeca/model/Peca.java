package br.com.alievi.autopeca.model;

import javafx.beans.property.*;

public class Peca {
    private final IntegerProperty id;
    private final StringProperty nome;
    private final DoubleProperty preco;

    public Peca() {
        this.id = new SimpleIntegerProperty(this, "id");
        this.nome = new SimpleStringProperty(this, "nome");
        this.preco = new SimpleDoubleProperty(this, "preco");
    }

    public Peca(String nome, double preco) {
        this();
        setNome(nome);
        setPreco(preco);
    }

    public Peca(Integer id, String nome, double preco) {
        this();
        setId(id);
        setNome(nome);
        setPreco(preco);
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

    public double getPreco() {
        return preco.get();
    }

    public void setPreco(double preco) {
        this.preco.set(preco);
    }

    public DoubleProperty precoProperty() {
        return preco;
    }
}
