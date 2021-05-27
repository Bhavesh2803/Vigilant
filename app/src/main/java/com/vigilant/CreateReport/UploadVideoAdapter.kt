package com.vigilant.CreateReport

import android.content.Context
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mssinfotech.mycity.Utility.Constants
import com.vigilant.R
import java.util.*


class UploadVideoAdapter(private val context: Context, private var IncidentVideoArray: ArrayList<String>) : RecyclerView.Adapter<UploadVideoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.upload_video_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //   holder.txtAntiPlanTitle.setText(antivirusPlanArrayLists.get(position).getTitle());
        holder.iv_video.setOnClickListener { }

        val thumb = ThumbnailUtils.createVideoThumbnail(IncidentVideoArray.get(position), MediaStore.Video.Thumbnails.MICRO_KIND)
        holder.iv_video.setImageBitmap(thumb);
        holder.iv_remove.setOnClickListener {
            IncidentVideoArray.removeAt(position)
            Constants.IncidentVideoArray = Constants.IncidentVideoArray
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return IncidentVideoArray.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var iv_video: ImageView
        var iv_remove: ImageView

        // antivirusPlanClickListener.onPlanBuyNowClicked(antivirusPlanArrayLists.get(getAdapterPosition()).getMembershipId(),antivirusPlanArrayLists.get(getAdapterPosition()).getSellingPrice());
        init {

            iv_video = itemView.findViewById(R.id.iv_image)
            iv_remove = itemView.findViewById(R.id.iv_remove)

        }
    }

}