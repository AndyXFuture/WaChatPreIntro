package com.example.perintro.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.perintro.bean.PersonInfo;

import java.util.ArrayList;

@SuppressLint("DefaultLocale")
public class PersonDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "PersonDBHelper";
    private static final String DB_NAME = "person.db"; // 数据库的名称
    private static final int DB_VERSION = 1; // 数据库的版本号
    private static PersonDBHelper mHelper = null; // 数据库帮助器的实例
    private SQLiteDatabase mDB = null; // 数据库的实例
    public static final String TABLE_NAME = "person_info"; // 表的名称

    private PersonDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private PersonDBHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static PersonDBHelper getInstance(Context context, int version) {
        if (version > 0 && mHelper == null) {
            mHelper = new PersonDBHelper(context, version);
        } else if (mHelper == null) {
            mHelper = new PersonDBHelper(context);
        }
        return mHelper;
    }

    // 打开数据库的读连接
    public SQLiteDatabase openReadLink() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = mHelper.getReadableDatabase();
        }
        return mDB;
    }

    // 打开数据库的写连接
    public SQLiteDatabase openWriteLink() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = mHelper.getWritableDatabase();
        }
        return mDB;
    }

    // 关闭数据库连接
    public void closeLink() {
        if (mDB != null && mDB.isOpen()) {
            mDB.close();
            mDB = null;
        }
    }

    // 创建数据库，执行建表语句
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        String drop_sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        Log.d(TAG, "drop_sql:" + drop_sql);
        db.execSQL(drop_sql);
        String create_sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + "_id INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL,"
                + "name VARCHAR NOT NULL," + "bir VARCHAR NOT NULL,"
                + "phone VARCHAR NOT NULL," + "education VARCHAR NOT NULL,"
                + "graduate VARCHAR NOT NULL," + "major VARCHAR NOT NULL,"
                + "target VARCHAR NOT NULL," + "skill VARCHAR,"
                + "hobby VARCHAR," + "update_time VARCHAR NOT NULL"
                //演示数据库升级时要先把下面这行注释
                + ");";
        Log.d(TAG, "create_sql:" + create_sql);
        db.execSQL(create_sql);
    }

    // 修改数据库，执行表结构变更语句
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade oldVersion=" + oldVersion + ", newVersion=" + newVersion);
        if (newVersion > 1) {
            //Android的ALTER命令不支持一次添加多列，只能分多次添加
            String alter_sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + "phone VARCHAR;";
            Log.d(TAG, "alter_sql:" + alter_sql);
            db.execSQL(alter_sql);
            alter_sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + "password VARCHAR;";
            Log.d(TAG, "alter_sql:" + alter_sql);
            db.execSQL(alter_sql);
        }
    }

    // 根据指定条件删除表记录
    public int delete(String condition) {
        // 执行删除记录动作，该语句返回删除记录的数目
        return mDB.delete(TABLE_NAME, "name=?", new String[]{condition});
    }

    // 删除该表的所有记录
    public int deleteAll() {
        // 执行删除记录动作，该语句返回删除记录的数目
        return mDB.delete(TABLE_NAME, "1=1", null);
    }

    // 往该表添加一条记录
    public long insert(PersonInfo info) {
        ArrayList<PersonInfo> infoArray = new ArrayList<PersonInfo>();
        infoArray.add(info);
        return insert(infoArray);
    }

    // 往该表添加多条记录
    public long insert(ArrayList<PersonInfo> infoArray) {
        long result = -1;
        for (int i = 0; i < infoArray.size(); i++) {
            PersonInfo info = infoArray.get(i);
            ArrayList<PersonInfo> tempArray = new ArrayList<PersonInfo>();
            // 如果存在同名记录，则更新记录
            // 注意条件语句的等号后面要用单引号括起来
            if (info.name != null && info.name.length() > 0) {
                String condition = String.format("name='%s'", info.name);
                tempArray = query(condition);
                if (tempArray.size() > 0) {
                    update(info, condition);
                    result = tempArray.get(0).rowid;
                    continue;
                }
            }
            /*/ 如果存在同样的手机号码，则更新记录
            if (info.phone != null && info.phone.length() > 0) {
                String condition = String.format("phone='%s'", info.phone);
                tempArray = query(condition);
                if (tempArray.size() > 0) {
                    update(info, condition);
                    result = tempArray.get(0).rowid;
                    continue;
                }
            }*/
            // 不存在唯一性重复的记录，则插入新记录
            ContentValues cv = new ContentValues();
            cv.put("name", info.name);
            cv.put("bir", info.birthday_YM);
            cv.put("phone", info.telephoneNumber);
            cv.put("education", info.education);
            cv.put("graduate", info.graduate_University);
            cv.put("major", info.major);
            cv.put("target", info.target_Work);
            cv.put("skill", info.skill);
            cv.put("hobby", info.hobby);
            cv.put("update_time", info.update_time);

            // 执行插入记录动作，该语句返回插入记录的行号
            result = mDB.insert(TABLE_NAME, "", cv);
            // 添加成功后返回行号，失败后返回-1
            if (result == -1) {
                return result;
            }
        }
        return result;
    }

    // 根据条件更新指定的表记录
    public int update(PersonInfo info, String condition) {
        ContentValues cv = new ContentValues();
        cv.put("name", info.name);
        cv.put("bir", info.birthday_YM);
        cv.put("phone", info.telephoneNumber);
        cv.put("education", info.education);
        cv.put("graduate", info.graduate_University);
        cv.put("major", info.major);
        cv.put("target", info.target_Work);
        cv.put("skill", info.skill);
        cv.put("hobby", info.hobby);
        cv.put("update_time", info.update_time);
        // 执行更新记录动作，该语句返回记录更新的数目
        return mDB.update(TABLE_NAME, cv, condition, null);
    }

    public int update(PersonInfo info) {
        // 执行更新记录动作，该语句返回记录更新的数目
        return update(info, "rowid=" + info.rowid);
    }

    // 根据指定条件查询记录，并返回结果数据队列
    public ArrayList<PersonInfo> query(String condition) {
        String sql = String.format("select rowid,_id,name,bir,phone,education,graduate,major,target," +
                "skill,hobby,update_time from %s where %s;", TABLE_NAME, condition);
        Log.d(TAG, "query sql: " + sql);
        ArrayList<PersonInfo> infoArray = new ArrayList<PersonInfo>();
        // 执行记录查询动作，该语句返回结果集的游标
        Cursor cursor = mDB.rawQuery(sql, null);
        // 循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            PersonInfo info = new PersonInfo();
            info.rowid = cursor.getLong(0); // 取出长整型数
            info.xuhao = cursor.getInt(1); // 取出整型数
            info.name = cursor.getString(2); // 取出字符串
            info.birthday_YM = cursor.getString(3);
            info.telephoneNumber = cursor.getString(4);
            info.education = cursor.getString(5);
            info.graduate_University = cursor.getString(6);
            info.major = cursor.getString(7);
            info.target_Work = cursor.getString(8);
            info.skill = cursor.getString(9);
            info.hobby = cursor.getString(10);
            info.update_time = cursor.getString(11);
            //SQLite没有布尔型，用0表示false，用1表示true
            //info.married = (cursor.getInt(6) == 0) ? false : true;

            infoArray.add(info);
        }
        cursor.close(); // 查询完毕，关闭游标
        return infoArray;
    }

    // 根据手机号码查询指定记录
    public PersonInfo queryByName(String Name) {
        PersonInfo info = null;
        ArrayList<PersonInfo> infoArray = query(String.format("name='%s'", Name));
        if (infoArray.size() > 0) {
            info = infoArray.get(0);
        }
        return info;
    }

}
