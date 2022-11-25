package ec.edu.ups.examenPeliculas

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class Registro : AppCompatActivity() {
    lateinit var dataBase :DataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        val txtNombre : TextView = findViewById(R.id.txtNombre)
        val txtApellido : TextView = findViewById(R.id.txtApellido)
        val txtUsuario : TextView = findViewById(R.id.txtUsuario2)
        val txtPassword : TextView = findViewById(R.id.txtPassword2)
        val btnRegistrarse : Button = findViewById(R.id.btnRegistrarse2)
        dataBase = DataBase(this)
        btnRegistrarse.setOnClickListener(View.OnClickListener {
            if (txtNombre.getText().toString()!="" && txtApellido.getText().toString() !=""
                && txtUsuario.getText().toString() !="" && txtPassword.getText().toString() !="" ){
                val u =  Usuario(txtNombre.getText().toString(),txtApellido.getText().toString(),txtUsuario.getText().toString(),txtPassword.getText().toString());
                val bandera: Boolean  =dataBase.addUsuario(u)
                if (bandera == true){
                    val toast = Toast.makeText(
                        applicationContext,
                        "Usuario Registrado!!",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()

                }
            } else {
                val toast = Toast.makeText(
                    applicationContext,
                    "Campos Vacios!! Usuario no Creado",
                    Toast.LENGTH_SHORT
                )
                toast.show()
                val intent = Intent(this, MainActivity::class.java).apply {}
                startActivity(intent)
            }
        })
    }
}