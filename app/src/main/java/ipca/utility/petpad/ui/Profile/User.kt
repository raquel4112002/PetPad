package ipca.utility.petpad.ui.Profile

import com.google.firebase.firestore.DocumentSnapshot

class User(var firstName: String?, var lastName: String?, var bio: String?) {


    companion object {
        fun fromDoc (doc: DocumentSnapshot) : User {
            return User(
                doc.getString("name"),
                doc.getString("last name"),
                doc.getString("bio"),
            )
        }

    }
}