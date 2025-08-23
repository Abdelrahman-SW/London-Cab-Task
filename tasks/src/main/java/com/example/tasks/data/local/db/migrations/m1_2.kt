package com.example.tasks.data.local.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Example: Add new column "age"
        db.execSQL("ALTER TABLE users ADD COLUMN title TEXT NOT NULL DEFAULT 'No Title'")
    }
}
