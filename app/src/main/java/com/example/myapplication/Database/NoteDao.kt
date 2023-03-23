package com.example.myapplication.Database
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.Model.note
@Dao
interface NoteDao {
    @Query("SELECT * FROM note_table ORDER BY id ASC")
    suspend fun getAllNotes(): List<note>

    @Query("SELECT * FROM note_table WHERE user LIKE :user")
    suspend fun findNoteByTitle(user: String): note

    @Insert
    suspend fun insert(note: note)

    @Delete
    suspend fun delete(note: note)

}