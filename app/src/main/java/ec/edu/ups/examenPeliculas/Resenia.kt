package ec.edu.ups.examenPeliculas

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import java.net.URL
import kotlin.properties.Delegates

class Resenia : AppCompatActivity() {

    lateinit var dataBase: DataBase
    lateinit var usuarioInicio: Usuario
    lateinit var txtResultadoFav: TextView
    lateinit var txtAnioFav: TextView
    lateinit var txtReseniaRecuperada: TextView
    lateinit var btnShare: ImageButton
    lateinit var btnDelete: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resenia)
        dataBase = DataBase(this)
        val layoutRes: LinearLayout = findViewById(R.id.LinearPrincipalRes)
        val idUsuario = intent.getIntExtra("idUser", 0)
        usuarioInicio = dataBase.getUsuario(idUsuario);
        var btnRegresarenFav: ImageButton = findViewById(R.id.btnRegresarenRes)
        btnRegresarenFav.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
        val bunny: TextView = findViewById(R.id.txtBannerUsuario4)
        bunny.text = usuarioInicio.nickname
        getResenias(idUsuario, layoutRes)
    }

    fun getResenias(imdbID: Int, layout: LinearLayout) {
        usuarioInicio = dataBase.getUsuario(imdbID);
        var lista: List<ReseniaModel> = dataBase.obtenerResenias(usuarioInicio.id)
        for (i in lista.indices) {
            //Recupero los valores
            val queue = Volley.newRequestQueue(this)

            val url = "https://www.omdbapi.com/?apikey=a928e8a3&i=" + lista[i].imdbID + "&plot=full"
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url,
                null,
                { response ->
                    if (response.getString("Response") == "True") {
                        Log.i("Data", response.toString())
                        val view: View = layoutInflater.inflate(R.layout.activity_resdetalle, null)
                        txtResultadoFav = view.findViewById(R.id.txtResultadoRes)
                        txtAnioFav = view.findViewById(R.id.txtAnioRes)
                        btnShare = view.findViewById(R.id.btnCompartir)
                        btnDelete = view.findViewById(R.id.btnDeleteRes)
                        txtReseniaRecuperada = view.findViewById(R.id.txtReseniaRecup)
                        println(lista[i].peliculaId.toString() + ">k")
                        val valoracion = dataBase.getPelicula(lista[i].peliculaId)
                        txtReseniaRecuperada.setText(lista[i].resenia)
                        println(lista[i].imdbID + " hola")
                        txtResultadoFav.setText(response.getString("Title"))
                        val titulo = response.getString("Title")
                        val Anio = response.getString("Year")
                        val resenia =lista[i].resenia
                        val poster = response.getString("Poster")
                        txtAnioFav.setText(response.getString("Year") + " - " + valoracion.valoracion)
                        DownloadImageFromInternet(view.findViewById(R.id.imgPosterRes)).execute(
                            response.getString(
                                "Poster"
                            )
                        )

                        btnShare.setVisibility(View.VISIBLE);
                        btnDelete.setVisibility(View.VISIBLE);
                        btnShare.setOnClickListener(View.OnClickListener {
                            val intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "!!PeliTech!!"+ "\n"
                                            + "Pelicula:" + "\n" + "Titulo: " + titulo + "\n" + "Año: " + Anio + "\n" +
                                            "Valoracion: " + valoracion.valoracion + "\n"+ "Reseña: " + resenia + "\n" +"Portada: " +poster
                                )
                                type = "text/plaint"
                            }
                            val share = Intent.createChooser(intent, null)
                            startActivity(share)
                        })
                        btnDelete.setOnClickListener(View.OnClickListener {
                            println("ID " + lista[i].id + "Id pelicula u " + lista[i].imdbID)
                            dataBase.eliminarResenia(lista[i].id)

                            finish();
                            startActivity(getIntent());
                            Toast.makeText(
                                applicationContext,
                                "Reseña Eliminada con Exito",
                                Toast.LENGTH_LONG
                            ).show()
                        })
                        layout.addView(view)

                    } else {
                        println("No retorno")
                    }
                },
                { error ->
                    Log.e("Error", error.toString())
                    Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_LONG).show()
                }
            )
            queue.add(jsonObjectRequest)
        }
    }

    private inner class DownloadImageFromInternet(var imageView: ImageView) :
        AsyncTask<String, Void, Bitmap?>() {
        init {
            //Toast.makeText(applicationContext, "Estamos cargando los datos...",     Toast.LENGTH_SHORT).show()
        }

        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            var image: Bitmap? = null
            try {
                val `in` = URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
            } catch (e: Exception) {
                Log.e("Error Message", e.message.toString())
                e.printStackTrace()
            }
            return image
        }

        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }

    //Para Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.game_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.itmReiniciar -> {
                newGame()
                true
            }
            R.id.itmSalir -> {
                exit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun newGame() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun exit() {
        this.finishAffinity()
    }
}