package jp.ac.titech.itpro.sdl.resist_kotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class RotationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val TAG: String = RotationView::class.java.simpleName

    private var direction: Double = 0.0

    private val paint: Paint = Paint()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w: Int = width
        val h: Int = height
        val cx: Int = w / 2
        val cy: Int = h / 2
        val r: Float = Math.min(cx, cy) - 20.0f
        paint.color = Color.LTGRAY
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), r, paint)
        paint.color = Color.BLUE
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), 20.0f, paint)
        paint.strokeWidth = 10.0f
        val x: Double = (cx + r * Math.cos(direction))
        val y: Double = (cy + r * Math.sin(direction))
        canvas.drawLine(cx.toFloat(), cy.toFloat(), x.toFloat(), y.toFloat(), paint)
    }

    fun setDirection(th: Double) {
        direction = th - Math.PI / 2
        this.getRotation()
        invalidate()
    }
}