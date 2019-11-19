package bitcoin.charts.domain

/**
 * Represents interval between two time instant.
 */
data class Interval(val value: String) {

    companion object {
        fun years(num: Int) = Interval("${num}years")
        fun days(num: Int) = Interval("${num}days")

        // add more if needed
    }
}
