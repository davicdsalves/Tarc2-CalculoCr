package br.com.unirio.cr.data.facade;

import android.content.Context;

import java.util.List;

import br.com.unirio.cr.data.DBLoader;
import br.com.unirio.cr.data.dao.MateriaPeriodoDao;
import br.com.unirio.cr.data.model.MateriaPeriodo;

public class MateriaPeriodoFacade {

    public MateriaPeriodoFacade() {
    }

    /**
     *
     * @param context
     * @param subjectId
     * @param periodId
     * @return
     */
    public MateriaPeriodo insert(Context context, Long subjectId, Long periodId, Double nota) {
        MateriaPeriodoDao dao = DBLoader.getInstance(context).daoSession.getMateriaPeriodoDao();
        MateriaPeriodo materiaPeriodo = new MateriaPeriodo();
        materiaPeriodo.setSubjectId(subjectId);
        materiaPeriodo.setPeriodId(periodId);
        materiaPeriodo.setNota(nota);

        MateriaPeriodo mp = get(context, subjectId, periodId);
        if (mp == null) {
            dao.insert(materiaPeriodo);
            return materiaPeriodo;
        }
        return mp;
    }

    /**
     *
     * @param subjectId
     * @param periodId
     * @return
     */
    public MateriaPeriodo get(Context context, Long subjectId, Long periodId) {
        MateriaPeriodoDao dao = DBLoader.getInstance(context).daoSession.getMateriaPeriodoDao();
        return dao.queryBuilder().where(MateriaPeriodoDao.Properties.SubjectId.eq(subjectId), MateriaPeriodoDao.Properties.PeriodId.eq(periodId)).unique();
    }

    /**
     *
     * @param context
     * @param periodId
     * @return
     */
    public List<MateriaPeriodo> get(Context context, Long periodId) {
        MateriaPeriodoDao dao = DBLoader.getInstance(context).daoSession.getMateriaPeriodoDao();
        return dao.queryBuilder().where(MateriaPeriodoDao.Properties.PeriodId.eq(periodId)).list();
    }

    /**
     *
     * @param context
     * @param periodId
     * @return
     */
    public List<MateriaPeriodo> getByPeriodId(Context context, Long periodId) {
        MateriaPeriodoDao dao = DBLoader.getInstance(context).daoSession.getMateriaPeriodoDao();
        return dao.queryBuilder().where(MateriaPeriodoDao.Properties.PeriodId.eq(periodId)).list();
    }
}
