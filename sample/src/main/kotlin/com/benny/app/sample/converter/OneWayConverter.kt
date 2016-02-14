package com.benny.app.sample.converter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import com.benny.app.sample.R
import com.benny.library.kbinding.common.roundRect
import com.benny.library.kbinding.converter.OneWayConverter
import org.jetbrains.anko.dip
import java.text.DecimalFormat

/**
 * Created by benny on 12/17/15.
 */

class StockColorConverter(val defaultColor: Int = Color.WHITE) : OneWayConverter<Int> {
    override fun convert(source: Any?): Int {
        if(source !is Number) return defaultColor

        val change = source.toFloat()
        return when {
            change > 0 -> Color.parseColor("#e74524")
            change < 0 -> Color.parseColor("#0f9920")
            else -> defaultColor
        }
    }
}

class StockPriceConverter : OneWayConverter<CharSequence> {
    override fun convert(source: Any?): CharSequence {
        if(source !is Number) return ""

        return DecimalFormat("0.00").format(source.toFloat())
    }
}

class StockPriceChangeConverter(val positiveSign: Boolean = true) : OneWayConverter<CharSequence> {
    override fun convert(source: Any?): CharSequence {
        if(source !is Number) return ""

        return if(positiveSign) DecimalFormat("+0.00;-0.00").format(source.toFloat()) else DecimalFormat("0.00;-0.00").format(source.toFloat())
    }
}

class StockPriceChangePercentageConverter(val positiveSign: Boolean = true) : OneWayConverter<CharSequence> {
    override fun convert(source: Any?): CharSequence {
        if(source !is Number) return ""

        return if(positiveSign) DecimalFormat("+0.00;-0.00").format(source.toFloat()) + "%" else DecimalFormat("0.00;-0.00").format(source.toFloat()) + "%"
    }
}

class TagBackgroundConverter(val context: Context) : OneWayConverter<Drawable> {
    override fun convert(source: Any?): Drawable {
        return when(source.toString()) {
            "持有" -> roundRect { radius = context.dip(2).toFloat(); color = context.resources.getColor(R.color.color_red) }
            else -> roundRect { radius = context.dip(2).toFloat(); color = context.resources.getColor(R.color.color_blue) }
        }
    }
}