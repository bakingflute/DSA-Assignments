// Various checkers for password strength. This module contains a variety of functions that can be used to check passwords
// for various sets of passwords rules, such as not using common blocklisted passwords, using passwords of a certain length, 
// or ensuring the use of various character types in passwords. These functions can be used in conjunction to
// provide more complex password checking.

fun not_common_password(password: String): Boolean {
    if (password == "123456")
        {return false}
        else if (password == "qwerty")
        {return false}
        else if (password == "password")
        {return false}
    else 
        {return true}

}

fun meets_length_restriction(password: String): Boolean {
    if (6 <= password.length && password.length <= 16)
        {return false}
    else 
        {return true}

}

fun count_occurrences_in_string(target:Int, characters_to_count:String): {
    var occurrences = 0
    for (character in target) {
        if (characters_to_count.contains(character)) {
            occurrences += 1
        }
    }
    return occurrences
}

fun uses_all_character_classes(password: String): Boolean {
    val hasLower = count_occurrences_in_string(password, ascii_lowercase) >= 1
    val hasUpper = count_occurrences_in_string(password, ascii_uppercase) >= 1
    val hasDigit = count_occurrences_in_string(password, digits) >= 1
    val hasPunct = count_occurrences_in_string(password, punctuation) >= 1
    return hasLower && hasUpper && hasDigit && hasPunct
}

// extra helper matching the python version
fun long_enough_or_all_rules(password: String): Boolean {
    return password.length > 16 ||
        (not_common_password(password)
                && meets_length_restriction(password)
                && uses_all_character_classes(password))
}
