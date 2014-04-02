package br.com.unirio.cr.data;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import br.com.unirio.cr.data.dao.EstudanteDao;
import br.com.unirio.cr.data.dao.MateriaDao;
import br.com.unirio.cr.data.dao.MateriaPeriodoDao;
import br.com.unirio.cr.data.dao.PeriodoDao;
import br.com.unirio.cr.data.model.Estudante;
import br.com.unirio.cr.data.model.Materia;
import br.com.unirio.cr.data.model.MateriaPeriodo;
import br.com.unirio.cr.data.model.Periodo;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig estudanteDaoConfig;
    private final DaoConfig periodoDaoConfig;
    private final DaoConfig materiaDaoConfig;
    private final DaoConfig materiaPeriodoDaoConfig;

    private final EstudanteDao estudanteDao;
    private final PeriodoDao periodoDao;
    private final MateriaDao materiaDao;
    private final MateriaPeriodoDao materiaPeriodoDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        estudanteDaoConfig = daoConfigMap.get(EstudanteDao.class).clone();
        estudanteDaoConfig.initIdentityScope(type);

        periodoDaoConfig = daoConfigMap.get(PeriodoDao.class).clone();
        periodoDaoConfig.initIdentityScope(type);

        materiaDaoConfig = daoConfigMap.get(MateriaDao.class).clone();
        materiaDaoConfig.initIdentityScope(type);

        materiaPeriodoDaoConfig = daoConfigMap.get(MateriaPeriodoDao.class).clone();
        materiaPeriodoDaoConfig.initIdentityScope(type);

        estudanteDao = new EstudanteDao(estudanteDaoConfig, this);
        periodoDao = new PeriodoDao(periodoDaoConfig, this);
        materiaDao = new MateriaDao(materiaDaoConfig, this);
        materiaPeriodoDao = new MateriaPeriodoDao(materiaPeriodoDaoConfig, this);

        registerDao(Estudante.class, estudanteDao);
        registerDao(Periodo.class, periodoDao);
        registerDao(Materia.class, materiaDao);
        registerDao(MateriaPeriodo.class, materiaPeriodoDao);
    }
    
    public void clear() {
        estudanteDaoConfig.getIdentityScope().clear();
        periodoDaoConfig.getIdentityScope().clear();
        materiaDaoConfig.getIdentityScope().clear();
        materiaPeriodoDaoConfig.getIdentityScope().clear();
    }

    public EstudanteDao getEstudanteDao() {
        return estudanteDao;
    }

    public PeriodoDao getPeriodoDao() {
        return periodoDao;
    }

    public MateriaDao getMateriaDao() {
        return materiaDao;
    }

    public MateriaPeriodoDao getMateriaPeriodoDao() {
        return materiaPeriodoDao;
    }

}
