package io.mdd.addit.util

import java.net.URL


//class CheckUrlValid {

    /* Returns true if url is valid */
    fun isValid(url: String): Boolean {
        /* Try creating a valid URL */
        try {
            URL(url).toURI()
            return true
        } catch (e: Exception) {
            return false
        }
        // If there was an Exception
        // while creating URL object
    }
//}