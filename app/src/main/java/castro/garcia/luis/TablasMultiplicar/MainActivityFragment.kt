package castro.garcia.luis.TablasMultiplicar

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.*
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : Fragment(), SeekBar.OnSeekBarChangeListener, TextToSpeech.OnInitListener, AdapterView.OnItemClickListener{
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale("es_MX"))
            if(result != TextToSpeech.LANG_NOT_SUPPORTED || result != TextToSpeech.LANG_MISSING_DATA){

            }else{
                Toast.makeText(context,"no soportado",Toast.LENGTH_SHORT).show()
            }
        }
    }

    var progreso:Int? = null
    var adaptador: ArrayAdapter<String>? = null
    var listView: ListView? = null

    var elementos = arrayOfNulls<String>(11)

    var tts : TextToSpeech? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val vistaRaiz = inflater.inflate(R.layout.fragment_main, container, false)

        val seek = vistaRaiz.findViewById<SeekBar>(R.id.seekBar) as SeekBar

        progreso = seek.progress //3

        tts = TextToSpeech(context,this)
        Log.i("Languages", Locale.getAvailableLocales().toString())

        seek.setOnSeekBarChangeListener(this)

        listView = vistaRaiz.findViewById<ListView>(R.id.listView) as ListView

        calcularTablas(progreso!!)

        return vistaRaiz
    }
    fun calcularTablas(progreso: Int) {
        for (i in 0..10) {
            val text = "$progreso * $i = ${progreso * i}"
            elementos.set(i, text)
        }
        adaptador = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, elementos)
        listView!!.adapter = adaptador
        listView!!.setOnItemClickListener(this)
    }

    override fun onProgressChanged(seekBar: SeekBar?, p1: Int, p2: Boolean) {
        calcularTablas(p1)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var texto = elementos.get(position)!!.replace("*"," por ")
        Toast.makeText(context,"presionado",Toast.LENGTH_SHORT).show()
        tts!!.speak(texto,TextToSpeech.QUEUE_FLUSH,null,null)
    }

    override fun onDestroy() {
        if (tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}
