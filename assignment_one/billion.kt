import kotlin.math.roundToInt

// Function for the One Billion Seconds exercise.

class variables() {

val seconds_per_minute: Double = 60.0  
val minutes_per_hour: Double  = 60.0   
val hours_per_day: Double = 24.0 
val days_per_year: Float = 365.25f // Include leap days (approximately)
}

fun time_to_billion_seconds(age: Double) {

    val classvariables = variables()

    var age_seconds = (
        age //An int representing a number to compute
        *classvariables.days_per_year
        *classvariables.hours_per_day
        *classvariables.minutes_per_hour
        *classvariables.seconds_per_minute
    )

    var seconds_remaining: Double = 1e9 - age_seconds

    // Split the total number of days remaining into years and days.
    val total_days_remaining: Double = seconds_remaining / (
       classvariables.seconds_per_minute * classvariables.minutes_per_hour * classvariables.hours_per_day
    )
    val years_remaining = kotlin.math.floor(total_days_remaining / classvariables.days_per_year).toLong()
    var days_remaining = total_days_remaining % classvariables.days_per_year

    // If total_days_remaining is negative, we need to swap the sign on
    // days_remaining.
    
    if (
        years_remaining < 0){

       days_remaining *= -1
    }

    val days_remaining_int = days_remaining.toInt()

    println(
        "You have $years_remaining years and $days_remaining_int days until you are a billion seconds old."
    )
}
