package bitcoin.charts.domain

import bitcoin.base.domain.UseCase
import bitcoin.charts.domain.DurationToDate.Args
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import javax.inject.Inject

/**
 * Converts the given duration into a date against the baseline and unit of duration.
 */
interface DurationToDate : UseCase<Args, String> {

    data class Args(
        val duration: Long,
        val durationUnit: ChronoUnit,
        val baseline: Instant
    )
}

/**
 * Implementation of [DurationToDate], simply adds the duration into the baseline and converts the
 * result into a date in given format.
 */
class DurationToDateImpl @Inject constructor() : DurationToDate {

    private val zone = ZoneId.systemDefault()
    private val formatter = DateTimeFormatter.ofPattern("dd MMM")

    override fun invoke(input: Args): String {
        val baselineDate = LocalDateTime.ofInstant(input.baseline, zone)
        val currentDate = baselineDate.plus(input.duration, input.durationUnit)
        return formatter.format(currentDate)
    }
}
