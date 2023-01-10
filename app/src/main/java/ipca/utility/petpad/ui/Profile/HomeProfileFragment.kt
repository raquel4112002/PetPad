package ipca.utility.petpad.ui.Profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import ipca.utility.petpad.LoginActivity
import ipca.utility.petpad.R
import ipca.utility.petpad.databinding.FragmentHomeProfileBinding

class HomeProfileFragment : Fragment() {

    private var _binding: FragmentHomeProfileBinding? = null

    internal lateinit var buttonLogout : Button



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View {
        _binding = FragmentHomeProfileBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogout.setOnClickListener {

            FirebaseAuth.getInstance().signOut()
            Toast.makeText(requireContext(), "You will  logout", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()

        }
        binding.floatingActionButton2.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_Profile_to_ProfileFragment)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}