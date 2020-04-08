package ir.alirezaiyan.touchword

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.BreakIterator
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sampleText = getString(R.string.sampleText)

        textView.movementMethod = LinkMovementMethod.getInstance()

        val htmlText = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(sampleText, Html.FROM_HTML_MODE_LEGACY);
        } else {
            Html.fromHtml(sampleText);
        }

        textView.text = htmlText
        val rawText = textView.text.toString()
        val iterator = BreakIterator.getWordInstance(Locale.US)

        iterator.setText(rawText)

        var start = iterator.first()
        var end = iterator.next()

        while (end != BreakIterator.DONE) {
            val possibleWord = rawText.substring(start, end)
            if (Character.isLetterOrDigit(possibleWord[0])) {
                val clickSpan = getClickableSpan(possibleWord)
                (textView.text as Spannable).setSpan(clickSpan, start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            start = end
            end = iterator.next()
        }
    }

    private fun getClickableSpan(word: String): ClickableSpan? {
        return object : ClickableSpan() {
            override fun onClick(widget: View) {
                Log.d("tapped on:", word)
                Toast.makeText(widget.context, word, Toast.LENGTH_SHORT)
                        .show()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
                ds.isAntiAlias = true
            }
        }
    }
}
