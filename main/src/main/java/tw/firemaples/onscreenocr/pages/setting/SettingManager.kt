package tw.firemaples.onscreenocr.pages.setting

import android.content.Context
import androidx.preference.PreferenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tw.firemaples.onscreenocr.utils.Logger
import tw.firemaples.onscreenocr.utils.SamsungSpenInsertedReceiver
import tw.firemaples.onscreenocr.utils.Utils

object SettingManager {
    //    private const val PREF_USE_SIMPLE_STYLE = "pref_use_simple_style"
    private const val PREF_ENABLE_FADING_OUT_WHILE_IDLE = "pref_enable_fading_out_while_idle"
    private const val PREF_FADE_OUT_AFTER_SECONDS = "pref_fade_out_after_seconds"
    private const val PREF_OPAQUE_PERCENTAGE = "pref_opaque_percentage"
//    private const val PREF_TEXT_BLOCK_JOINER = "pref_text_block_joiner"

    private const val PREF_SAVE_LAST_SELECTION_AREA = "pref_save_last_selection_area"
    private const val PREF_EXIT_APP_WHILE_SPEN_INSERTED = "pref_exit_app_while_spen_inserted"

//    private val DEFAULT_JOINER = TextBlockJoiner.Space

    private val context: Context by lazy { Utils.context }
    private val logger: Logger = Logger(SettingManager::class)

    private val preferences by lazy { PreferenceManager.getDefaultSharedPreferences(context) }

//    val useSimpleStyle: Boolean
//        get() = preferences.getBoolean(PREF_USE_SIMPLE_STYLE, false)

    val enableFadingOutWhileIdle: Boolean
        get() = preferences.getBoolean(PREF_ENABLE_FADING_OUT_WHILE_IDLE, true)

    val timeoutToFadeOut: Long
        get() = preferences.getInt(PREF_FADE_OUT_AFTER_SECONDS, 5) * 1000L

    val opaquePercentageToFadeOut: Float
        get() = preferences.getInt(PREF_OPAQUE_PERCENTAGE, 20).toFloat() / 100f

//    val textBlockJoiner: TextBlockJoiner
//        get() =
//            try {
//                TextBlockJoiner.valueOf(
//                    preferences.getString(PREF_TEXT_BLOCK_JOINER, DEFAULT_JOINER.name)
//                        ?: DEFAULT_JOINER.name
//                )
//            } catch (e: Exception) {
//                logger.warn(t = e)
//                DEFAULT_JOINER
//            }
//
//    enum class TextBlockJoiner(val joiner: String) {
//        LineBreaker("\n"),
//        Space(" "),
//        None(""),
//    }

    val saveLastSelectionArea: Boolean
        get() = preferences.getBoolean(PREF_SAVE_LAST_SELECTION_AREA, true)

    val exitAppWhileSPenInserted: Boolean
        get() = preferences.getBoolean(PREF_EXIT_APP_WHILE_SPEN_INSERTED, true)

    init {
        preferences.registerOnSharedPreferenceChangeListener { _, key ->
            if (key == PREF_EXIT_APP_WHILE_SPEN_INSERTED) {
                CoroutineScope(Dispatchers.Main).launch {
                    if (exitAppWhileSPenInserted) {
                        SamsungSpenInsertedReceiver.start()
                    } else {
                        SamsungSpenInsertedReceiver.stop()
                    }
                }
            }
        }
    }
}