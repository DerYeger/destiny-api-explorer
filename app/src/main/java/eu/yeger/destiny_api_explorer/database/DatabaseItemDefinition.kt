package eu.yeger.destiny_api_explorer.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity
data class DatabaseItemDefinition(
    @PrimaryKey
    val hash: Long,
    val name: String,
    val description: String,
    val iconUrl: String,
    val soldByXur: Boolean,
    val type: String,
    val screenshotUrl: String?
)

@Dao
interface ItemDefinitionDao {

    @Query("SELECT * FROM databaseitemdefinition")
    fun getItemDefinitions(): LiveData<List<DatabaseItemDefinition>>

    @Query("SELECT * FROM databaseitemdefinition WHERE hash = :hash")
    fun getItemDefinition(hash: Long): DatabaseItemDefinition?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg itemDefinitions: DatabaseItemDefinition)

    @Update
    fun updateAll(vararg itemDefinitions: DatabaseItemDefinition)

    @Query("DELETE FROM databaseitemdefinition")
    fun deleteItemDefinitions()
}
