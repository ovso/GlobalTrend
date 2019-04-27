package io.github.ovso.globaltrend.view.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.navigation.NavigationView
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.R.id
import io.github.ovso.globaltrend.R.layout
import io.github.ovso.globaltrend.R.string
import io.github.ovso.globaltrend.view.ui.main.dailytrend.DailyTrendFragment
import io.github.ovso.globaltrend.view.ui.main.realtimetrend.RealTimeTrendFragment
import kotlinx.android.synthetic.main.activity_main.drawer_layout
import kotlinx.android.synthetic.main.activity_main.nav_view
import kotlinx.android.synthetic.main.app_bar_main.tabs_main
import kotlinx.android.synthetic.main.app_bar_main.toolbar
import kotlinx.android.synthetic.main.app_bar_main.viewpager_main

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)
    setSupportActionBar(toolbar)

    val toggle = ActionBarDrawerToggle(
        this,
        drawer_layout, toolbar,
        string.navigation_drawer_open,
        string.navigation_drawer_close
    )
    drawer_layout.addDrawerListener(toggle)
    toggle.syncState()

    nav_view.setNavigationItemSelectedListener(this)

    setupTabsAndViewPager()
  }

  private fun setupTabsAndViewPager() {
    viewpager_main.adapter = ViewPagerAdapter(
        supportFragmentManager,
        resources.getStringArray(R.array.tab_names)
    )
    tabs_main.setupWithViewPager(viewpager_main)
  }

  override fun onBackPressed() {
    if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
      drawer_layout.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    when (item.itemId) {
      id.action_settings -> return true
      else -> return super.onOptionsItemSelected(item)
    }
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    // Handle navigation view item clicks here.
    when (item.itemId) {
      id.nav_camera -> {
        // Handle the camera action
      }
      id.nav_gallery -> {

      }
      id.nav_slideshow -> {

      }
      id.nav_manage -> {

      }
      id.nav_share -> {

      }
      id.nav_send -> {

      }
    }

    drawer_layout.closeDrawer(GravityCompat.START)
    return true
  }

  inner class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val titles: Array<String>
  ) :
      FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int) = when (position) {
      0 -> DailyTrendFragment.newInstance()
      1 -> RealTimeTrendFragment.newInstance()
      else -> BlankFragment.newInstance("", "")
    }

    override fun getCount() = 2
    override fun getPageTitle(position: Int) = titles.get(position)
  }
}
