package eu.yeger.destiny_api_explorer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseItemDefinition::class], version = 1)
abstract class ItemDefinitionDatabase : RoomDatabase() {
    abstract val itemDefinitionDao: ItemDefinitionDao
}

private lateinit var INSTANCE: ItemDefinitionDatabase

fun getDatabase(context: Context): ItemDefinitionDatabase {
    synchronized(ItemDefinitionDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room
                .databaseBuilder(
                    context.applicationContext,
                    ItemDefinitionDatabase::class.java,
                    "itemDefinitions"
                )
                .build()
        }
        return INSTANCE
    }
}
