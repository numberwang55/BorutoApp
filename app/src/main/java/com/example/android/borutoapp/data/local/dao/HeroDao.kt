package com.example.android.borutoapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.android.borutoapp.domain.model.entity.HeroEntity

@Dao
interface HeroDao {

    @Query("SELECT * FROM hero_table ORDER BY id ASC")
    fun getAllHeroes(): PagingSource<Int, HeroEntity>

    @Query("SELECT * FROM hero_table WHERE id LIKE :heroId")
    suspend fun getHeroById(heroId: Int): HeroEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHeroes(heroes: List<HeroEntity>)

    @Query("DELETE FROM hero_table")
    suspend fun deleteAllHeroes()

}