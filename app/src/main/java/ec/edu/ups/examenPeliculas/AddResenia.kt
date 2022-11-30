package ec.edu.ups.examenPeliculas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class AddResenia : AppCompatActivity() {
    lateinit var dataBase: DataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_resenia)
        dataBase = DataBase(this)
        val idUsuario = intent.getIntExtra("idUser", 0)
        val idPeliculaFav= intent.getIntExtra("idPeliculaFav",0)
        val imdbPelicula = intent.getStringExtra("ID")
        val txtResenia: TextView = findViewById(R.id.txtCrearResenia)
        val btnCrearResenia: Button = findViewById(R.id.btnCrearReseña)
        val btnComeback:ImageButton = findViewById(R.id.btnRegresarenResAdd)
        btnCrearResenia.setOnClickListener(View.OnClickListener {
            if (txtResenia.getText().toString() != "") {
                val r = ReseniaModel(idPeliculaFav,txtResenia.getText().toString(),idUsuario,imdbPelicula.toString())
                val buscarReseniaBandera = dataBase.buscarResenia(r)
                if (buscarReseniaBandera){
                    dataBase.addResenia(r)
                    Toast.makeText(applicationContext, "Reseña Creada", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                } else {
                    dataBase.actualizarResenia(r)
                    Toast.makeText(applicationContext, "Reseña Actualizada", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }

            } else {
                val toast = Toast.makeText(
                    applicationContext,
                    "Campos Vacios!! Reseña no Creada",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        })
        btnComeback.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })


    }
}