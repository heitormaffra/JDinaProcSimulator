/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.util;

/**
 *
 * @author heitormaffra
 */
public class MetaModel {

    public MetaModel(String nome, Double artefato) {
        this.nome = nome;
        this.artefato = artefato;
    }
        
    private String nome;
    private Double artefato; 

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getArtefato() {
        return artefato;
    }

    public void setArtefato(Double artefato) {
        this.artefato = artefato;
    }
    
}
