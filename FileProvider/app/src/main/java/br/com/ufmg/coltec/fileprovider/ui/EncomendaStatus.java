public enum EncomendaStatus {
    AGUARDANDO_RETIRADA(0, 700, R.string.aguardando_retirada, R.color.encomendaPendenteColor),
    RETIRADA(1, 701, R.string.retirada, R.color.encomendaRetiradaColor),
    CANCELADA(2, 702, R.string.cancelada, R.color.encomendaCanceladaColor);

    private int valor;
    private int idString;
    private int idColor;
    private int codigoNotificao;

    EncomendaStatus(int valor, int codigoNotificao, int idString, int idColor) {
        this.valor = valor;
        this.codigoNotificao = codigoNotificao;
        this.idString = idString;
        this.idColor = idColor;
    }

    public static EncomendaStatus valueOf(int valor) {
        for (EncomendaStatus status : EncomendaStatus.values()) {
            if(status.getValor() == valor)
                return status;
        }
        return null;
    }
    public int getValor() {
        return valor;
    }

    public int getIdString() {
        return idString;
    }

    public int getIdColor() {
        return idColor;
    }

    public int getCodigoNotificao() {
        return codigoNotificao;
    }
}
