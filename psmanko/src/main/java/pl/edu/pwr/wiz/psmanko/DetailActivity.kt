package pl.edu.pwr.wiz.psmanko

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Callback
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.viewPager

const val EXTRA_STARTING_POSITION = "start_position"
const val EXTRA_CURRENT_POSITION = "current_position"

class DetailActivity : AppCompatActivity() {
    val pos by lazy { intent.extras.getInt("position") }
    var viewPager: ViewPager? = null
    override fun onBackPressed() = Unit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportPostponeEnterTransition()
        setTheme(R.style.AppTheme)
//        val autoTransition = AutoTransition()
//        window.enterTransition = autoTransition
//        window.exitTransition = autoTransition
//        window.enterTransition?.excludeTarget(R.id.action_bar_container, true)
//        window.enterTransition?.excludeTarget(android.R.id.statusBarBackground, true)
//        window.enterTransition?.excludeTarget(android.R.id.navigationBarBackground, true)
//        window.exitTransition?.excludeTarget(R.id.action_bar_container, true)
//        window.exitTransition?.excludeTarget(android.R.id.statusBarBackground, true)
//        window.exitTransition?.excludeTarget(android.R.id.navigationBarBackground, true)
        val ui = ActivityUI({
            verticalLayout {

                viewPager = viewPager {
                    adapter = ViewPagerAdapter(MainActivity.items, pos, this@DetailActivity)
                    currentItem = pos
                }.lparams {
                    width = matchParent
                    weight = 1F
                }
                button("Cofnij") {
                    elevation = 0F
                    onClick {
                        if (viewPager?.currentItem == pos) {
                            finishAfterTransition()
                        } else {
                            finishAfterTransition()
                        }
                    }
                }.lparams(width = matchParent)
            }
        })
        ui.setContentView(this)

    }

    override fun finishAfterTransition() {
        val data = Intent()
        data.putExtra(EXTRA_STARTING_POSITION, pos)
        data.putExtra(EXTRA_CURRENT_POSITION, viewPager?.currentItem)
        setResult(Activity.RESULT_OK, data)
        super.finishAfterTransition()
    }
}

class ViewPagerAdapter(val items: List<String>, val transitionElement: Int, val activity: AppCompatActivity) : PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return with(container.context) {
            verticalLayout {
                imageView {
                    transitionName = "image" + position
                    var callback: Callback? = null
                    if (transitionElement == position) {
                        callback = object : Callback {
                            override fun onSuccess() = this@imageView.resumeSharedElementTransition(activity)

                            override fun onError() = this@imageView.resumeSharedElementTransition(activity)
                        }
                    }
                    loadUrl(items[position], callback)
                }.lparams(width = matchParent, height = matchParent)
            }
        }.apply { container.addView(this) }
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean = view == `object`

    override fun getCount(): Int = items.size

    override fun destroyItem(container: ViewGroup, position: Int, child: Any?) {
        container.removeView(child as View?)
    }

}
