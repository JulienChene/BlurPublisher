package io.snaker.app.blurpublisher

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class MultiColorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var color1 = Random.nextInt(0, 255)
    var color2 = Random.nextInt(0, 255)
    var color3 = Random.nextInt(0, 255)
    var disposable: Disposable? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        disposable = Observable.interval(100, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                nextColor()
                invalidate()
            }, {
                Timber.e(it)
            }, {

            })
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        if (disposable?.isDisposed == false) {
            Timber.e("Disposing of stream")
            disposable?.dispose()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawARGB(255, color1 % 256, color2 % 256, color3 % 256)
    }

    private fun nextColor() {
        color1 += 10
        color2 += 10
        color3 += 10
    }
}
