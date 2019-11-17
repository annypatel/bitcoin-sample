package bitcoin.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bitcoin.sample.R

/**
 * Launcher activity for this app.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
