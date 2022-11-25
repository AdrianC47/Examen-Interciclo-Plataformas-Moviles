package ec.edu.ups.examenPeliculas
//
//class Ejemplo {
//    fun getTask(_id: Int): Tasks {
//        val tasks = Tasks()
//        val db = writableDatabase
//        val selectQuery = "SELECT  * FROM $TABLE_NAME WHERE $ID = $_id"
//        val cursor = db.rawQuery(selectQuery, null)
//        if (cursor != null) {
//            cursor.moveToFirst()
//            while (cursor.moveToNext()) {
//                tasks.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
//                tasks.name = cursor.getString(cursor.getColumnIndex(NAME))
//                tasks.desc = cursor.getString(cursor.getColumnIndex(DESC))
//                tasks.completed = cursor.getString(cursor.getColumnIndex(COMPLETED))
//            }
//        }
//        cursor.close()
//        return tasks
//    }
//}