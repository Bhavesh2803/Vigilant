package com.vigilant.VideoList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vigilant.R
import com.vigilant.VideoList.Model.GetAllVideosDTO

class VideoListAdapter(private val context: Context,private val videoDTO:GetAllVideosDTO) : RecyclerView.Adapter<VideoListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.video_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_title!!.text = videoDTO.data.get(position).title
        holder.tv_description!!.text = videoDTO.data.get(position).description
        holder.tv_created_at!!.text = videoDTO.data.get(position).createdAt

    }

    override fun getItemCount(): Int {
        return videoDTO.data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_title:TextView? = null
        var tv_description:TextView? = null
        var tv_created_at:TextView? = null

        // antivirusPlanClickListener.onPlanBuyNowClicked(antivirusPlanArrayLists.get(getAdapterPosition()).getMembershipId(),antivirusPlanArrayLists.get(getAdapterPosition()).getSellingPrice());
        init {

            itemView.findViewById<TextView>(R.id.tv_title).also { tv_title = it }
            itemView.findViewById<TextView>(R.id.tv_description).also { tv_description = it }
            itemView.findViewById<TextView>(R.id.tv_created_at).also { tv_created_at = it }

        }
    }


}