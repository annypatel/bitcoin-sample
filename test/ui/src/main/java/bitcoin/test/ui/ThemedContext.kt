package bitcoin.test.ui

import android.content.Context
import android.view.ContextThemeWrapper
import androidx.test.core.app.ApplicationProvider.getApplicationContext

/**
 * Returns [Context] with given theme.
 */
fun themedContext(theme: Int): Context = ContextThemeWrapper(getApplicationContext(), theme)
