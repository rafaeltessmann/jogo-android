package com.example.a20161cmqads0220.jogo;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by 20161cmq.ads0220 on 22/06/2018.
 */

public class criatura implements Serializable{

    private int id;
    private String nomeCriatura;
    private String ataque;
    private String defesa;
    private String velocidade;
    private String vida;
    private int pontos;
    private int imagemCriatura;

    public String getNomeCriatura() {
        return nomeCriatura;
    }

    public void setNomeCriatura(String nomeCriatura) {
        this.nomeCriatura = nomeCriatura;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public String getAtaque() {

        return ataque;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAtaque(String ataque) {
        this.ataque = ataque;
    }

    public String getDefesa() {
        return defesa;
    }

    public void setDefesa(String defesa) {
        this.defesa = defesa;
    }

    public String getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(String velocidade) {
        this.velocidade = velocidade;
    }

    public String getVida() {
        return vida;
    }

    public void setVida(String vida) {
        this.vida = vida;
    }

    public int getImagemCriatura() {
        return imagemCriatura;
    }

    public void setImagemCriatura(int imagemCriatura) {
        this.imagemCriatura = imagemCriatura;
    }
}