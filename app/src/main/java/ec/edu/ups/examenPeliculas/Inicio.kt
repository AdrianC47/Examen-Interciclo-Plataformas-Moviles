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
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class Inicio : AppCompatActivity(), CustomDialogFragment.CustomDialogFragment_Interface {
    private var contador: Int = 1

    lateinit var txtResultado: TextView
    lateinit var txtAnio: TextView
    lateinit var txtResultado2: TextView
    lateinit var txtAnio2: TextView
    lateinit var txtResultado3: TextView
    lateinit var txtAnio3: TextView
    lateinit var txtResultado4: TextView
    lateinit var txtAnio4: TextView
    lateinit var txtResultado5: TextView
    lateinit var txtAnio5: TextView
    lateinit var txtResultado6: TextView
    lateinit var txtAnio6: TextView
    lateinit var txtResultado7: TextView
    lateinit var txtAnio7: TextView
    lateinit var txtResultado8: TextView
    lateinit var txtAnio8: TextView
    lateinit var txtResultado9: TextView
    lateinit var txtAnio9: TextView
    lateinit var txtResultado10: TextView
    lateinit var txtAnio10: TextView
    lateinit var txtValoracion:TextView
    lateinit var btnDerecha: ImageButton
    lateinit var btmMore:ImageButton
    lateinit var btmMore2:ImageButton
    lateinit var btmMore3:ImageButton
    lateinit var btmMore4:ImageButton
    lateinit var btmMore5:ImageButton
    lateinit var btmMore6:ImageButton
    lateinit var btmMore7:ImageButton
    lateinit var btmMore8:ImageButton
    lateinit var btmMore9:ImageButton
    lateinit var btmMore10:ImageButton
    lateinit var btmFav:ImageButton
    lateinit var btmFav2:ImageButton
    lateinit var btmFav3:ImageButton
    lateinit var btmFav4:ImageButton
    lateinit var btmFav5:ImageButton
    lateinit var btmFav6:ImageButton
    lateinit var btmFav7:ImageButton
    lateinit var btmFav8:ImageButton
    lateinit var btmFav9:ImageButton
    var puntuacion:String = "inicializado"
    var  nuevo :String =""
    lateinit var btmFav10:ImageButton
    lateinit var dataBase :DataBase
    lateinit var usuarioInicio :Usuario
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        dataBase = DataBase(this)
        var btnBuscar: Button = findViewById(R.id.btnBuscar)
        val codigoUsuario = intent.getIntExtra("idUser",0)
        usuarioInicio = dataBase.getUsuario(codigoUsuario);
        var txtPelicula: TextView = findViewById(R.id.txtBuscar)
        var bannerUsuario: TextView  = findViewById(R.id.txtBannerUsuario)
        bannerUsuario.text = usuarioInicio.nickname+" "
        btnDerecha = findViewById(R.id.btnDerecha)
        var btnIzquierda: ImageButton = findViewById(R.id.btnRegresar)
        btnBuscar.setOnClickListener(View.OnClickListener {
            if (txtPelicula.getText().toString() == "") {
                val toast = Toast.makeText(
                    applicationContext,
                    "¡¡Ingrese un nombre!!",
                    Toast.LENGTH_SHORT

                )
                toast.show()
            } else {
                btnDerecha.setVisibility(View.VISIBLE);
                getMoviesInformation(txtPelicula.getText().toString(), contador)
            }
        })
        btnDerecha.setOnClickListener(View.OnClickListener {

            contador++
            txtResultado.text = ""
            txtAnio.text = ""
            var img: ImageView = findViewById(R.id.imgPoster)
            img.setImageResource(0)
            txtResultado2.text = ""
            txtAnio2.text = ""
            var img2: ImageView = findViewById(R.id.imgPoster2)
            img2.setImageResource(0)
            txtResultado3.text = ""
            txtAnio3.text = ""
            var img3: ImageView = findViewById(R.id.imgPoster3)
            img3.setImageResource(0)
            txtResultado4.text = ""
            txtAnio4.text = ""
            var img4: ImageView = findViewById(R.id.imgPoster4)
            img4.setImageResource(0)
            txtResultado5.text = ""
            txtAnio5.text = ""
            var img5: ImageView = findViewById(R.id.imgPoster5)
            img5.setImageResource(0)
            txtResultado6.text = ""
            txtAnio6.text = ""
            var img6: ImageView = findViewById(R.id.imgPoster6)
            img6.setImageResource(0)
            txtResultado7.text = ""
            txtAnio7.text = ""
            var img7: ImageView = findViewById(R.id.imgPoster7)
            img7.setImageResource(0)
            txtResultado8.text = ""
            txtAnio8.text = ""
            var img8: ImageView = findViewById(R.id.imgPoster8)
            img8.setImageResource(0)
            txtResultado9.text = ""
            txtAnio9.text = ""
            var img9: ImageView = findViewById(R.id.imgPoster9)
            img9.setImageResource(0)
            txtResultado10.text = ""
            txtAnio10.text = ""
            var img10: ImageView = findViewById(R.id.imgPoster10)
            img10.setImageResource(0)
            if (contador > 1) {
                btnIzquierda.setVisibility(View.VISIBLE);
            }
            getMoviesInformation(txtPelicula.getText().toString(), contador)

        })
        btnIzquierda.setOnClickListener(View.OnClickListener {
            if (contador == 1) {
                btnIzquierda.setVisibility(View.INVISIBLE);
                val toast = Toast.makeText(
                    applicationContext,
                    "¡¡Lista en Inicio!!",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            } else {
                btnIzquierda.setVisibility(View.VISIBLE);
                contador--
                getMoviesInformation(txtPelicula.getText().toString(), contador)
            }

        })


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

    override fun mandarTexto(texto: String?) {
        if (texto != null) {
            puntuacion = texto
            println("hola mundo "+puntuacion)
            txtValoracion = findViewById(R.id.txtValoracion)
            txtValoracion.text=puntuacion
            nuevo = puntuacion
        };
    }

    fun getMoviesInformation(search: String, page: Int) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://www.omdbapi.com/?apikey=a928e8a3&s=" + search + "&plot=full&page=" + page
        txtResultado = findViewById(R.id.txtResultado)
        txtAnio = findViewById(R.id.txtAnio)
        txtResultado2 = findViewById(R.id.txtResultado2)
        txtAnio2 = findViewById(R.id.txtAnio2)
        txtResultado3 = findViewById(R.id.txtResultado3)
        txtAnio3 = findViewById(R.id.txtAnio3)
        txtResultado4 = findViewById(R.id.txtResultado4)
        txtAnio4 = findViewById(R.id.txtAnio4)
        txtResultado5 = findViewById(R.id.txtResultado5)
        txtAnio5 = findViewById(R.id.txtAnio5)
        txtResultado6 = findViewById(R.id.txtResultado6)
        txtAnio6 = findViewById(R.id.txtAnio6)
        txtResultado7 = findViewById(R.id.txtResultado7)
        txtAnio7 = findViewById(R.id.txtAnio7)
        txtResultado8 = findViewById(R.id.txtResultado8)
        txtAnio8 = findViewById(R.id.txtAnio8)
        txtResultado9 = findViewById(R.id.txtResultado9)
        txtAnio9 = findViewById(R.id.txtAnio9)
        txtResultado10 = findViewById(R.id.txtResultado10)
        txtAnio10 = findViewById(R.id.txtAnio10)

        btmMore = findViewById(R.id.btnMore)
        btmMore2 = findViewById(R.id.btnMore2)
        btmMore3 = findViewById(R.id.btnMore3)
        btmMore4 = findViewById(R.id.btnMore4)
        btmMore5 = findViewById(R.id.btnMore5)
        btmMore6 = findViewById(R.id.btnMore6)
        btmMore7 = findViewById(R.id.btnMore7)
        btmMore8 = findViewById(R.id.btnMore8)
        btmMore9 = findViewById(R.id.btnMore9)
        btmMore10= findViewById(R.id.btnMore10)
        btmFav  = findViewById(R.id.btnFav)
        btmFav2 = findViewById(R.id.btnFav2)
        btmFav3 = findViewById(R.id.btnFav3)
        btmFav4 = findViewById(R.id.btnFav4)
        btmFav5 = findViewById(R.id.btnFav5)
        btmFav6 = findViewById(R.id.btnFav6)
        btmFav7 = findViewById(R.id.btnFav7)
        btmFav8 = findViewById(R.id.btnFav8)
        btmFav9 = findViewById(R.id.btnFav9)
        btmFav10= findViewById(R.id.btnFav10)
        var status: Int = 0

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url,
            null,
            { response ->
                if (response.getString("Response") == "True") {


                    btnDerecha.setVisibility(View.VISIBLE);
                    Log.i("Data", response.toString())
                    val movies = response.getJSONArray("Search")
                    val movie = movies.optJSONObject(0)
                    if (movie != null) {
                        DownloadImageFromInternet(findViewById(R.id.imgPoster)).execute(
                            movie.getString(
                                "Poster"
                            )
                        )
                        txtResultado.text = movie.getString("Title")
                        txtAnio.text = movie.getString("Year")
                        btmMore.setVisibility(View.VISIBLE);
                        btmFav.setVisibility(View.VISIBLE);
                        btmMore.setOnClickListener(View.OnClickListener {
                            val intent = Intent(this, Detalle::class.java);
                            var extras: Bundle? = Bundle()
                            if (extras != null) {
                                extras.putString("ID", movie.getString("imdbID"))
                                extras.putInt("idUser",usuarioInicio.id)
                                intent.putExtras(extras)
                            };
                            startActivity(intent)
                            })
                        btmFav.setOnClickListener(View.OnClickListener {
                            var dialog = CustomDialogFragment()
                            dialog.show(supportFragmentManager,"customDialog")
                            println(puntuacion+"recuperado"+ nuevo)



                        })
                    } else {
                        btmMore.setVisibility(View.INVISIBLE);
                        btmFav.setVisibility(View.INVISIBLE);
                    }
                    val movie2 = movies.optJSONObject(1)
                    if (movie2 != null) {
                        DownloadImageFromInternet(findViewById(R.id.imgPoster2)).execute(
                            movie2.getString(
                                "Poster"
                            )
                        )
                        txtResultado2.text = movie2.getString("Title")
                        txtAnio2.text = movie2.getString("Year")
                        btmMore2.setVisibility(View.VISIBLE);
                        btmFav2.setVisibility(View.VISIBLE);
                        btmMore2.setOnClickListener(View.OnClickListener {
                            val intent = Intent(this, Detalle::class.java);
                            var extras: Bundle? = Bundle()
                            if (extras != null) {
                                extras.putString("ID", movie2.getString("imdbID"))
                                extras.putInt("idUser",usuarioInicio.id)
                                intent.putExtras(extras)
                            };
                            startActivity(intent)
                        })
                    }else {
                        btmMore2.setVisibility(View.INVISIBLE);
                        btmFav2.setVisibility(View.INVISIBLE);
                    }
                    val movie3 = movies.optJSONObject(2)
                    if (movie3 != null) {
                        DownloadImageFromInternet(findViewById(R.id.imgPoster3)).execute(
                            movie3.getString(
                                "Poster"
                            )
                        )
                        txtResultado3.text = movie3.getString("Title")
                        txtAnio3.text = movie3.getString("Year")
                        btmMore3.setVisibility(View.VISIBLE);
                        btmFav3.setVisibility(View.VISIBLE);
                        btmMore3.setOnClickListener(View.OnClickListener {
                            val intent = Intent(this, Detalle::class.java);
                            var extras: Bundle? = Bundle()
                            if (extras != null) {
                                extras.putString("ID", movie3.getString("imdbID"))
                                extras.putInt("idUser",usuarioInicio.id)
                                intent.putExtras(extras)
                            };
                            startActivity(intent)
                        })
                    }else {
                        btmMore3.setVisibility(View.INVISIBLE);
                        btmFav3.setVisibility(View.INVISIBLE);
                    }
                    val movie4 = movies.optJSONObject(3)
                    if (movie4 != null) {
                        DownloadImageFromInternet(findViewById(R.id.imgPoster4)).execute(
                            movie4.getString(
                                "Poster"
                            )
                        )
                        txtResultado4.text = movie4.getString("Title")
                        txtAnio4.text = movie4.getString("Year")
                        btmMore4.setVisibility(View.VISIBLE);
                        btmFav4.setVisibility(View.VISIBLE);
                        btmMore4.setOnClickListener(View.OnClickListener {
                            val intent = Intent(this, Detalle::class.java);
                            var extras: Bundle? = Bundle()
                            if (extras != null) {
                                extras.putString("ID", movie4.getString("imdbID"))
                                extras.putInt("idUser",usuarioInicio.id)
                                intent.putExtras(extras)
                            };
                            startActivity(intent)
                        })
                    }else {
                        btmMore4.setVisibility(View.INVISIBLE);
                        btmFav4.setVisibility(View.INVISIBLE);
                    }
                    val movie5 = movies.optJSONObject(4)
                    if (movie5 != null) {
                        DownloadImageFromInternet(findViewById(R.id.imgPoster5)).execute(
                            movie5.getString(
                                "Poster"
                            )
                        )
                        txtResultado5.text = movie5.getString("Title")
                        txtAnio5.text = movie5.getString("Year")
                        btmMore5.setVisibility(View.VISIBLE);
                        btmFav5.setVisibility(View.VISIBLE);
                        btmMore5.setOnClickListener(View.OnClickListener {
                            val intent = Intent(this, Detalle::class.java);
                            var extras: Bundle? = Bundle()
                            if (extras != null) {
                                extras.putString("ID", movie5.getString("imdbID"))
                                extras.putInt("idUser",usuarioInicio.id)
                                intent.putExtras(extras)
                            };
                            startActivity(intent)
                        })
                    }else {
                        btmMore5.setVisibility(View.INVISIBLE);
                        btmFav5.setVisibility(View.INVISIBLE);
                    }

                    val movie6 = movies.optJSONObject(5)
                    if (movie6 != null) {
                        DownloadImageFromInternet(findViewById(R.id.imgPoster6)).execute(
                            movie6.getString(
                                "Poster"
                            )
                        )
                        txtResultado6.text = movie6.getString("Title")
                        txtAnio6.text = movie6.getString("Year")
                        btmMore6.setVisibility(View.VISIBLE);
                        btmFav6.setVisibility(View.VISIBLE);
                        btmMore6.setOnClickListener(View.OnClickListener {
                            val intent = Intent(this, Detalle::class.java);
                            var extras: Bundle? = Bundle()
                            if (extras != null) {
                                extras.putString("ID", movie6.getString("imdbID"))
                                extras.putInt("idUser",usuarioInicio.id)
                                intent.putExtras(extras)
                            };
                            startActivity(intent)
                        })
                    }else {
                        btmMore6.setVisibility(View.INVISIBLE);
                        btmFav6.setVisibility(View.INVISIBLE);
                    }
                    val movie7 = movies.optJSONObject(6)
                    if (movie7 != null) {
                        DownloadImageFromInternet(findViewById(R.id.imgPoster7)).execute(
                            movie7.getString(
                                "Poster"
                            )
                        )
                        txtResultado7.text = movie7.getString("Title")
                        txtAnio7.text = movie7.getString("Year")
                        btmMore7.setVisibility(View.VISIBLE);
                        btmFav7.setVisibility(View.VISIBLE);
                        btmMore7.setOnClickListener(View.OnClickListener {
                            val intent = Intent(this, Detalle::class.java);
                            var extras: Bundle? = Bundle()
                            if (extras != null) {
                                extras.putString("ID", movie7.getString("imdbID"))
                                extras.putInt("idUser",usuarioInicio.id)
                                intent.putExtras(extras)
                            };
                            startActivity(intent)
                        })
                    }else {
                        btmMore7.setVisibility(View.INVISIBLE);
                        btmFav7.setVisibility(View.INVISIBLE);
                    }
                    val movie8 = movies.optJSONObject(7)
                    if (movie8 != null) {
                        DownloadImageFromInternet(findViewById(R.id.imgPoster8)).execute(
                            movie8.getString(
                                "Poster"
                            )
                        )
                        txtResultado8.text = movie8.getString("Title")
                        txtAnio8.text = movie8.getString("Year")
                        btmMore8.setVisibility(View.VISIBLE);
                        btmFav8.setVisibility(View.VISIBLE);
                        btmMore8.setOnClickListener(View.OnClickListener {
                            val intent = Intent(this, Detalle::class.java);
                            var extras: Bundle? = Bundle()
                            if (extras != null) {
                                extras.putString("ID", movie8.getString("imdbID"))
                                extras.putInt("idUser",usuarioInicio.id)
                                intent.putExtras(extras)
                            };
                            startActivity(intent)
                        })
                    }else {
                        btmMore8.setVisibility(View.INVISIBLE);
                        btmFav8.setVisibility(View.INVISIBLE);
                    }
                    val movie9 = movies.optJSONObject(8)
                    if (movie9 != null) {
                        DownloadImageFromInternet(findViewById(R.id.imgPoster9)).execute(
                            movie9.getString(
                                "Poster"
                            )
                        )
                        txtResultado9.text = movie9.getString("Title")
                        txtAnio9.text = movie9.getString("Year")
                        btmMore9.setVisibility(View.VISIBLE);
                        btmFav9.setVisibility(View.VISIBLE);
                        btmMore9.setOnClickListener(View.OnClickListener {
                            val intent = Intent(this, Detalle::class.java);
                            var extras: Bundle? = Bundle()
                            if (extras != null) {
                                extras.putString("ID", movie9.getString("imdbID"))
                                extras.putInt("idUser",usuarioInicio.id)
                                intent.putExtras(extras)
                            };
                            startActivity(intent)
                        })
                    }else {
                        btmMore9.setVisibility(View.INVISIBLE);
                        btmFav9.setVisibility(View.INVISIBLE);
                    }
                    val movie10 = movies.optJSONObject(9)
                    if (movie10 != null) {
                        DownloadImageFromInternet(findViewById(R.id.imgPoster10)).execute(
                            movie10.getString(
                                "Poster"
                            )
                        )
                        txtResultado10.text = movie10.getString("Title")
                        txtAnio10.text = movie10.getString("Year")
                        btmMore10.setVisibility(View.VISIBLE);
                        btmFav10.setVisibility(View.VISIBLE);
                        btmMore10.setOnClickListener(View.OnClickListener {
                            val intent = Intent(this, Detalle::class.java);
                            var extras: Bundle? = Bundle()
                            if (extras != null) {
                                extras.putString("ID", movie10.getString("imdbID"))
                                extras.putInt("idUser",usuarioInicio.id)
                                intent.putExtras(extras)
                            };
                            startActivity(intent)
                        })
                    }else {
                        btmMore10.setVisibility(View.INVISIBLE);
                        btmFav10.setVisibility(View.INVISIBLE);
                    }
                    Toast.makeText(
                        applicationContext,
                        "Estamos cargando los datos...",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(applicationContext, "No hay mas resultados", Toast.LENGTH_LONG)
                        .show()
                    btnDerecha.setVisibility(View.INVISIBLE);
                    btmMore.setVisibility(View.INVISIBLE);
                    btmMore2.setVisibility(View.INVISIBLE);
                    btmMore3.setVisibility(View.INVISIBLE);
                    btmMore4.setVisibility(View.INVISIBLE);
                    btmMore5.setVisibility(View.INVISIBLE);
                    btmMore6.setVisibility(View.INVISIBLE);
                    btmMore7.setVisibility(View.INVISIBLE);
                    btmMore8.setVisibility(View.INVISIBLE);
                    btmMore9.setVisibility(View.INVISIBLE);
                    btmMore10.setVisibility(View.INVISIBLE);
                    btmFav.setVisibility(View.INVISIBLE);
                    btmFav2.setVisibility(View.INVISIBLE);
                    btmFav3.setVisibility(View.INVISIBLE);
                    btmFav4.setVisibility(View.INVISIBLE);
                    btmFav5.setVisibility(View.INVISIBLE);
                    btmFav6.setVisibility(View.INVISIBLE);
                    btmFav7.setVisibility(View.INVISIBLE);
                    btmFav8.setVisibility(View.INVISIBLE);
                    btmFav9.setVisibility(View.INVISIBLE);
                    btmFav10.setVisibility(View.INVISIBLE);
                }
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
                val `in` = java.net.URL(imageURL).openStream()
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


}