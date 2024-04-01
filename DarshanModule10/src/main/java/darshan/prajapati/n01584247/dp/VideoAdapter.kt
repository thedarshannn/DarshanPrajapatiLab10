package darshan.prajapati.n01584247.dp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VideoAdapter(private val videoTitles: Array<String>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.darNameTV)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.textViewTitle.text = videoTitles[position]
    }

    override fun getItemCount(): Int {
        return videoTitles.size
    }
}