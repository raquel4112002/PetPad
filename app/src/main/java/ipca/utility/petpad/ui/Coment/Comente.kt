package ipca.utility.petpad.ui.Coment

import com.google.firebase.firestore.DocumentSnapshot

class comente {

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
            fun fromDoc (doc: DocumentSnapshot) : comente {
                return comente (
                    doc.getString("description"),



                    )
            }

        }
}