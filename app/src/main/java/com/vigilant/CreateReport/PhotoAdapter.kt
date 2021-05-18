package com.vigilant.CreateReport

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.vigilant.R

class PhotoAdapter(private val context: Context) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.photo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //   holder.txtAntiPlanTitle.setText(antivirusPlanArrayLists.get(position).getTitle());

    }

    override fun getItemCount(): Int {
        return 3
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

       // var iv_image: ImageView

        // antivirusPlanClickListener.onPlanBuyNowClicked(antivirusPlanArrayLists.get(getAdapterPosition()).getMembershipId(),antivirusPlanArrayLists.get(getAdapterPosition()).getSellingPrice());
        init {

           // iv_image = itemView.findViewById(R.id.iv_image)

        }
    }

    companion object {

    }

    init {

    }
}