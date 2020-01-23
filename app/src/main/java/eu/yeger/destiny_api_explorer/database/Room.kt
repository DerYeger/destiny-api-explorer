package eu.yeger.destiny_api_explorer.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDefinitionDao {

    @Query("SELECT * FROM databaseitemdefinition")
    fun getAll(): LiveData<List<DatabaseItemDefinition>>

    @Query("SELECT * FROM databaseitemdefinition WHERE hash = :hash")
    fun get(hash: Long): DatabaseItemDefinition?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg itemDefinitions: DatabaseItemDefinition)

    @Update
    fun update(vararg itemDefinitions: DatabaseItemDefinition)

    @Query("DELETE FROM databaseitemdefinition")
    fun deleteAll()

    @Delete
    fun delete(itemDefinition: DatabaseItemDefinition)
}

@Dao
interface SaleItemsDao {

    @Query("SELECT * FROM databasesaleitem")
    fun getAll(): List<DatabaseSaleItem>

    @Query("SELECT databaseitemdefinition.* FROM databasesaleitem INNER JOIN databaseitemdefinition ON (databasesaleitem.itemHash == databaseitemdefinition.hash)")
    fun getItemDefinitions(): LiveData<List<DatabaseItemDefinition>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg saleItems: DatabaseSaleItem)

    @Query("DELETE FROM databasesaleitem")
    fun deleteAll()
}

@Database(
    entities = [DatabaseItemDefinition::class, DatabaseSaleItem::class],
    version = 1,
    exportSchema = false
)
abstract class ItemDefinitionDatabase : RoomDatabase() {
    abstract val itemDefinitions: ItemDefinitionDao
    abstract val saleItems: SaleItemsDao
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
