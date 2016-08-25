package com.benny.library.kbinding.common

import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.*
import android.graphics.drawable.shapes.ArcShape
import android.graphics.drawable.shapes.RectShape
import android.graphics.drawable.shapes.RoundRectShape
import android.graphics.drawable.shapes.Shape
import android.view.View
import org.jetbrains.anko.internals.AnkoInternals
import java.io.InputStream
import java.util.*

/**
 * Created by benny on 12/10/15.
 */

interface DrawableProvider<out T: Drawable> {
    val drawable: T
    var drawableState: IntArray
    var drawableLevel: Int
}

@Suppress("UNCHECKED_CAST")
interface RawDrawableProvider<out T: Drawable> : DrawableProvider<T> {
    override val drawable: T
        get() = this as T
    override var drawableState: IntArray
        set(value) {
            (this as T).state = value
        }
        get() = (this as T).state

    override var drawableLevel: Int
        set(value) {
            (this as T).level = value
        }
        get() = (this as T).level
}

interface DrawableManager {
    fun addDrawable(drawable: Drawable, params: Any?)
}

fun AnkoInternals.addDrawable(manager: DrawableManager, drawable: Drawable) {
    when(manager) {
        is _StateListManager -> manager.apply { addDrawable(drawable, drawable.state) }
        is _LevelListManager -> manager.apply { addDrawable(drawable, drawable.level) }
        else -> manager.addDrawable(drawable, null)
    }
}

inline fun <R: Drawable, T : DrawableProvider<R>> DrawableManager.ankoDrawable(factory: () -> T, init: T.() -> Unit): R {
    val manager = factory()
    manager.init()
    val drawable = manager.drawable
    AnkoInternals.addDrawable(this, drawable)
    return drawable
}

inline fun <R: Drawable, T : DrawableProvider<R>> ankoDrawable(factory: () -> T, init: T.() -> Unit): R {
    val manager = factory()
    manager.init()
    val drawable = manager.drawable
    return drawable
}

inline fun DrawableManager.layer(init: _LayerManager.() -> Unit): Drawable {
    return ankoDrawable({ _LayerManager() }, init)
}
inline fun layer(init: _LayerManager.() -> Unit): Drawable {
    return ankoDrawable({ _LayerManager() }, init)
}

inline fun DrawableManager.stateList(init: _StateListManager.() -> Unit): Drawable {
    return ankoDrawable({ _StateListManager() }, init)
}
inline fun stateList(init: _StateListManager.() -> Unit): Drawable {
    return ankoDrawable({ _StateListManager() }, init)
}

inline fun DrawableManager.levelList(init: _LevelListManager.() -> Unit): Drawable {
    return ankoDrawable({ _LevelListManager() }, init)
}
inline fun levelList(init: _LevelListManager.() -> Unit): Drawable {
    return ankoDrawable({ _LevelListManager() }, init)
}

inline fun DrawableManager.roundRect(init: _RoundRectManager.() -> Unit): ShapeDrawable {
    return ankoDrawable({ _RoundRectManager() }, init)
}
inline fun roundRect(init: _RoundRectManager.() -> Unit): ShapeDrawable {
    return ankoDrawable({ _RoundRectManager() }, init)
}

inline fun DrawableManager.oval(init: _OvalManager.() -> Unit): ShapeDrawable {
    return ankoDrawable({ _OvalManager() }, init)
}
inline fun oval(init: _OvalManager.() -> Unit): ShapeDrawable {
    return ankoDrawable({ _OvalManager() }, init)
}

inline fun DrawableManager.arc(init: _ArcManager.() -> Unit): ShapeDrawable {
    return ankoDrawable({ _ArcManager() }, init)
}
inline fun arc(init: _ArcManager.() -> Unit): ShapeDrawable {
    return ankoDrawable({ _ArcManager() }, init)
}

inline fun DrawableManager.bitmap(res: Resources, init: _BitmapManager.() -> Unit): BitmapDrawable {
    return ankoDrawable({ _BitmapManager(res) }, init)
}
inline fun bitmap(res: Resources, init: _BitmapManager.() -> Unit): BitmapDrawable {
    return ankoDrawable({ _BitmapManager(res) }, init)
}

inline fun DrawableManager.color(init: _ColorManager.() -> Unit): ColorDrawable {
    return ankoDrawable({ _ColorManager() }, init)
}
inline fun color(init: _ColorManager.() -> Unit): ColorDrawable {
    return ankoDrawable({ _ColorManager() }, init)
}


class _LevelListManager() : LevelListDrawable(), DrawableManager, RawDrawableProvider<LevelListDrawable> {
    var minLevel = level

    override fun addDrawable(drawable: Drawable, params: Any?) {
        if(params == null) return
        if(params is Int) addLevel(params, drawable)
    }

    fun addLevel(level: Int, drawable: Drawable) {
        if(level <= minLevel) throw RuntimeException("drawableLevel less than minLevel $minLevel")
        addLevel(minLevel, level, drawable)
        minLevel = level
    }
}

class _StateListManager() : StateListDrawable(), DrawableManager, RawDrawableProvider<StateListDrawable> {
    override fun addDrawable(drawable: Drawable, params: Any?) {
        if(params == null) return
        if(params is IntArray) addState(params, drawable)
    }
}

class _LayerManager(): DrawableManager, DrawableProvider<LayerDrawable> {
    override var drawableState: IntArray = intArrayOf()
    override var drawableLevel: Int = 0

    val layers: MutableList<Drawable> = ArrayList()

    override val drawable: LayerDrawable get() = LayerDrawable(layers.toTypedArray()).apply { setState(drawableState); setLevel(drawableLevel) }

    override fun addDrawable(drawable: Drawable, params: Any?) {
        layers.add(drawable)
    }
}

class _ColorManager() : ColorDrawable(), RawDrawableProvider<ColorDrawable> {
}

class _BitmapManager(val res: Resources) : DrawableProvider<BitmapDrawable> {
    override var drawableState: IntArray = intArrayOf()
    override var drawableLevel: Int = 0

    var bitmap: Bitmap? = null
    var filePath: String? = null
    var stream: InputStream? = null

    override val drawable: BitmapDrawable
        get() = when {
            bitmap != null -> BitmapDrawable(res, bitmap)
            filePath != null -> BitmapDrawable(res, filePath)
            stream != null -> BitmapDrawable(res, stream)
            else -> throw RuntimeException("must set one of bitmap, filePath or stream")
        }.apply {
            setState(drawableState); setLevel(drawableLevel)
        }
}

abstract class _ShapeManager() : DrawableProvider<ShapeDrawable> {
    override var drawableState: IntArray = intArrayOf()
    override var drawableLevel: Int = 0

    var color: Int = Color.TRANSPARENT

    var leftPadding: Int = 0
    var topPadding: Int = 0
    var rightPadding: Int = 0
    var bottomPadding: Int = 0
    var padding: Int = 0
        set(value) {
            leftPadding = value
            rightPadding = value
            topPadding = value
            bottomPadding = value
        }
}

class _RoundRectManager() : _ShapeManager() {
    override var drawableState: IntArray = intArrayOf()

    var radius: Float = 0f
    var bottomLeftRadius: Float = 0f
    var bottomRightRadius: Float = 0f
    var topLeftRadius: Float = 0f
    var topRightRadius: Float = 0f

    override val drawable: ShapeDrawable
        get() {
            val outerRadii: FloatArray = if(radius != 0f) floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)
            else floatArrayOf(topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius)
            return ShapeDrawable(RoundRectShape(outerRadii, null, null)).apply {
                paint.color = color
                setPadding(leftPadding, topPadding, rightPadding, bottomPadding)
                setState(drawableState)
                setLevel(drawableLevel)
            }
        }
}

class _OvalManager() : _ShapeManager() {
    override val drawable: ShapeDrawable
        get() {
            return ShapeDrawable(OvalShape()).apply {
                paint.color = color
                setPadding(leftPadding, topPadding, rightPadding, bottomPadding)
                setState(drawableState)
                setLevel(drawableLevel)
            }
        }

    open class OvalShape : Shape() {
        override fun draw(canvas: Canvas, paint: Paint) {
            canvas.drawOval(RectF(0f, 0f, width, height), paint)
        }
        /*override fun getOutline(outline: Outline) {
            val rect = rect()
            outline.setOval(Math.ceil(rect.left.toDouble()).toInt(), Math.ceil(rect.top.toDouble()).toInt(),
                    Math.floor(rect.right.toDouble()).toInt(), Math.floor(rect.bottom.toDouble()).toInt())
        }*/
    }
}

class _ArcManager() : _ShapeManager() {
    var start: Float = 0f
    var sweep: Float = 360f

    override val drawable: ShapeDrawable
        get() {
            return ShapeDrawable(ArcShape(start, sweep)).apply {
                paint.color = color
                setPadding(leftPadding, topPadding, rightPadding, bottomPadding)
                setState(drawableState)
                setLevel(drawableLevel)
            }
        }
}


/*helper method*/
/*----------------------------*/
inline fun DrawableManager.borderRoundRect(radius: Float, color: Int, init: _LayerManager.() -> Unit) : Drawable {
    return borderRoundRect(radius, color, 0f, Color.TRANSPARENT, init)
}
inline fun View.borderRoundRect(radius: Float, color: Int, init: _LayerManager.() -> Unit) : Drawable {
    return borderRoundRect(radius, color, 0f, Color.TRANSPARENT, init)
}

inline fun DrawableManager.borderRoundRect(radius: Float, color: Int, strokeWidth: Float, strokeColor: Int, init: _LayerManager.() -> Unit) : Drawable {
    return layer {
        roundRect {
            padding = strokeWidth.toInt()
            this.radius = radius
            this.color = strokeColor
        }
        roundRect {
            this.radius = if(radius >= strokeWidth) radius - strokeWidth else 0f
            this.color = color
        }
        init()
    }
}
inline fun View.borderRoundRect(radius: Float, color: Int, strokeWidth: Float, strokeColor: Int, init: _LayerManager.() -> Unit) : Drawable {
    return layer {
        roundRect {
            padding = strokeWidth.toInt()
            this.radius = radius
            this.color = strokeColor
        }
        roundRect {
            this.radius = if (radius >= strokeWidth) radius - strokeWidth else 0f
            this.color = color
        }
        init()
    }
}
/*----------------------------*/




