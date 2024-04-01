package darshan.prajapati.n01584247.dp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class N013584247Fragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var webView: WebView
    private lateinit var videoUrls: Array<String>
    private lateinit var videoTitles: Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_n013584247, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.darRecyclerView)
        webView = view.findViewById(R.id.darWebView)

        videoUrls = resources.getStringArray(R.array.video_urls)
        videoTitles = resources.getStringArray(R.array.video_titles)

        val adapter = VideoAdapter(videoTitles) { position ->
            loadVideo(videoUrls[position as Int])
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Configure WebView settings
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
    }
    private fun loadVideo(videoUrl: String) {
        webView.loadUrl(videoUrl)
    }
}