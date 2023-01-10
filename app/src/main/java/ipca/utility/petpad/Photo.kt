package ipca.utility.petpad

import com.google.firebase.firestore.DocumentSnapshot

class Photo {

    var description : String? = null
    var photo : String? = null
    var user : String?=null

    constructor(description: String?, photo: String?,user:String? ) {
        this.description = description
        this.photo = photo
        this.user = user
    }


    fun toHashMap () : HashMap<String, Any?> {
        return hashMapOf(
            "description" to description,
            "photo" to  photo,
            "User" to user
        )
    }

    companion object {
        fun fromDoc (doc:DocumentSnapshot) :Photo {
            return Photo (
                doc.getString("description"),
                doc.getString("photo"),
                doc.getString("user")

            )
        }
    }


    }
