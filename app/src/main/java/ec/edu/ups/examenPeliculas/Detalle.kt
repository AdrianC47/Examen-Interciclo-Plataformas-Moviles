package ec.edu.ups.examenPeliculas

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import java.net.URL

class Detalle : AppCompatActivity() {
    lateinit var dataBase :DataBase
    lateinit var usuarioInicio :Usuario
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)
        dataBase = DataBase(this)
        val codigoUsuario = intent.getIntExtra("idUser",0)
        usuarioInicio = dataBase.getUsuario(codigoUsuario);

        rellenarInfo()
        var botonComeBack: ImageButton = findViewById(R.id.btnRegresar)
        botonComeBack.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Inicio::class.java).apply {}
            startActivity(intent)
        })

    }


    fun rellenarInfo() {
        //Recupero los valores
        val titulo = intent.getStringExtra("ID")
        val bunny: TextView = findViewById(R.id.txtBannerUsuario2)
        bunny.text=usuarioInicio.nickname
        val todaInfo: TextView = findViewById(R.id.txtTodaInfo)
        val final: TextView = findViewById(R.id.txtFinal)
        val queue = Volley.newRequestQueue(this)
        val url = "https://www.omdbapi.com/?apikey=a928e8a3&i=" + titulo + "&plot=full"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,
            null,
            { response ->
                println(response.toString())
                val cadena =
                    "Title: " + (response.getString("Title")) + "\n" + "Year: " + (response.getString(
                        "Year"
                    )) + "\n" + "Rated: "+(response.getString("Rated")) + "\n" + "Released: "+(response.getString("Released")) + "\n" + "Runtime: "+(response.getString(
                        "Runtime"
                    )) + "\n" + "Genre: "+(response.getString("Genre")) + "\n" + "Director: "+(response.getString("Director")) + "\n" + "Writer: "+(response.getString(
                        "Writer"
                    )) + "\n" + "Actors: "+(response.getString("Actors")) + "\n" + "Plot: "+(response.getString("Plot")) + "\n" + "Language: "+(response.getString(
                        "Language"
                    )) + "\n" + "Country: "+(response.getString("Country")) + "\n" + "Awards: "+(response.getString("Awards")) + "\n" + "Poster: "+(response.getString(
                        "Poster"
                    )) + "\n" + "Ratings: "+(response.getString("Ratings")) + "\n" + "Metascore: "+(response.getString("Metascore")) + "\n" + "imdbRating: "+(response.getString(
                        "imdbRating"
                    )) + "\n" + "imdbVotes: "+(response.getString("imdbVotes")) + "\n" + "imdbID: "+(response.getString("imdbID")) + "\n" + "Type: "+(response.getString(
                        "Type"
                    )) + "\n" + "DVD: "+(response.getString("DVD")) + "\n" + "BoxOffice: "+(response.getString("BoxOffice")) + "\n" + "Production: "+(response.getString(
                        "Production"
                    )) + "\n" + "Website: "+(response.getString("Website")) + "\n" + "Response: "+(response.getString("Response"))

                DownloadImageFromInternet(findViewById(R.id.imgFinal)).execute(
                    response.getString("Poster")
                )
                final.text = response.getString("Title")
                todaInfo.text = cadena
            },
            { error ->
                Log.e("Error", error.toString())
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)
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