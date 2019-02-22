package com.example.a20161cmqads0220.jogo;

import java.util.ArrayList;

/**
 * Created by 20161cmq.ads0220 on 15/06/2018.
 */

public class Jogador {
    private int id;
    private String nome;
    private String apelido;
    private String email;
    private String senha;
    private int pontos;
    private ArrayList<criatura>Criaturas;

    public ArrayList<criatura> getCriaturas() {
        return Criaturas;
    }

    public Jogador() {
        Criaturas=new ArrayList();
    }

    public void setCriaturas(ArrayList<criatura> criaturas) {
        Criaturas = criaturas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


}