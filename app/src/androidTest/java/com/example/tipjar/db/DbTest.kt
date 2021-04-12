package com.example.tipjar.db

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.room.Room
import com.example.tipjar.database.TipDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.util.concurrent.TimeUnit

/**
 * Created by Chethan on 5/3/2019.
 */
abstract class DbTest {
    @Rule
    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()
    private lateinit var _db: TipDatabase
    val db: TipDatabase
        get() = _db

    @Before
    fun initDb() {
        _db = Room.inMemoryDatabaseBuilder(
            androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext,
            TipDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() {
        countingTaskExecutorRule.drainTasks(10, TimeUnit.SECONDS)
        _db.close()
    }
}
