package br.com.unirio.cr.data.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import br.com.unirio.cr.data.DaoSession;
import br.com.unirio.cr.data.model.Materia;
import br.com.unirio.cr.data.model.MateriaPeriodo;
import br.com.unirio.cr.data.model.Periodo;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.internal.SqlUtils;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table MATERIA_PERIODO.
*/
public class MateriaPeriodoDao extends AbstractDao<MateriaPeriodo, Void> {

    public static final String TABLENAME = "MATERIA_PERIODO";

    /**
     * Properties of entity MateriaPeriodo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property SubjectId = new Property(0, Long.class, "subjectId", false, "SUBJECT_ID");
        public final static Property PeriodId = new Property(1, Long.class, "periodId", false, "PERIOD_ID");
        public final static Property Nota = new Property(2, Double.class, "nota", false, "NOTA");
    };

    private DaoSession daoSession;


    public MateriaPeriodoDao(DaoConfig config) {
        super(config);
    }
    
    public MateriaPeriodoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'MATERIA_PERIODO' (" + //
                "'SUBJECT_ID' INTEGER," + // 0: subjectId
                "'PERIOD_ID' INTEGER," + // 1: periodId
                "'NOTA' DOUBLE);"); // 2: nota
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'MATERIA_PERIODO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, MateriaPeriodo entity) {
        stmt.clearBindings();
 
        Long subjectId = entity.getSubjectId();
        if (subjectId != null) {
            stmt.bindLong(1, subjectId);
        }
 
        Long periodId = entity.getPeriodId();
        if (periodId != null) {
            stmt.bindLong(2, periodId);
        }

        Double nota = entity.getNota();
        if (nota != null) {
            stmt.bindDouble(3, nota);
        }
    }

    @Override
    protected void attachEntity(MateriaPeriodo entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public MateriaPeriodo readEntity(Cursor cursor, int offset) {
        MateriaPeriodo entity = new MateriaPeriodo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // subjectId
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // periodId
            cursor.isNull(offset + 2) ? null : cursor.getDouble(offset + 2) // nota
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, MateriaPeriodo entity, int offset) {
        entity.setSubjectId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPeriodId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setNota(cursor.isNull(offset + 2) ? null : cursor.getDouble(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(MateriaPeriodo entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(MateriaPeriodo entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getMateriaDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getPeriodoDao().getAllColumns());
            builder.append(" FROM MATERIA_PERIODO T");
            builder.append(" LEFT JOIN MATERIA T0 ON T.'SUBJECT_ID'=T0.'_id'");
            builder.append(" LEFT JOIN PERIODO T1 ON T.'PERIOD_ID'=T1.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected MateriaPeriodo loadCurrentDeep(Cursor cursor, boolean lock) {
        MateriaPeriodo entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Materia materia = loadCurrentOther(daoSession.getMateriaDao(), cursor, offset);
        entity.setMateria(materia);
        offset += daoSession.getMateriaDao().getAllColumns().length;

        Periodo periodo = loadCurrentOther(daoSession.getPeriodoDao(), cursor, offset);
        entity.setPeriodo(periodo);

        return entity;    
    }

    public MateriaPeriodo loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<MateriaPeriodo> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<MateriaPeriodo> list = new ArrayList<MateriaPeriodo>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<MateriaPeriodo> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<MateriaPeriodo> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
