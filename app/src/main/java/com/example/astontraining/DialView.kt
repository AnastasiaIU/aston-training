package com.example.astontraining

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.time.LocalTime
import java.time.temporal.ChronoField
import kotlin.math.min

/**
 * Custom analog clock [View].
 */
class DialView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Paint style
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.WHITE
    }

    // Dial radius
    private var dialRadius = 0f

    // Half of the DialView width and height
    private var halfWidth = 0f
    private var halfHeight = 0f

    // Dial's outer circle width and an half of it
    private val dialWidth = resources.displayMetrics.density * 5
    private val dialWidthHalf = dialWidth / 2

    // Hour marker width and length
    private val hourMarkerWidth = dialWidth * 1.2f
    private var hourMarkerLength = 0f

    // Hands length and color
    private val handHourWidth: Float
    private val handMinuteWidth: Float
    private val handSecondWidth: Float
    private val handHourLength: Float
    private val handMinuteLength: Float
    private val handSecondLength: Float
    private val handHourColor: Int
    private val handMinuteColor: Int
    private val handSecondColor: Int

    init {

        // TypedArray with the attributes from attrs
        val attrsArray = context.obtainStyledAttributes(attrs, R.styleable.DialView)

        // Get the attributes
        handHourWidth = attrsArray.getDimension(R.styleable.DialView_hand_hour_width, 0f)
        handHourLength = attrsArray.getDimension(R.styleable.DialView_hand_hour_length, 0f)
        handHourColor = attrsArray.getColor(R.styleable.DialView_hand_hour_color, 0)
        handMinuteWidth = attrsArray.getDimension(R.styleable.DialView_hand_minute_width, 0f)
        handMinuteLength = attrsArray.getDimension(R.styleable.DialView_hand_minute_length, 0f)
        handMinuteColor = attrsArray.getColor(R.styleable.DialView_hand_minute_color, 0)
        handSecondWidth = attrsArray.getDimension(R.styleable.DialView_hand_second_width, 0f)
        handSecondLength = attrsArray.getDimension(R.styleable.DialView_hand_second_length, 0f)
        handSecondColor = attrsArray.getColor(R.styleable.DialView_hand_second_color, 0)

        // Recycle the TypedArray after use
        attrsArray.recycle()
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {

        // Calculate the dial radius from the smaller of the width and height
        dialRadius = (min(width, height) / 2 * 0.8).toFloat()

        // Set a hour marker length
        hourMarkerLength = dialRadius * 0.1f

        // Get half of the DialView width and height
        halfWidth = width / 2f
        halfHeight = height / 2f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // Get current time
        val currentTime = LocalTime.now()
        val second = currentTime.getLong(ChronoField.SECOND_OF_MINUTE)
        val minute = currentTime.getLong(ChronoField.MINUTE_OF_HOUR)
        var hour = currentTime.getLong(ChronoField.HOUR_OF_DAY)
        if (hour > 12) hour -= 12

        // Draw the dial background
        paint.apply {
            style = Paint.Style.FILL
            color = Color.WHITE
        }
        canvas?.drawCircle(halfWidth, halfHeight, dialRadius * 1.1f, paint)

        // Draw the dial
        paint.apply {
            style = Paint.Style.STROKE
            color = Color.BLACK
            strokeWidth = dialWidth
        }
        canvas?.drawCircle(halfWidth, halfHeight, dialRadius, paint)

        // Draw hour markers
        paint.apply {
            style = Paint.Style.FILL
            strokeWidth = hourMarkerWidth
        }
        canvas?.save()
        for (i in 0..55 step 5) {
            canvas?.drawLine(
                halfWidth,
                halfHeight - dialRadius + dialWidthHalf,
                halfWidth,
                halfHeight - dialRadius + dialWidthHalf + hourMarkerLength,
                paint
            )
            canvas?.rotate(30f, halfWidth, halfHeight)
        }
        canvas?.restore()

        // Draw hour hand
        paint.apply {
            color = handHourColor
            strokeWidth = handHourWidth
        }
        canvas?.save()
        canvas?.rotate(30f * hour, halfWidth, halfHeight)
        canvas?.drawLine(
            halfWidth,
            halfHeight * 1.07f,
            halfWidth,
            halfHeight - handHourLength,
            paint
        )
        canvas?.restore()

        // Draw minute hand
        paint.apply {
            color = handMinuteColor
            strokeWidth = handMinuteWidth
        }
        canvas?.save()
        canvas?.rotate(6f * minute, halfWidth, halfHeight)
        canvas?.drawLine(
            halfWidth,
            halfHeight * 1.07f,
            halfWidth,
            halfHeight - handMinuteLength,
            paint
        )
        canvas?.restore()

        // Draw second hand
        paint.apply {
            color = handSecondColor
            strokeWidth = handSecondWidth
        }
        canvas?.save()
        canvas?.rotate(6f * second, halfWidth, halfHeight)
        canvas?.drawLine(
            halfWidth,
            halfHeight * 1.07f,
            halfWidth,
            halfHeight - handSecondLength,
            paint
        )
        canvas?.restore()

        postInvalidateDelayed(500)
    }
}