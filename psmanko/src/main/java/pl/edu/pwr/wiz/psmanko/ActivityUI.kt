package pl.edu.pwr.wiz.psmanko

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toolbar
import org.jetbrains.anko.*

class ActivityUI(val layout: _FrameLayout.() -> Unit) : AnkoComponent<AppCompatActivity> {
    var toolbar: Toolbar? = null
    override fun createView(ui: AnkoContext<AppCompatActivity>): View {
        return with(ui) {
            linearLayout(R.style.AppTheme) {
                fitsSystemWindows = true
                frameLayout {
                    layout()
                }.lparams(width = matchParent, height = matchParent)
            }
        }
    }
}