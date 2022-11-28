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

class Favoritas : AppCompatActivity() {
    lateinit var dataBase: DataBase
    lateinit var usuarioInicio: Usuario
    lateinit var txtResultadoFav: TextView
    lateinit var txtAnioFav: TextView
    lateinit var imgPosterFav: ImageView
    lateinit var btnAdd: ImageButton
    lateinit var btnDelete: ImageButton

    var tamanio by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritas)
        dataBase = DataBase(this)
        val layoutFav: LinearLayout = findViewById(R.id.LinearPrincipalFav)
//        rellenarInfo()
        val idUsuario = intent.getIntExtra("idUser", 0)

        getMoviesInformation(idUsuario, layoutFav)

    }

    fun getMoviesInformation(imdbID: Int, layout: LinearLayout) {


        usuarioInicio = dataBase.getUsuario(imdbID);
        var lista: List<Pelicula> = dataBase.obtenerPeliculasFav(usuarioInicio.id)
        println("imdb " + imdbID.toString())
        for (i in lista.indices) {
            //Recupero los valores
            val queue = Volley.newRequestQueue(this)

            val url = "https://www.omdbapi.com/?apikey=a928e8a3&i=" + lista[i].imdbID + "&plot=full"
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url,
                null,
                { response ->
                    if (response.getString("Response") == "True") {
                        println("dsfdfssdfsdf")


                        Log.i("Data", response.toString())
                        val view: View = layoutInflater.inflate(R.layout.activity_fav, null)
                        txtResultadoFav = view.findViewById(R.id.txtResultadoFav)
                        txtAnioFav = view.findViewById(R.id.txtAnioFav)
                        btnAdd = view.findViewById(R.id.btnAdd)
                        btnDelete = view.findViewById(R.id.btnDelete)
//                    for (i in lista.indices){

                        println(lista[i].imdbID + " hola")
                        txtResultadoFav.setText(response.getString("Title"))
                        txtAnioFav.setText(response.getString("Year")+" - "+lista[i].valoracion)
                        DownloadImageFromInternet(view.findViewById(R.id.imgPosterFav)).execute(
                            response.getString(
                                "Poster"
                            )
                        )
                        btnAdd = view.findViewById(R.id.btnAdd)
                        btnDelete = view.findViewById(R.id.btnDelete)
                        btnAdd.setVisibility(View.VISIBLE);
                        btnDelete.setVisibility(View.VISIBLE);
                        layout.addView(view)
//                    }

                        tamanio = lista.size
                        println("tamaño de lista " + tamanio)
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