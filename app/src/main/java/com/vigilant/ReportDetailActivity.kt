package com.vigilant

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import com.vigilant.databinding.ActivityReportDetailBinding

class ReportDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportDetailBinding
    var viewPager: ViewPager? = null
    private val spring_dots_indicator: SpringDotsIndicator? = null
    private var myViewPagerAdapter: MyViewPagerAdapter? = null
    var ct: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myViewPagerAdapter = MyViewPagerAdapter(ct)
        binding.viewPager.adapter = myViewPagerAdapter
        binding.springDotsIndicator.setViewPager(binding.viewPager)

        val Adapter  =VideoAdapter(this)
        binding.recylerview.setLayoutManager(LinearLayoutManager(ct, LinearLayoutManager.HORIZONTAL, false))
        binding.recylerview.setAdapter(Adapter)

        val title = intent.getStringExtra("title")

    }

    class MyViewPagerAdapter(private val mContext: Context) : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            val inflater = LayoutInflater.from(mContext)
            val view = inflater.inflate(R.layout.report_detail_item, container, false) as ViewGroup
            container.addView(view)
            return view
        }

        override fun getCount(): Int {
            return 5
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }
}