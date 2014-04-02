package br.com.unirio.cr.data.facade;

import android.content.Context;

import java.util.List;

import br.com.unirio.cr.data.DBLoader;
import br.com.unirio.cr.data.dao.PeriodoDao;
import br.com.unirio.cr.data.model.Periodo;

public class PeriodoFacade {

    /**
     *
     * @param context
     * @param matricula
     * @param numero
     * @param nota
     * @param creditos
     * @return
     */
    public Periodo insert(Context context, long matricula, int numero, double nota, long creditos) {
        Periodo periodo = new Periodo();
        periodo.setNota(nota);
        periodo.setMatricula(matricula);
        periodo.setNumero(numero);
        periodo.setCreditosPeriodo(creditos);

        PeriodoDao dao = DBLoader.getInstance(context).daoSession.getPeriodoDao();
        Periodo p = getByMatriculaAndNumero(context, matricula, numero);
        if (p == null) {
            dao.insert(periodo);
            return periodo;
        }
        return p;
    }

    /**
     *
     * @param context
     * @param matricula
     * @param numero
     * @return
     */
    public Periodo getByMatriculaAndNumero(Context context, long matricula, int numero) {
        PeriodoDao dao = DBLoader.getInstance(context).daoSession.getPeriodoDao();
        return dao.queryBuilder().where(PeriodoDao.Properties.Matricula.eq(matricula), PeriodoDao.Properties.Numero.eq(numero)).unique();
    }

    /**
     *
     * @param context
     * @param matricula
     * @return
     */
    public List<Periodo> getByMatricula(Context context, long matricula) {
        DBLoader dbloader = DBLoader.getInstance(context);
        PeriodoDao dao = dbloader.daoSession.getPeriodoDao();
        return dao.queryBuilder().where(PeriodoDao.Properties.Matricula.eq(matricula)).list();
    }

    /**
     *
     * @param context
     * @param matricula
     * @param numero
     * @param nota
     */
    public void update(Context context, long matricula, Integer numero, Double nota, Long creditos) {
        PeriodoDao dao = DBLoader.getInstance(context).daoSession.getPeriodoDao();
        Periodo p = getByMatriculaAndNumero(context, matricula, numero);
        if (p != null) {
            p.setNota(nota);
            p.setCreditosPeriodo(creditos);
            dao.update(p);
        }
    }
}
