package bitcoin.charts.domain

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import org.threeten.bp.Instant.ofEpochSecond
import org.threeten.bp.temporal.ChronoUnit

class DurationToDateImplTest {

    @Test
    fun shouldReturnDateByAddingDurationIntoBaseline() {
        // GIVEN
        val duration = 1L // days
        val durationUnit = ChronoUnit.DAYS
        val baseline = ofEpochSecond(1574153680) // 19 Nov 2019
        val durationToDate = DurationToDateImpl()

        // WHEN
        val date = durationToDate(DurationToDate.Args(duration, durationUnit, baseline))

        // THEN
        assertThat(date, equalTo("20 Nov"))
    }
}
