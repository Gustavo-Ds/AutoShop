package br.com.ssdev.autoshop.models;

public enum OrdemServicoStatus {
    NA_FILA("Na fila"),
    EM_ANDAMENTO("Em andamento"),
    FINALIZADA("Finalizada");

    private String status;

    OrdemServicoStatus(String status){this.status = status;}

    public String getStatus() {
        return status;
    }
}
