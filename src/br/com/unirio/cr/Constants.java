package br.com.unirio.cr;

public class Constants {

    public static final String MATRICULA_KEY = "matricula";
    public static final String PERIODO_KEY = "periodo";
    public static final String CR_KEY = "cr";
    public static final String CREDITOS_KEY = "creditos";

    public static final Integer PERIODO_UM = 1;

    public static final String TEXT_TYPE = " TEXT";
    public static final String INT_TYPE = " INTEGER";
    public static final String DOUBLE_TYPE = " DOUBLE";
    public static final String COMMA_SEP = ",";
    public static final String IF_NOT_EXISTS = " IF NOT EXISTS";

    public static final String MATRICULA_MASK = "Matricula: %d";
    public static final String PERIODO_CR_MASK = "%dº Periodo / CR: %.2f";
    public static final String MATERIA_MASK = "%s - %d creditos - Nota: %.2f";

    public static final String DB_NAME = "dbtarc";

    public static final String BROADCAST_UPDATE_CR = "br.com.unirio.cr.update";
    public static final String BROADCAST_UPDATE_CRA = "br.com.unirio.cra.update";

}
