// Darshan Prajapati n01584247
package darshan.prajapati.n01584247.dp

import android.content.pm.PackageManager

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.material.snackbar.Snackbar
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
    private lateinit var locationBtn: Button
    private lateinit var  snackbar: Snackbar
    private var adClickCount = 0

    private val LOCATION_PERMISSION_REQUEST_CODE = 100
    private var locationPermissionDeniedCount = 0
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

        locationBtn = view.findViewById(R.id.darLocationButton)
        locationBtn.setOnClickListener {
            requestLocationPermission()
        }

    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            getLocation()
        }
    }

    private fun getLocation() {
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                val message = String.format(Locale.getDefault(), "Latitude: %f, Longitude: %f", latitude, longitude)
                // Show location in a Snackbar
                snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_INDEFINITE)
                snackbar.setAction("Dismiss") {
                    snackbar.dismiss()
                }
                snackbar.show()
            } else {
                snackbar = Snackbar.make(requireView(),
                    getString(R.string.location_is_not_determined), Snackbar.LENGTH_INDEFINITE)
                snackbar.setAction("Dismiss") {
                    snackbar.dismiss()
                }
                snackbar.show()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getLocation()
                } else {
                    locationPermissionDeniedCount++
                    if (locationPermissionDeniedCount > 2) {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri: Uri = Uri.fromParts("package", requireActivity().packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    } else {
                        Snackbar.make(requireView(),
                            getString(R.string.location_permission_is_required), Snackbar.LENGTH_LONG).show()
                    }
                }
                return
            }
        }
    }
}