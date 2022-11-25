package ec.edu.ups.examenPeliculas

class Usuario  {
    var id: Int= 0
    var nombre: String = ""
    var apellido: String = ""
    var nickname: String = ""
    var password: String = ""


    constructor(){
        this.id = 0;
        this.nombre  = ""
        this.apellido = ""
        this.nickname = ""
        this.password = ""

    }
    constructor(id: Int, nombre:String,apellido:String,nickname:String,password:String){
        this.id  = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nickname = nickname;
        this.password = password;
    }
    constructor( nombre:String,apellido:String,nickname:String,password:String){
        this.nombre = nombre;
        this.apellido = apellido;
        this.nickname = nickname;
        this.password = password;
    }

    override fun toString(): String {
        return "Usuario(id=$id, nombre='$nombre', apellido='$apellido', nickname='$nickname', password='$password')"
    }


}