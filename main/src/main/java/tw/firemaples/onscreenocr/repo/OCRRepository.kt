package tw.firemaples.onscreenocr.repo

import android.content.Context
import androidx.lifecycle.asFlow
import com.chibatching.kotpref.livedata.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import tw.firemaples.onscreenocr.pref.AppPref
import tw.firemaples.onscreenocr.recognition.RecognitionLanguage
import tw.firemaples.onscreenocr.recognition.TextRecognizer
import tw.firemaples.onscreenocr.utils.Logger
import tw.firemaples.onscreenocr.utils.Utils

class OCRRepository {
    private val logger: Logger by lazy { Logger(OCRRepository::class) }
    private val context: Context by lazy { Utils.context }

    val selectedOCRLangFlow: Flow<String>
        get() = AppPref.asLiveData(AppPref::selectedOCRLang).asFlow()
            .flowOn(Dispatchers.Default)

    fun getAllOCRLanguages(): Flow<List<RecognitionLanguage>> = flow {
//        val res = context.resources
//        val codeList = res.getStringArray(R.array.ocr_langCode_iso6391)
//        val ocrCodeList = res.getStringArray(R.array.ocr_langCode_iso6393)
//        val displayNameList = res.getStringArray(R.array.ocr_langName)
//
//        val selectedLang = AppPref.selectedOCRLang
//
//        val result = (codeList.indices).map {
//            val code = codeList[it]
//            val ocrCode = ocrCodeList[it]
//            val displayName = displayNameList[it]
//
//            OCRLanguage(code, ocrCode, displayName, code == selectedLang)
//        }

        val supportedLangList = TextRecognizer.getRecognizer().supportedLanguages()

        emit(supportedLangList)
    }.flowOn(Dispatchers.Default)

//    fun getSelectedOCRLanguage(): Flow<String> = flow {
//        emit(AppPref.selectedOCRLang)
//    }.flowOn(Dispatchers.Default)

    suspend fun setSelectedOCRLanguage(langCode: String) {
        withContext(Dispatchers.Default) {
            AppPref.selectedOCRLang = langCode
        }
    }
}

//data class OCRLanguage(
//    val code: String,
//    val ocrCode: String,
//    val displayName: String,
//    val selected: Boolean,
//)