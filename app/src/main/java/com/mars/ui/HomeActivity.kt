package com.mars.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.mars.R
import com.mars.common.base.AbstractActivity
import com.mars.models.MenuOption
import com.mars.models.UserInfo
import com.mars.ui.adapters.MenuAdapter
import com.mars.ui.dashboard.MarkAttendanceFragment
import com.mars.utils.AppCache
import com.mars.utils.AppConstants
import com.mars.viewmodels.DashboardViewModel

class HomeActivity : AbstractActivity<DashboardViewModel>(), View.OnClickListener {

    private lateinit var drawerLayout: DrawerLayout

    private lateinit var userInfo: UserInfo

    override val viewModel: DashboardViewModel
        get() = ViewModelProviders.of(this).get(DashboardViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
//        setSupportActionBar(toolbar)

        userInfo = AppCache.INSTANCE.getUserInfo()

        drawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        findViewById<ImageButton>(R.id.btnMenu).setOnClickListener(this)

        val menuAdapter = getMenuAdapter()

        val menuListView = findViewById<RecyclerView>(R.id.menuListView)
        menuListView.adapter = menuAdapter

        menuAdapter.selectedMenu.observe(this, Observer {
            drawerLayout.closeDrawer(GravityCompat.START)
            handleMenuSelection(it)
        })

        findViewById<View>(R.id.nav_view).findViewById<TextView>(R.id.lblName).text = userInfo.UserId

        findViewById<View>(R.id.nav_view).findViewById<ImageView>(R.id.btnLogout).setOnClickListener({
            showLogOutDialog(true)
        })

        pushFragment(true, MarkAttendanceFragment(), R.id.container)
    }

    override fun onBackPressed() {
        if(this.supportFragmentManager.backStackEntryCount == 1) {
            showLogOutDialog(false)
        } else {
            super.onBackPressed()
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btnMenu -> drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun getMenuAdapter(): MenuAdapter {

        val menuList = ArrayList<MenuOption>()
        menuList.add(MenuOption(getString(R.string.lblViewAttendance), R.string.lblViewAttendance))
        menuList.add(MenuOption(getString(R.string.lblMarkAttendance), R.string.lblMarkAttendance))
        menuList.add(MenuOption(getString(R.string.lblContact), R.string.lblContact))
        menuList.add(MenuOption(getString(R.string.lblFAQ), R.string.lblFAQ))
        menuList.add(MenuOption(getString(R.string.lblUserGuide), R.string.lblUserGuide))

        val menuAdapter = MenuAdapter()
        menuAdapter.setMenuList(menuList)

        return menuAdapter
    }

    private fun handleMenuSelection(selectedOption: Int) {

        when(selectedOption) {

            R.string.lblViewAttendance -> {
//                pushFragment(true, ViewAttendanceFragment(), R.id.container)
                redirectToViewAttendance()
            }

            R.string.lblMarkAttendance -> {
                redirectToMarkAttendance()
            }

            R.string.lblContact -> {
                redirectToUrl(AppConstants.ServiceURLs.CONTACT_URL)
            }

            R.string.lblFAQ -> {
                redirectToUrl(AppConstants.ServiceURLs.FAQ_URL)
            }

            R.string.lblUserGuide -> {
                redirectToUrl(AppConstants.ServiceURLs.USER_GUIDE_URL)
            }
        }
    }

    private fun redirectToUrl(url: String) {
        val intent = Intent(this, BrowserActivity::class.java)
        intent.putExtra(BrowserActivity.URL_TAG, url)
        startActivity(intent)

//        try {
//            val intent = Intent("android.intent.action.VIEW",
//                Uri.parse(url))
//            startActivity(intent)
//
//        } catch (e: ActivityNotFoundException) {
//
//        }

    }

    private fun redirectToMarkAttendance() {
        val fragCount = this.supportFragmentManager.backStackEntryCount
        if(fragCount > 1) {
            this.supportFragmentManager.popBackStack(MarkAttendanceFragment::class.java.name, 0)
        }
    }

    fun redirectToViewAttendance() {
        val intent = Intent(this, ViewAttendanceActivity::class.java)
        startActivity(intent)
    }

}
