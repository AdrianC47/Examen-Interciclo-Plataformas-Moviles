package ec.edu.ups.examenPeliculas

class ReseniaModel {
    var id: Int = 0
    var peliculaId: Int = 0
    var resenia: String = ""
    var usuarioId: Int = 0
    var imdbID: String = ""

    constructor() {
        this.id = 0
        this.peliculaId = 0
        this.resenia = ""
        this.usuarioId = 0
        this.imdbID = "";
    }

    constructor(id: Int, peliculaId: Int, resenia: String, usuarioId: Int,imdbID:String) {
        this.id = id
        this.peliculaId = peliculaId
        this.resenia = resenia
        this.usuarioId = usuarioId
        this.imdbID = imdbID
    }

    constructor(peliculaId: Int, resenia: String, usuarioId: Int,imdbID:String) {
        this.peliculaId = peliculaId
        this.resenia = resenia
        this.usuarioId = usuarioId
        this.imdbID =  imdbID
    }
}