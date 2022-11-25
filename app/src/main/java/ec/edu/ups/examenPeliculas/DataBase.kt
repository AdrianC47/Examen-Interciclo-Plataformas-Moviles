package ec.edu.ups.examenPeliculas

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper




class DataBase(context: Context) :
    SQLiteOpenHelper(context, "BDpeliculas", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE usuario" +
                  "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre  TEXT, apellido TEXT, nickname TEXT UNIQUE, password TEXT)"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS  usuario"
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun addUsuario(usuario: Usuario): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("nombre", usuario.nombre)
        values.put("apellido", usuario.apellido)
        values.put("nickname", usuario.nickname)
        values.put("password", usuario.password)
        val _success = db.insert("usuario", null, values)
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }


    @SuppressLint("Range")
    fun getUsuario(_id: Int): Usuario {
        val usuario = Usuario()
        val db = writableDatabase
        val args = arrayOf(_id)
        val selectQuery = "SELECT  * FROM usuario WHERE  id=$_id"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null && cursor.count >0) {
            cursor.moveToFirst()
            while (cursor.isAfterLast==false) {// Devuelve true cuando el cursor   apunta a la posición después de la última fila.
                usuario.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")))
                usuario.nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                usuario.apellido = cursor.getString(cursor.getColumnIndex("apellido"))
                usuario.nickname = cursor.getString(cursor.getColumnIndex("nickname"))
                usuario.password = cursor.getString(cursor.getColumnIndex("password"))
                cursor.moveToNext()
            }
        } else{
            println("Objeto no encontrado")
            usuario.nombre ="false"

        }
        cursor.close()

        return usuario
    }

    @SuppressLint("Range")
    fun getUsuarioEnLogin(nickName: String,password:String): Usuario {
        val usuarioRecuperado = Usuario()
        val db = this.writableDatabase
        val args = arrayOf(nickName, password)
        val selectQuery = "SELECT  * FROM usuario WHERE  nickName=? AND password=?"
        val cursor = db.rawQuery(selectQuery, args) // Un cursor es una coleccion de filas
        if (cursor != null && cursor.count >0) {
            cursor.moveToFirst()
            while (cursor.isAfterLast==false) {// Devuelve true cuando el cursor   apunta a la posición después de la última fila.
                usuarioRecuperado.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")))
                usuarioRecuperado.nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                usuarioRecuperado.apellido = cursor.getString(cursor.getColumnIndex("apellido"))
                usuarioRecuperado.nickname = cursor.getString(cursor.getColumnIndex("nickname"))
                usuarioRecuperado.password = cursor.getString(cursor.getColumnIndex("password"))
                cursor.moveToNext() //Este método devolverá falso si el cursor ya pasó el última entrada en el conjunto de resultados.
            }

        } else{
            println("Objeto no encontrado")
            usuarioRecuperado.nombre ="false"

        }
        cursor.close()

        return usuarioRecuperado
    }
    @SuppressLint("Range")
    fun getUsuarioEnLogin2(nickName: String,password:String): Usuario {
        val usuarioRecuperado = Usuario()
        val db = this.writableDatabase
        val args = arrayOf(nickName, password)
        val selectQuery = "SELECT  * FROM usuario WHERE  nickName=? AND password=?"
        val cursor = db.rawQuery(selectQuery, args) // Un cursor es una coleccion de filas
        if (cursor != null  && cursor.count >0) {
            cursor.moveToFirst()
//            while (cursor.moveToNext()){ // Mientras pueda moverme al siguiente dato
                usuarioRecuperado.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")))
                usuarioRecuperado.nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                usuarioRecuperado.apellido = cursor.getString(cursor.getColumnIndex("apellido"))
                usuarioRecuperado.nickname = cursor.getString(cursor.getColumnIndex("nickname"))
                usuarioRecuperado.password = cursor.getString(cursor.getColumnIndex("password"))
//            }
        } else{
            println("Objeto no encontrado2")
            usuarioRecuperado.nombre ="false"
        }
        cursor.close()

        return usuarioRecuperado
    }
    val usuarios: List<Usuario>
        @SuppressLint("Range")
        get() {
            val listaUsuario = ArrayList<Usuario>()
            val db = writableDatabase
            val selectQuery = "SELECT  * FROM usuario"
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor != null) {
                cursor.moveToFirst()
                while (cursor.moveToNext()) {
                    val usuario = Usuario()
                    usuario.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")))
                    usuario.nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                    usuario.apellido = cursor.getString(cursor.getColumnIndex("apellido"))
                    usuario.nickname = cursor.getString(cursor.getColumnIndex("nickname"))
                    usuario.password = cursor.getString(cursor.getColumnIndex("password"))
                    listaUsuario.add(usuario)
                }
            }
            cursor.close()
            return listaUsuario
        }

//    Like the wind
//    You came here running
//    Take the consequence of leavin'


}