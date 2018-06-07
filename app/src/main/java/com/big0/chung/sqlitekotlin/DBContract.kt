package com.big0.chung.sqlitekotlin

import android.provider.BaseColumns

object DBContract {
    class UserEntity: BaseColumns {
        companion object {
            val TABLE_NAME = "users"
            val COLUMN_USER_ID = "userid"
            val COLUMN_NAME = "name"
            val COLUMN_AGE = "age"
        }
    }
}