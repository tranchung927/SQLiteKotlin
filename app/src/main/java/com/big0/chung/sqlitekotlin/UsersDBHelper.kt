package com.big0.chung.sqlitekotlin

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UsersDBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "FeedReader.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DBContract.UserEntity.TABLE_NAME + " (" +
                        DBContract.UserEntity.COLUMN_USER_ID + " TEXT PRIMARY KEY," +
                        DBContract.UserEntity.COLUMN_NAME + " TEXT," +
                        DBContract.UserEntity.COLUMN_AGE + " TEXT)"
        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.UserEntity.TABLE_NAME
    }

    val db = writableDatabase
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertUser(user: UserModel): Boolean {
        val values = ContentValues()
        values.put(DBContract.UserEntity.COLUMN_USER_ID, user.userid)
        values.put(DBContract.UserEntity.COLUMN_AGE, user.age)
        values.put(DBContract.UserEntity.COLUMN_NAME, user.name)
        val newRowId = db.insert(DBContract.UserEntity.TABLE_NAME, null, values)
        return newRowId > 0
    }
    @Throws(SQLiteConstraintException::class)
    fun deleteUser(userid: String): Boolean {
        val selection = DBContract.UserEntity.COLUMN_USER_ID + " LIKE "
        val selectionArgs = arrayOf(userid)
        db.delete(DBContract.UserEntity.TABLE_NAME, selection, selectionArgs)
        return true
    }

    fun readUser(userid: String): UserModel? {
        var user: UserModel? = null
        val query = "SELECT * FROM " + DBContract.UserEntity.TABLE_NAME + " WHERE " + DBContract.UserEntity.COLUMN_USER_ID + " LIKE " + userid
        try {
            var cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val userid: String = cursor.getString(cursor.getColumnIndex(DBContract.UserEntity.COLUMN_USER_ID))
                    val name: String = cursor.getString(cursor.getColumnIndex(DBContract.UserEntity.COLUMN_NAME))
                    val age: String = cursor.getString(cursor.getColumnIndex(DBContract.UserEntity.COLUMN_AGE))
                    user = UserModel(userid, name, age)
                    cursor.moveToNext()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return user
    }

    fun readAllUsers(): ArrayList<UserModel> {
        var users = ArrayList<UserModel>()

        val query = "SELECT * FROM " + DBContract.UserEntity.TABLE_NAME
        try {
            var cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val userid: String = cursor.getString(cursor.getColumnIndex(DBContract.UserEntity.COLUMN_USER_ID))
                    val name: String = cursor.getString(cursor.getColumnIndex(DBContract.UserEntity.COLUMN_NAME))
                    val age: String = cursor.getString(cursor.getColumnIndex(DBContract.UserEntity.COLUMN_AGE))
                    users.add(UserModel(userid, name, age))
                    cursor.moveToNext()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        users.sortBy { it.userid }
        return users
    }
}