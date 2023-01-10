package ipca.utility.petpad

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.ktx.storageMetadata
import ipca.utility.petpad.databinding.FragmentAddComentBinding
import ipca.utility.petpad.ui.home.AddPhotoFragment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddComentFragment: Fragment() {
    private var _binding: FragmentAddComentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddComentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = Firebase.firestore
        val currentUser = FirebaseAuth.getInstance().currentUser
        db.collection("users")
            .document(currentUser?.uid!!)

        binding.imageViewpublic.setOnClickListener {

            uploadFile { filename ->
                filename?.let {


                    val comment = comente(binding.editTextTextPersonName2.text.toString())

                    db.collection("comment")
                        .add(comment.toHashMap())
                        .addOnSuccessListener { documentReference ->
                            Log.d(AddPhotoFragment.TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                            findNavController().popBackStack()
                        }
                        .addOnFailureListener { e ->
                            Log.w(AddPhotoFragment.TAG, "Error adding document", e)
                        }
                }

            }


        }


    }

    fun uploadFile(callback:(String?)->Unit){
        val storage = Firebase.storage
        val storageRef = storage.reference
        val file = Uri.fromFile(File(currentComentePath))

        var metadata = storageMetadata {
            contentType = "text"
        }

        val uploadTask = storageRef.child("text/${file.lastPathSegment}").putFile(file)
        uploadTask.addOnProgressListener { task ->
            val progress = (100.0 * task.bytesTransferred) / task.totalByteCount
            Log.d(AddComentFragment.TAG, "Upload is $progress% done")
        }
        uploadTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback.invoke(file.lastPathSegment)
            }else{
                callback.invoke(null)
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    lateinit var currentComentePath: String
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!
        return File.createTempFile(
            "TEXT${timeStamp}_", /* prefix */
            "text", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentComentePath = absolutePath
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    companion object {

        const val TAG = "AddComentFragment"
    }

}