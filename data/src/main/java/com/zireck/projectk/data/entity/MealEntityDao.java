package com.zireck.projectk.data.entity;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;

import com.zireck.projectk.data.entity.MealEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table MEAL_ENTITY.
*/
public class MealEntityDao extends AbstractDao<MealEntity, Long> {

    public static final String TABLENAME = "MEAL_ENTITY";

    /**
     * Properties of entity MealEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Date = new Property(1, java.util.Date.class, "date", false, "DATE");
        public final static Property Mealtime = new Property(2, int.class, "mealtime", false, "MEALTIME");
        public final static Property Grams = new Property(3, int.class, "grams", false, "GRAMS");
        public final static Property Calories = new Property(4, double.class, "calories", false, "CALORIES");
        public final static Property Fats = new Property(5, double.class, "fats", false, "FATS");
        public final static Property Carbohydrates = new Property(6, double.class, "carbohydrates", false, "CARBOHYDRATES");
        public final static Property Proteins = new Property(7, double.class, "proteins", false, "PROTEINS");
        public final static Property FoodName = new Property(8, String.class, "foodName", false, "FOOD_NAME");
        public final static Property FoodId = new Property(9, Long.class, "foodId", false, "FOOD_ID");
    };

    private DaoSession daoSession;


    public MealEntityDao(DaoConfig config) {
        super(config);
    }
    
    public MealEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'MEAL_ENTITY' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'DATE' INTEGER NOT NULL ," + // 1: date
                "'MEALTIME' INTEGER NOT NULL ," + // 2: mealtime
                "'GRAMS' INTEGER NOT NULL ," + // 3: grams
                "'CALORIES' REAL NOT NULL ," + // 4: calories
                "'FATS' REAL NOT NULL ," + // 5: fats
                "'CARBOHYDRATES' REAL NOT NULL ," + // 6: carbohydrates
                "'PROTEINS' REAL NOT NULL ," + // 7: proteins
                "'FOOD_NAME' TEXT," + // 8: foodName
                "'FOOD_ID' INTEGER);"); // 9: foodId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'MEAL_ENTITY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, MealEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getDate().getTime());
        stmt.bindLong(3, entity.getMealtime());
        stmt.bindLong(4, entity.getGrams());
        stmt.bindDouble(5, entity.getCalories());
        stmt.bindDouble(6, entity.getFats());
        stmt.bindDouble(7, entity.getCarbohydrates());
        stmt.bindDouble(8, entity.getProteins());
 
        String foodName = entity.getFoodName();
        if (foodName != null) {
            stmt.bindString(9, foodName);
        }
 
        Long foodId = entity.getFoodId();
        if (foodId != null) {
            stmt.bindLong(10, foodId);
        }
    }

    @Override
    protected void attachEntity(MealEntity entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public MealEntity readEntity(Cursor cursor, int offset) {
        MealEntity entity = new MealEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            new java.util.Date(cursor.getLong(offset + 1)), // date
            cursor.getInt(offset + 2), // mealtime
            cursor.getInt(offset + 3), // grams
            cursor.getDouble(offset + 4), // calories
            cursor.getDouble(offset + 5), // fats
            cursor.getDouble(offset + 6), // carbohydrates
            cursor.getDouble(offset + 7), // proteins
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // foodName
            cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9) // foodId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, MealEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDate(new java.util.Date(cursor.getLong(offset + 1)));
        entity.setMealtime(cursor.getInt(offset + 2));
        entity.setGrams(cursor.getInt(offset + 3));
        entity.setCalories(cursor.getDouble(offset + 4));
        entity.setFats(cursor.getDouble(offset + 5));
        entity.setCarbohydrates(cursor.getDouble(offset + 6));
        entity.setProteins(cursor.getDouble(offset + 7));
        entity.setFoodName(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setFoodId(cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(MealEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(MealEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
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
            SqlUtils.appendColumns(builder, "T0", daoSession.getFoodEntityDao().getAllColumns());
            builder.append(" FROM MEAL_ENTITY T");
            builder.append(" LEFT JOIN FOOD_ENTITY T0 ON T.'FOOD_ID'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected MealEntity loadCurrentDeep(Cursor cursor, boolean lock) {
        MealEntity entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        FoodEntity foodEntity = loadCurrentOther(daoSession.getFoodEntityDao(), cursor, offset);
        entity.setFoodEntity(foodEntity);

        return entity;    
    }

    public MealEntity loadDeep(Long key) {
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
    public List<MealEntity> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<MealEntity> list = new ArrayList<MealEntity>(count);
        
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
    
    protected List<MealEntity> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<MealEntity> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
