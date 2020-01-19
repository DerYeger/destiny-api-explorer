package eu.yeger.destiny_api_explorer.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDefinitionDao {

    @Query("SELECT * FROM databaseitemdefinition")
    fun getItemDefinitions(): LiveData<List<DatabaseItemDefinition>>

    @Query("SELECT * FROM databaseitemdefinition WHERE hash = :hash")
    fun getItemDefinition(hash: Long): DatabaseItemDefinition?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg itemDefinitions: DatabaseItemDefinition)

    @Delete
    fun deleteItemDefinition(hash: Long)
}

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
