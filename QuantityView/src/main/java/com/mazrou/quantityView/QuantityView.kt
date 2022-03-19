package com.mazrou.quantityView

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.quantity_view.view.*

class QuantityView : FrameLayout {


    /**
     * Constructor.
     *
     * @param context the context.
     */
    constructor(context: Context) : super(context) {
        obtainStyledAttributes(context, null, 0)
        init()
    }

    /**
     * Constructor.
     *
     * @param context the context.
     * @param attrs   the attributes from the layout.
     */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        obtainStyledAttributes(context, attrs, 0)
        init()
    }

    /**
     * Constructor.
     *
     * @param context      the context.
     * @param attrs        the attributes from the layout.
     * @param defStyleAttr the attributes from the default style.
     */
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        obtainStyledAttributes(context, attrs, defStyleAttr)
        init()
    }
    private var minQuantity = 0
    private var maxQuantity = 0
    private var startQuantity = 0
    private var currentQuantity = 0
    private var deltaQuantity = 0
    private var textColorRes = 0
    private var textSize = 0f
    private var enable = true


    private var onQuantityChanged : OnQuantityChanged? = null

    private fun obtainStyledAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.QuantityView,
                defStyleAttr,
                0
            )
            minQuantity = typedArray.getInteger(R.styleable.QuantityView_qt_min_value, 0)
            maxQuantity = typedArray.getInteger(R.styleable.QuantityView_qt_max_value, 100)
            startQuantity = typedArray.getInteger(R.styleable.QuantityView_qt_start_from, 0)
            deltaQuantity = typedArray.getInteger(R.styleable.QuantityView_qt_delta_value, 1)
            textColorRes = typedArray.getResourceId(
                R.styleable.QuantityView_qt_color,
                R.color.primaryColor
            )
            textSize = typedArray.getDimension(R.styleable.QuantityView_qt_textSize, 18f)
            enable = typedArray.getBoolean(R.styleable.QuantityView_qt_enable, true)
            return
        }
        minQuantity = 0
        maxQuantity = 100
        startQuantity = 0
        deltaQuantity = 1
        textColorRes = R.color.primaryColor
        textSize = 18f
        enable = true
    }

    private fun init() {
        inflate(context, R.layout.quantity_view, this)
        setQuantity(startQuantity)
        setEnable(enable)
        minus_btn.setOnClickListener {
            setQuantity(true)
        }
        plus_btn.setOnClickListener {
            setQuantity(false)
        }
        value_text.textSize = textSize
    }

    private fun setEnable(enable : Boolean){
        modifyViewClickable(isEnabled = enable,isSubtract = true)
        modifyViewClickable(isEnabled = enable,isSubtract = false)
        if (enable){
            value_text.setTextColor(ContextCompat.getColor(context,R.color.primaryColor))
        }else {
            value_text.setTextColor(ContextCompat.getColor(context,R.color.secondaryColor))
        }
    }

    private fun setQuantity(isSubtract: Boolean) {
        currentQuantity =
            if (isSubtract) subtractQuantity(currentQuantity) else addQuantity(currentQuantity)
        value_text.text = currentQuantity.toString()
        onQuantityChanged?.onQuantityChangedListener(currentQuantity)
    }

    private fun subtractQuantity(currentQuantity: Int): Int {
        var currentQuantityLocal = currentQuantity
        if (currentQuantity - deltaQuantity <= minQuantity) {
            currentQuantityLocal = minQuantity
            modifyViewClickable(isEnabled = false, isSubtract = true)
        } else {
            currentQuantityLocal -= deltaQuantity
            modifyViewClickable(isEnabled = true, isSubtract = false)
        }
        return currentQuantityLocal
    }

    private fun addQuantity(currentQuantity: Int): Int {
        var currentQuantityLocal = currentQuantity
        if (currentQuantityLocal + deltaQuantity >= maxQuantity) {
            currentQuantityLocal = maxQuantity
            modifyViewClickable(isEnabled = false, isSubtract = false)
        } else {
            currentQuantityLocal += deltaQuantity
            modifyViewClickable(isEnabled = true, isSubtract = true)
        }
        return currentQuantityLocal
    }

    private fun modifyViewClickable(isEnabled: Boolean, isSubtract: Boolean) {
        if (isSubtract) {
            minus_btn.isEnabled = isEnabled
            if (isEnabled)
                minus_btn.background = ContextCompat.getDrawable(context , R.drawable.ic_minus_enable)
            else
                minus_btn.background = ContextCompat.getDrawable(context , R.drawable.ic_minus_disable)
            return
        }
        plus_btn.isEnabled = isEnabled
        if (isEnabled)
            plus_btn.background = ContextCompat.getDrawable(context , R.drawable.ic_plus_enable)
        else
            plus_btn.background = ContextCompat.getDrawable(context , R.drawable.ic_plus_disable)
    }


    /**
     * Method that gets the currently set value.
     *
     * @return the current value.
     */
    fun getQuantity(): Int {
        return currentQuantity
    }
    fun setQuantity(value : Int) {
        currentQuantity = when {
            value <= minQuantity -> {
                modifyViewClickable(isEnabled = false , isSubtract = true)
                minQuantity
            }
            value >= maxQuantity -> {
                modifyViewClickable(isEnabled = false , isSubtract = false)
                maxQuantity
            }
            else -> {
                value
            }
        }
        value_text.text = currentQuantity.toString()
    }

    interface OnQuantityChanged{
        fun onQuantityChangedListener(quantity: Int)
    }
}