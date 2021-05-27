package com.vigilant.Home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso
import com.vigilant.Home.Model.WantedListDTO
import com.vigilant.R
import com.vigilant.ReportDetailActivity
import com.vigilant.WebViewActivity

class HomeListAdapter(private val context: Context, private val getAllReportesDTO: WantedListDTO) : RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.home_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //   holder.txtAntiPlanTitle.setText(antivirusPlanArrayLists.get(position).getTitle());
        holder.tv_id?.text = "Report ID: "+ getAllReportesDTO.data.get(position).id
        holder.tv_name?.text = "Name :  ${getAllReportesDTO.data.get(position).name} "
        holder.tv_location?.text = "Wanted for :  ${getAllReportesDTO.data.get(position).wantedFor} "
        holder.tv_priority?.text = "Last seen :  ${getAllReportesDTO.data.get(position).lastSeen} "
        Picasso.with(context).load(getAllReportesDTO.data.get(position).image).into(holder.iv_image)
        holder.rl_main_view?.setOnClickListener {
            val intent = Intent(context, ReportDetailActivity::class.java)
            intent.putExtra("criminal_id", getAllReportesDTO.data.get(position).id)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return getAllReportesDTO.data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_id: TextView? = null
        var tv_name: TextView? = null
        var tv_location: TextView? = null
        var tv_priority: TextView? = null
        var iv_image: CircularImageView? = null
        var rl_main_view: RelativeLayout? = null

        // antivirusPlanClickListener.onPlanBuyNowClicked(antivirusPlanArrayLists.get(getAdapterPosition()).getMembershipId(),antivirusPlanArrayLists.get(getAdapterPosition()).getSellingPrice());
        init {
            itemView.findViewById<TextView>(R.id.tv_id).also { tv_id = it }
            itemView.findViewById<TextView>(R.id.tv_name).also { tv_name = it }
            itemView.findViewById<TextView>(R.id.tv_wanted_for).also { tv_location = it }
            itemView.findViewById<TextView>(R.id.tv_last_seen).also { tv_priority = it }
            itemView.findViewById<CircularImageView>(R.id.iv_image).also { iv_image = it }
            itemView.findViewById<RelativeLayout>(R.id.rl_main_view).also { rl_main_view = it }

        }
    }
}