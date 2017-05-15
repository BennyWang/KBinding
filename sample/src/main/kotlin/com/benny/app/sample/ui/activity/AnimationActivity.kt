package com.benny.app.sample.ui.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Button
import com.benny.app.sample.R
import com.benny.app.sample.ui.activity.`AnimationActivity$$KB`.*
import com.benny.app.sample.ui.layout.TitleToolBarView
import com.benny.library.kbinding.annotation.Command
import com.benny.library.kbinding.bind.commandBinding
import com.benny.library.kbinding.common.bindings.defaultCommand
import com.benny.library.kbinding.common.bindings.postDefaultEvent
import com.benny.library.kbinding.dsl.bind
import com.benny.library.kbinding.dsl.inflate
import com.benny.library.kbinding.util.SubjectCache
import com.benny.library.kbinding.view.ViewBinderComponent
import com.benny.library.kbinding.view.setContentView
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import rx.subjects.PublishSubject

/**
 * Created by ldy on 9/1/16.
 * This is a simple example, the use of the features custom events
 */
class AnimationActivity : BaseActivity() {
    lateinit var toolBar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AnimationActivityUI().setContentView(this).bindTo(this)

        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar?.title = "";
    }

    @Command
    fun box1Finish() {
        toast("End of the animation of the box1")
    }

    @Command
    fun box2Finish() {
        toast("End of the animation of the box2")
    }

    fun jump(view: View, finish: () -> Unit) {
        val down = TranslateAnimation(0f, 0f, -600f, 0f)
        down.fillAfter = true
        down.interpolator = BounceInterpolator()
        down.duration = 2500
        down.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }
            override fun onAnimationRepeat(animation: Animation?) {
            }
            override fun onAnimationEnd(animation: Animation?) {
                finish()
            }
        })
        view.startAnimation(down)

    }

    override fun onDestroy() {
        super.onDestroy()
        SubjectCache.removeContext(this)
    }

    inner class AnimationActivityUI : ViewBinderComponent<AnimationActivity> {
        override fun builder(): AnkoContext<out AnimationActivity>.() -> Unit = {
            var box1: Button?
            var box2: Button? = null
            val box2Subject:PublishSubject<Unit> = PublishSubject.create()
            verticalLayout {
                appBarLayout {
                    toolBar = inflate(TitleToolBarView(ctx.resources.getString(R.string.animation)), this@appBarLayout) as Toolbar
                }
                frameLayout {
                    lparams(matchParent, matchParent) {
                        horizontalPadding = dip(16)
                        verticalPadding = dip(56)
                    }
                    box1 = button("box1") {
                        // Library will automatically create a and will only create a default subject for every view.
                        // this call will use the default subject.
                        // But this requires invoke SubjectCache#removeContext(activity) when the activity destroyed
                        setOnClickListener { owner.jump(box2!!) { postDefaultEvent() } }
                        bind { defaultCommand(k_box2Finish) }
                    }.lparams(gravity = Gravity.BOTTOM)
                    box2 = button("box2") {
                        //You can also create a subject, so that a more flexible
                        setOnClickListener { owner.jump(box1!!) { box2Subject.onNext(Unit) } }
                        bind { commandBinding(k_box1Finish,box2Subject) }
                    }.lparams(gravity = Gravity.END.xor(Gravity.BOTTOM))
                }
            }
        }

    }
}
