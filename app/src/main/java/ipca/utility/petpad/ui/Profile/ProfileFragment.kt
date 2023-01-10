package ipca.utility.petpad.ui.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ipca.utility.petpad.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveBtn.setOnClickListener{

            val db = Firebase.firestore
            val currentUser = FirebaseAuth.getInstance().currentUser
            db.collection("users")
                .document(currentUser?.uid!!)
                .set(hashMapOf("name" to binding.etFirstName.text.toString(),"last name" to binding.etLastName.text.toString(),
                "bio" to binding.etBio.text.toString()))
        }


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




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
