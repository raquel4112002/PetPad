package ipca.utility.petpad.ui.Coment

import com.google.firebase.firestore.DocumentSnapshot

class Comment {

        var description : String? = null



        constructor(description: String?) {
            this.description = description

        }


        fun toHashMap () : HashMap<String, Any?> {
            return hashMapOf(
                "description" to description,

            )
        }

        companion object {
            fun fromDoc (doc: DocumentSnapshot) : Comment {
                return Comment (
                    doc.getString("description"),



                    )
            }

        }
}