package darshan.prajapati.n01584247.dp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
class Da1rshanFragment : Fragment() {

    private lateinit var spinner: Spinner
    private lateinit var imageView: ImageView
    private lateinit var button: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: ArrayAdapter<ImageWithText>
    private val imageList = listOf(
        ImageWithText("https://cdn-icons-png.flaticon.com/512/5971/5971593.png", "Cricket"),
        ImageWithText("https://cdn-icons-png.flaticon.com/512/2642/2642160.png", "Soccer"),
        ImageWithText("https://cdn-icons-png.flaticon.com/512/7439/7439270.png", "Volleyball"),
        ImageWithText("https://cdn-icons-png.flaticon.com/512/3055/3055855.png", "Basketball")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_da1rshan, container, false)

        spinner = view.findViewById(R.id.darSpinner)
        imageView = view.findViewById(R.id.darImageView)
        button = view.findViewById(R.id.darDownloadButton)
        progressBar = view.findViewById(R.id.darProgressBar)

        // Create an adapter for the spinner
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, imageList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        button.setOnClickListener {
            downloadAndDisplayImage()
        }

        // Inflate the layout for this fragment
        return view
    }

    data class ImageWithText(val url: String, val text: String) {
        override fun toString(): String {
            return text
        }
    }

    private fun downloadAndDisplayImage() {
        progressBar.visibility = View.VISIBLE
        imageView.visibility = View.GONE

        val selectedImage = spinner.selectedItem as ImageWithText

        // CoroutineScope will be cancelled when the Fragment's view is destroyed (lifecycle-aware)
        viewLifecycleOwner.lifecycleScope.launch {
            // Simulating image download, replace with actual download logic using Glide
            val downloadedBitmap = withContext(Dispatchers.IO) {
                delay(5000) // Simulating 5 seconds delay
                try {
                    Glide.with(requireContext())
                        .asBitmap()
                        .load(selectedImage.url)
                        .submit()
                        .get()
                } catch (e: Exception) {
                    null
                }
            }

            progressBar.visibility = View.GONE
            if (downloadedBitmap != null) {
                imageView.setImageBitmap(downloadedBitmap)
                imageView.visibility = View.VISIBLE
            } else {
                Toast.makeText(requireContext(), "Failed to download image", Toast.LENGTH_SHORT).show()
            }
        }
    }

}