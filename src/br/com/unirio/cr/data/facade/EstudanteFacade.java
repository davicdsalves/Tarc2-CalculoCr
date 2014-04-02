package br.com.unirio.cr.data.facade;

import android.content.Context;
import android.widget.Toast;

import br.com.unirio.cr.data.DBLoader;
import br.com.unirio.cr.data.dao.EstudanteDao;
import br.com.unirio.cr.data.model.Estudante;

public class EstudanteFacade {

    /**
     *
     * @param context
     * @param matricula
     * @return
     */
    public Estudante insert(Context context, long matricula) {
        EstudanteDao dao = DBLoader.getInstance(context).daoSession.getEstudanteDao();
        Estudante estudante = new Estudante();
        estudante.setMatricula(matricula);
        estudante.setCargaAcumulada(0.0);
        estudante.setCargaCumprida(0L);
        estudante.setCargaDoCurso(0L);
        Estudante e = getByMatricula(context, matricula);
        if (e == null) {
            dao.insert(estudante);
            return estudante;
        }
        return e;
    }

    /**
     *
     * @param context
     * @param matricula
     * @return
     */
    public Estudante getByMatricula(Context context, long matricula) {
        EstudanteDao dao = DBLoader.getInstance(context).daoSession.getEstudanteDao();
        return dao.queryBuilder().where(EstudanteDao.Properties.Matricula.eq(matricula)).unique();
    }

    /**
     *
     * @param context
     * @param matricula
     * @param crAcumulada
     * @param crCumprida
     * @param crCurso
     */
    public void update(Context context, long matricula, double crAcumulada, long crCumprida, long crCurso) {
        EstudanteDao dao = DBLoader.getInstance(context).daoSession.getEstudanteDao();
        Estudante e = getByMatricula(context, matricula);
        if (e != null) {
            e.setCargaCumprida(crCumprida);
            e.setCargaAcumulada(crAcumulada);
            e.setCargaDoCurso(crCurso);
            dao.update(e);
        }
    }
}
