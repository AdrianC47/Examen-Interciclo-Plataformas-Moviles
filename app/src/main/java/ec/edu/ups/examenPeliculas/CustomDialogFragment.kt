package ec.edu.ups.examenPeliculas

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment


class   CustomDialogFragment:DialogFragment (){
    var interfaz: CustomDialogFragment_Interface? = null
    var valoracion : String = ":)";
    var retorno :String = ">";
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        var rootView: View = inflater.inflate(R.layout.popup,container,false)

        rootView.findViewById<View>(R.id.BtnCancelar).setOnClickListener{
            dismiss()
        }
        rootView.findViewById<View>(R.id.BtnSubir).setOnClickListener{

            var seleccionado :RadioGroup = rootView.findViewById(R.id.ratingRadioGroup)
            seleccionado.checkedRadioButtonId
            val selectedId =seleccionado.checkedRadioButtonId
            val radio = rootView.findViewById<RadioButton>(selectedId)
            val idx = seleccionado.indexOfChild(radio)
            valoracion = idx.toString()
            println("Valoracion en Custom" +valoracion)
            interfaz?.mandarTexto(valoracion)
            Toast.makeText(context,"Calificaste: ${idx.toString()}", Toast.LENGTH_LONG).show()

            dismiss()

        }
//
        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        interfaz = context as CustomDialogFragment_Interface
    }
    interface CustomDialogFragment_Interface {
        fun mandarTexto(texto: String?)
    }
    fun obtenerCalificacion (): String {
        println("en metodo "+retorno)
        return retorno
    }
}