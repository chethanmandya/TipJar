package com.example.tipjar.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tipjar.database.entity.TipHistory
import com.example.tipjar.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Chethan on 5/3/2019.
 */
@RunWith(AndroidJUnit4::class)
class TipJarDaoTest : DbTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val item = TipHistory(
        inputtedAmount = 10.00,
        inputtedTipInPercent = 15.0,
        totalTipAmount = 1.5,
        tipPerPerson = 0.0,
        grandTotal = 11.5,
        timestamp = System.currentTimeMillis()
    )

    @Test
    fun insertAndRead() {
        runBlocking {
            db.tipJarDao().insert(item)
        }
        // check data is not null
        val loaded = db.tipJarDao().getSavedTip(item.timestamp).getOrAwaitValue()
        assertThat(loaded, notNullValue())

        // check data element
        assertThat(loaded.grandTotal, `is`(11.5))
        assertThat(loaded.inputtedAmount, `is`(10.00))
    }
}
