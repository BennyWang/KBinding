package com.benny.app.sample.converter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import com.benny.app.sample.R
import com.benny.library.neobinding.converter.OneWayConverter
import com.benny.library.neobinding.drawable.borderRoundRect
import com.benny.library.neobinding.drawable.color
import com.benny.library.neobinding.drawable.roundRect
import org.jetbrains.anko.dip
import java.text.DecimalFormat

/**
 * Created by benny on 12/17/15.
 */

class StockColorConverter(val defaultColor: Int) : OneWayConverter<Int> {
    override fun convert(source: Any?): Int {
        if(source == null || source !is Number) return defaultColor

        val change = source.toFloat()
        return when {
            change > 0 -> Color.parseColor("#e74524")
            change < 0 -> Color.parseColor("#0f9920")
            else -> defaultColor
        }
    }
}

class StockPriceConverter : OneWayConverter<String> {
    override fun convert(source: Any?): String {
        if(source == null || source !is Number) return ""

        return DecimalFormat("0.00").format(source.toFloat())
    }
}

class StockPriceChangeConverter(val positiveSign: Boolean = true) : OneWayConverter<String> {
    override fun convert(source: Any?): String {
        if(source == null || source !is Number) return ""

        return if(positiveSign) DecimalFormat("+0.00;-0.00").format(source.toFloat()) else DecimalFormat("0.00;-0.00").format(source.toFloat())
    }
}

class StockPriceChangePercentageConverter(val positiveSign: Boolean = true) : OneWayConverter<String> {
    override fun convert(source: Any?): String {
        if(source == null || source !is Number) return ""

        return if(positiveSign) DecimalFormat("+0.00;-0.00").format(source.toFloat()) + "%" else DecimalFormat("0.00;-0.00").format(source.toFloat()) + "%"
    }
}

class TagBackgroundConverter(val context: Context) : OneWayConverter<Drawable> {
    override fun convert(source: Any?): Drawable {
        if(source == null) return context.color { color = Color.TRANSPARENT }

        return when(source.toString()) {
            "持有" -> context.roundRect{ radius = context.dip(2).toFloat(); color = context.resources.getColor(R.color.color_red) }
            else -> context.roundRect{ radius = context.dip(2).toFloat(); color = context.resources.getColor(R.color.color_blue) }
        }
    }
}