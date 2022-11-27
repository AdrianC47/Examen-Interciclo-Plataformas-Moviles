package ec.edu.ups.examenPeliculas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var dataBase :DataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnInicio: Button = findViewById(R.id.btnIniciarSesion)
        val txtUsuario: TextView = findViewById(R.id.txtUsuario)
        val txtPassword: TextView = findViewById(R.id.txtPassword)
        val btnRegistrarse : Button = findViewById(R.id.btnRegistrarse)
        txtUsuario.text=""
        txtPassword.text=""
        dataBase = DataBase(this)
        btnRegistrarse.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Registro::class.java).apply {

            }
            startActivity(intent)
        })
        btnInicio.setOnClickListener(View.OnClickListener {
            if (txtUsuario.getText().toString() != ""  && txtPassword.getText().toString() != ""){
                val usuarioLogin = dataBase.getUsuarioEnLogin(txtUsuario.getText().toString(),txtPassword.getText().toString());
                val pruebaotro = dataBase.getUsuarioEnLogin2(txtUsuario.getText().toString(),txtPassword.getText().toString())
                if (usuarioLogin.nombre != "false" && pruebaotro.nombre!= "false" ) {
                    val toast = Toast.makeText(
                        applicationContext,
                        "Iniciando Sesion",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                val intent = Intent(this, Inicio::class.java).apply {
                    putExtra("idUser", usuarioLogin.id)
                }
                startActivity(intent)
                } else {
                    val toast = Toast.makeText(
                        applicationContext,
                        "Datos incorrectos!!",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                }
            } else {
                val toast = Toast.makeText(
                    applicationContext,
                    "Por favor ingrese datos en los campos",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        })
    }
}