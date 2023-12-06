package com.dimovsoft.shutterfly.util

import android.content.Context
import androidx.window.layout.WindowMetricsCalculator
import com.dimovsoft.shutterfly.ui.components.movies_list.COLUMNS

/**
 * Looking for best image size to retrieve from network according to screen width
 */
fun bestImageSize(context: Context, sizes: List<String>): String {

	// Calculate width of column
	val columnWidth = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(context).bounds.width() / COLUMNS

	// Calculate width server supports
	val dpiList = sizes.mapNotNull { it.substring(1).toIntOrNull() } // stripping 'w'. Now the list contains numeric widths: 92, 154, 185 etc...

	// Choose best appropriate width (first who's bigger the needed)
	val bestDpi = dpiList.find { it > columnWidth } ?: return sizes.last()

	return "w$bestDpi"
}
