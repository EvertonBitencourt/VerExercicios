package com.prova.everton.verexercicios;

/**
 * Created by Everton on 19/11/2017.
 */

public class ExercicioResolvido {

    private int idExercicio;
    private String pergunta;
    private String resposta;

    public ExercicioResolvido() {
    }

    public ExercicioResolvido(int idExercicio, String pergunta, String resposta) {
        this.idExercicio = idExercicio;
        this.pergunta = pergunta;
        this.resposta = resposta;
    }

    public int getIdExercicio() {
        return idExercicio;
    }

    public void setIdExercicio(int idExercicio) {
        this.idExercicio = idExercicio;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    @Override
    public String toString() {
        return "ExercicioResolvido{" +
                "idExercicio=" + idExercicio +
                ", pergunta='" + pergunta + '\'' +
                ", resposta='" + resposta + '\'' +
                '}';
    }
}
