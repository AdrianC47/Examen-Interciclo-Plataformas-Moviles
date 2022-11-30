package ec.edu.ups.examenPeliculas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment


class   CustomDialogFragment():DialogFragment (){
    lateinit var dataBase :DataBase


    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        var rootView: View = inflater.inflate(R.layout.popup,container,false)
        dataBase = context?.let { DataBase(it) }!!
        rootView.findViewById<View>(R.id.BtnCancelar).setOnClickListener{
            dismiss()
        }
        rootView.findViewById<View>(R.id.BtnSubir).setOnClickListener{
            val data = arguments
            var usuario :Int = -1
            var pelicula = ""
            var bandera = ""
            var imdbID = ""
            var seleccionado :RadioGroup = rootView.findViewById(R.id.ratingRadioGroup)
            val selectedId =seleccionado.checkedRadioButtonId
            val radio = rootView.findViewById<RadioButton>(selectedId)
            val idx = seleccionado.indexOfChild(radio)
            val texto = radio.getText().toString()
            if (data != null) {
                usuario = data.getInt("usuario")
                pelicula = data.getString("pelicula").toString()
                bandera = data.getString("bandera").toString()
                imdbID = data.getString("imdbID").toString()

            }
            val p = Pelicula(texto,usuario,imdbID)
            val buscarPeliculaBandera = dataBase.buscarPelicula(p)
            println("retorno " +buscarPeliculaBandera)
            if (buscarPeliculaBandera) {
                dataBase.addPelicula(p)
                Toast.makeText(context,"Calificaste: $texto", Toast.LENGTH_LONG).show()
            } else {
                dataBase.actualizarPelicula(p)
                Toast.makeText(context,"Calificacion Actualizada a : $texto", Toast.LENGTH_LONG).show()
            }
            val updateIntent = Intent(activity, Inicio::class.java)
            var extras: Bundle? = Bundle()
            if (extras != null) {
                extras.putInt("idUser", usuario)
                extras.putString("pelicula",pelicula.toString())
                extras.putString("bandera","2")
                updateIntent.putExtras(extras)
            };

            startActivity(updateIntent)
        }

        return rootView
        dismiss()
    }
}