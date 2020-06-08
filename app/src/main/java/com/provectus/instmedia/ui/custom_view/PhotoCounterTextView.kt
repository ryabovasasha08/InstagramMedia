package com.provectus.instmedia.ui.custom_view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.provectus.instmedia.R

class PhotoCounterTextView : AppCompatTextView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setText()
    }

    var photoTotalAmount: Int = 1
        set(value) {
            field = value
            setText()
        }

    var currentPosition: Int = 1
        set(value) {
            field = value
            setText()
        }

    private fun setText() {
        val counterText = context.resources.getString(R.string.photo_counter, currentPosition.toString(), photoTotalAmount.toString())
        super.setText(counterText)
    }

}