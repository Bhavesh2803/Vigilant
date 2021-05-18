package com.vigilant

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.mssinfotech.mycity.Utility.CommonFunction.showToast
import com.vigilant.CreateReport.CreateReportActivity
import com.vigilant.Model.GetAllReportesDTO
import com.vigilant.Network.rest.ServiceGenerator
import com.vigilant.VideoList.VideosActivity
import com.vigilant.databinding.ActivityDashboardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityDashboardBinding
    var ct: Context = this
    var menu_button: ImageView? = null
    var recylerview: RecyclerView? = null
    var ivMenu: ImageView? = null
    var gr: GetAllReportesDTO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, binding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        menu_button = findViewById(R.id.iv_menu)



        recylerview = findViewById(R.id.recylerview) as RecyclerView
        ivMenu = findViewById(R.id.iv_menu) as ImageView



        ivMenu!!.setOnClickListener {
            openSideMenuDrawer()
        }
        callGetReportListApi()
    }

    override fun onResume() {
        super.onResume()
        try {

            binding.navView.bringToFront();
            binding.navView.setNavigationItemSelectedListener(this)


            val headerview = binding.navView.getHeaderView(0)
            init(headerview)
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), 1)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun openSideMenuDrawer() {
        try {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        binding.drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }

    private fun init(headerview: View) {
        val ll_profile: LinearLayout
        val ll_create_report: LinearLayout
        val ll_news: LinearLayout
        val ll_video: LinearLayout
        val ll_social_activites: LinearLayout


        ll_profile = headerview.findViewById<View>(R.id.ll_profile) as LinearLayout
        ll_create_report = headerview.findViewById<View>(R.id.ll_create_report) as LinearLayout
        ll_news = headerview.findViewById<View>(R.id.ll_news) as LinearLayout
        ll_video = headerview.findViewById<View>(R.id.ll_video) as LinearLayout
        ll_social_activites = headerview.findViewById<View>(R.id.ll_social_activites) as LinearLayout

        ll_profile.setOnClickListener {
            startActivity(Intent(ct, ProfileActivity::class.java))
        }
        ll_create_report.setOnClickListener {
            startActivity(Intent(ct, CreateReportActivity::class.java))
        }
        ll_news.setOnClickListener {
            startActivity(Intent(ct, NewsListActivity::class.java))
        }
        ll_video.setOnClickListener {
            startActivity(Intent(ct, VideosActivity::class.java))
        }

        ll_social_activites.setOnClickListener {
            startActivity(Intent(ct, SocialPageActivity::class.java))
        }
        /* ll_share_app = headerview.findViewById<View>(R.id.ll_share_app) as LinearLayout

         ll_profile.setOnClickListener { //startActivity(new Intent(ct, VendorProfileActivity.class));

         }*/

    }

    private fun callGetReportListApi() {


        val getAllReportCall = ServiceGenerator.getClient(true).getAllReport()
        getAllReportCall.enqueue(object : Callback<GetAllReportesDTO?> {
            override fun onResponse(call: Call<GetAllReportesDTO?>, response: Response<GetAllReportesDTO?>) {
               showToast(ct,response.message())

                val Adapter = response.body()?.let { HomeListAdapter(ct, it) }
                recylerview!!.setLayoutManager(LinearLayoutManager(ct))
                recylerview!!.adapter = Adapter
            }

            override fun onFailure(call: Call<GetAllReportesDTO?>, t: Throwable) {
                showToast(ct,t.message.toString())
            }
        })



    }
}