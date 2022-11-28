package ec.edu.ups.examenPeliculas

class Pelicula {
    var id:Int = 0
    var valoracion:String = ""
    var usuarioId:Int = 0
    var imdbID:String = ""

    constructor(){
        this.id = 0;
        this.valoracion = "";
        this.usuarioId = 0;
        this.imdbID = "";
    }

    constructor(id: Int, valoracion: String, usuarioId: Int, imdbID:String) {
        this.id = id
        this.valoracion = valoracion
        this.usuarioId = usuarioId
        this.imdbID = imdbID
    }

    constructor(valoracion: String, usuarioId: Int, imdbID:String) {
        this.valoracion = valoracion
        this.usuarioId = usuarioId
        this.imdbID =  imdbID
    }


}