package com.nikbrik.openweathermapclient.app

import android.os.Bundle
import androidx.fragment.app.Fragment

// fun Activity.toast(text: String) {
//    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
// }
//
// fun Fragment.toast(text: String) {
//    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
// }

fun <T : Fragment> T.withArguments(action: Bundle.() -> Unit): T {
    return apply {
        arguments = Bundle().apply(action)
    }
}
