package com.ihediohachinedu.bookkeeper.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ihediohachinedu.bookkeeper.converter.DateTypeConverter

@Database(
    entities = [Book::class],
    version = 3
)
@TypeConverters(DateTypeConverter::class)
abstract class BookDb : RoomDatabase() {

    abstract fun bookDao(): BookDao

    //Create a database and make an instance of it using Room.databaseBuilder()
    //The database instance should be a singleton object
    companion object {

        private var instance: BookDb? = null

        //Defining the migration
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ${Book.TABLE_NAME} "
                        + " ADD COLUMN description TEXT DEFAULT 'Add Description' " +
                        " NOT NULL ")
            }
        }

        //Defining the migration
        val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ${Book.TABLE_NAME} " +
                        " ADD COLUMN last_updated INTEGER DEFAULT NULL")
            }
        }

        fun getDatabase(context: Context) : BookDb? {
            if(instance == null) {

                synchronized(BookDb::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder<BookDb>(context.applicationContext,
                        BookDb::class.java, "book_database")
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                            .build()
                    }
                }
            }
            return instance
        }
    }

//    companion object {
//        @Volatile
//        private var bookRoomInstance: BookDb? = null
//        private val LOCK = Any()
//
//
//        operator fun invoke(context: Context) = bookRoomInstance ?: synchronized(LOCK) {
//            bookRoomInstance ?: createDatabase(context).also { bookRoomInstance= it }
//        }
//
//        private fun createDatabase(context: Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                BookDb::class.java,
//                "book_database"
//            ).build()
//    }

}