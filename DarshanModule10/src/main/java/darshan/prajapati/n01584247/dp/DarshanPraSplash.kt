// Darshan Prajapati n01584247
package darshan.prajapati.n01584247.dp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*


class DarshanPraSplash : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        CoroutineScope(Dispatchers.Main).launch {
            delay(3000) // Delay for 3 seconds

            // Start your app main activity
            val i = Intent(this@DarshanPraSplash, PrajapatiActivity10::class.java)
            startActivity(i)

            // Close this activity
            finish()
        }
    }

}