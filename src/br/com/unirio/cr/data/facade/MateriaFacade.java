package br.com.unirio.cr.data.facade;

import android.content.Context;

import java.util.List;

import br.com.unirio.cr.data.DBLoader;
import br.com.unirio.cr.data.dao.MateriaDao;
import br.com.unirio.cr.data.model.Materia;

public class MateriaFacade {

    private Long id;
    private Long creditos;
    private String nome;

    public MateriaFacade() {
    }

    /**
     *
     * @param context
     * @param creditos
     * @param nome
     * @return
     */
    public Materia insert(Context context, Long creditos, String nome) {
        MateriaDao dao = DBLoader.getInstance(context).daoSession.getMateriaDao();
        Materia materia = new Materia();
        materia.setCreditos(creditos);
        materia.setNome(nome);
        Materia m = getByName(context, nome.toUpperCase());
        if (m == null) {
            dao.insert(materia);
            return materia;
        }
        return m;
    }

    /**
     *
     * @param context
     * @param nome
     * @return
     */
    public Materia getByName(Context context, String nome) {
        MateriaDao dao = DBLoader.getInstance(context).daoSession.getMateriaDao();
        return dao.queryBuilder().where(MateriaDao.Properties.Nome.eq(nome)).unique();
    }

    /**
     *
     * @param context
     * @param nome
     * @return
     */
    public List<Materia> getNameLike(Context context, String nome) {
        MateriaDao dao = DBLoader.getInstance(context).daoSession.getMateriaDao();
        return dao.queryBuilder().where(MateriaDao.Properties.Nome.like('%' + nome + '%')).list();

    }

}
