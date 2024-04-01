package darshan.prajapati.n01584247.dp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class D4pFragment : Fragment() {

    private lateinit var dateTimeTextView: TextView
    private lateinit var adView: AdView
    private var adClickCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_d4p, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dateTimeTextView = view.findViewById(R.id.darDateTimeTV)
        adView = view.findViewById(R.id.darAdView)
        val dateFormat = SimpleDateFormat("yyyy:MM:dd         hh:mm:ss", Locale.getDefault())

        val job = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                // Get current date and time
                val dateTime = dateFormat.format(Date())
                // Update TextView with current date and time
                dateTimeTextView.text = dateTime
                // Delay for 1 second
                delay(1000)
            }
        }

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        adView.adListener = object : AdListener() {
            override fun onAdClicked() {
                adClickCount++
                val message = getString(R.string.full_name) + ", ad counter $adClickCount times"
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        }

    }
}