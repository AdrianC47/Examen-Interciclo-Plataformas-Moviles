package ec.edu.ups.examenPeliculas

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


class DataBase(context: Context) :
    SQLiteOpenHelper(context, "BDpeliculas", null, 1) {


    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE usuario" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre  TEXT, apellido TEXT, nickname TEXT UNIQUE, password TEXT)"

        val CREAR_TABLA_PELICULA = "CREATE TABLE pelicula" +
                "(id_Pelicula INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "valoracion TEXT,imdbID TEXT, id_Pelicula_Usuario INTEGER, FOREIGN KEY(id_Pelicula_Usuario) REFERENCES usuario(usuario_id))"

        db.execSQL(CREATE_TABLE)
        db.execSQL(CREAR_TABLA_PELICULA)
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




    fun addPelicula(pelicula: Pelicula): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("valoracion", pelicula.valoracion)
        values.put("id_Pelicula_Usuario", pelicula.usuarioId)
        values.put("imdbID", pelicula.imdbID)
        val _success = db.insert("pelicula", null, values)
        println("se creo")
        db.close()
        return (Integer.parseInt("$_success") != -1)


    }
    fun buscarPelicula(pelicula: Pelicula): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        var bandera : Boolean = false
        val usuarioId = pelicula.usuarioId.toString()
        val args = arrayOf(usuarioId, pelicula.imdbID)
        val selectQuery = "SELECT  * FROM pelicula WHERE  id_Pelicula_Usuario=? AND imdbID=?"
        val cursor = db.rawQuery(selectQuery, args) // Un cursor es una coleccion de filas
        println("cantidad "+cursor.count)
        if (cursor.count ==0){
            bandera = true
        }
        return bandera
    }

    fun actualizarPelicula(pelicula: Pelicula): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        val usuarioId = pelicula.usuarioId.toString()
        val args = arrayOf(usuarioId, pelicula.imdbID)
        values.put("valoracion", pelicula.valoracion)
        values.put("id_Pelicula_Usuario", pelicula.usuarioId)
        values.put("imdbID", pelicula.imdbID)
        val _success = db.update("pelicula", values, "id_Pelicula_Usuario=? AND imdbID=?", args)
        println("se Actualizo")
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
        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
            while (cursor.isAfterLast == false) {// Devuelve true cuando el cursor   apunta a la posición después de la última fila.
                usuario.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")))
                usuario.nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                usuario.apellido = cursor.getString(cursor.getColumnIndex("apellido"))
                usuario.nickname = cursor.getString(cursor.getColumnIndex("nickname"))
                usuario.password = cursor.getString(cursor.getColumnIndex("password"))
                cursor.moveToNext()
            }
        } else {
            println("Objeto no encontrado")
            usuario.nombre = "false"
        }
        cursor.close()
        return usuario
    }

    @SuppressLint("Range")
    fun getPelicula(_id: Int): Pelicula {
        val pelicula = Pelicula()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM pelicula WHERE  id_Pelicula=$_id"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
            while (cursor.isAfterLast == false) {// Devuelve true cuando el cursor   apunta a la posición después de la última fila.
                pelicula.id =
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex("id_Pelicula")))
                pelicula.valoracion = cursor.getString(cursor.getColumnIndex("valoracion"))
                pelicula.usuarioId =
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex("usuario_id")))
                pelicula.imdbID = cursor.getString(cursor.getColumnIndex("imdbID"))
                cursor.moveToNext()
            }
        } else {
            println("Pelicula no encontrada")
            pelicula.valoracion = "false"
        }
        cursor.close()
        return pelicula
    }


    @SuppressLint("Range")
    fun getUsuarioEnLogin(nickName: String, password: String): Usuario {
        val usuarioRecuperado = Usuario()
        val db = this.writableDatabase
        val args = arrayOf(nickName, password)
        val selectQuery = "SELECT  * FROM usuario WHERE  nickName=? AND password=?"
        val cursor = db.rawQuery(selectQuery, args) // Un cursor es una coleccion de filas
        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
            while (cursor.isAfterLast == false) {// Devuelve true cuando el cursor   apunta a la posición después de la última fila.
                usuarioRecuperado.id =
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")))
                usuarioRecuperado.nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                usuarioRecuperado.apellido = cursor.getString(cursor.getColumnIndex("apellido"))
                usuarioRecuperado.nickname = cursor.getString(cursor.getColumnIndex("nickname"))
                usuarioRecuperado.password = cursor.getString(cursor.getColumnIndex("password"))
                cursor.moveToNext() //Este método devolverá falso si el cursor ya pasó el última entrada en el conjunto de resultados.
            }
        } else {
            println("Objeto no encontrado")
            usuarioRecuperado.nombre = "false"
        }
        cursor.close()
        return usuarioRecuperado
    }

    @SuppressLint("Range")
    fun getUsuarioEnLogin2(nickName: String, password: String): Usuario {
        val usuarioRecuperado = Usuario()
        val db = this.writableDatabase
        val args = arrayOf(nickName, password)
        val selectQuery = "SELECT  * FROM usuario WHERE  nickName=? AND password=?"
        val cursor = db.rawQuery(selectQuery, args) // Un cursor es una coleccion de filas
        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
//            while (cursor.moveToNext()){ // Mientras pueda moverme al siguiente dato
            usuarioRecuperado.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")))
            usuarioRecuperado.nombre = cursor.getString(cursor.getColumnIndex("nombre"))
            usuarioRecuperado.apellido = cursor.getString(cursor.getColumnIndex("apellido"))
            usuarioRecuperado.nickname = cursor.getString(cursor.getColumnIndex("nickname"))
            usuarioRecuperado.password = cursor.getString(cursor.getColumnIndex("password"))
//            }
        } else {
            println("Objeto no encontrado2")
            usuarioRecuperado.nombre = "false"
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


    @SuppressLint("Range")
    fun obtenerPeliculasFav(usuarioId: Int): List<Pelicula> {
        val listaPelicula = ArrayList<Pelicula>()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM pelicula WHERE  id_Pelicula_Usuario=$usuarioId"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
            while (cursor.isAfterLast == false) {
                val pelicula = Pelicula()
                pelicula.id =
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex("id_Pelicula")))
                pelicula.imdbID = cursor.getString(cursor.getColumnIndex("imdbID"))
                pelicula.valoracion = cursor.getString(cursor.getColumnIndex("valoracion"))
                pelicula.usuarioId =
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex("id_Pelicula_Usuario")))
                listaPelicula.add(pelicula)
//                println("Strangers")
                cursor.moveToNext() //Este método devolverá falso si el cursor ya
            }
        } else {
            println("No hay lista no encontrado")
        }
        cursor.close()
        return listaPelicula
    }

    val obtenerPeliculasFav: List<Pelicula>
        @SuppressLint("Range")
        get() {
            val listaPelicula = ArrayList<Pelicula>()
            val db = writableDatabase
            val selectQuery = "SELECT  * FROM pelicula"
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor != null) {
                cursor.moveToFirst()
                while (cursor.moveToNext()) {
                    val pelicula = Pelicula()
                    pelicula.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")))
                    pelicula.imdbID = cursor.getString(cursor.getColumnIndex("imdbID"))
                    pelicula.valoracion = cursor.getString(cursor.getColumnIndex("valoracion"))
                    pelicula.usuarioId =
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex("id_Pelicula_Usuario")))
                    listaPelicula.add(pelicula)
                }
            }
            cursor.close()
            return listaPelicula
        }
//    Like the wind
//    You came here running
//    Take the consequence of leavin'


}