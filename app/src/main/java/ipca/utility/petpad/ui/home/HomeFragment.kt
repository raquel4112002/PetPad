package ipca.utility.petpad.ui.home

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import ipca.utility.petpad.Photo
import ipca.utility.petpad.R
import ipca.utility.petpad.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    var photos = arrayListOf<Photo>()
    val db = Firebase.firestore
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAddPhoto.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_addPhotoFragment2)
        }
        val adapter = PhotoAdapter()
        binding.listViewPhotos.adapter = adapter

        db.collection("photos")
            .addSnapshotListener { value, e ->
                if (e != null) {

                    return@addSnapshotListener
                }

                photos.clear()
                for (doc in value!!) {
                    val item = Photo.fromDoc(doc)
                    photos.add(item)
                }

                adapter.notifyDataSetChanged()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class PhotoAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return photos.size
        }

        override fun getItem(p0: Int): Any {
            return photos[p0]
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }

        @SuppressLint("SetTextI18n", "MissingInflatedId")
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_photo, p2, false)

            val textViewRowDescription = rootView.findViewById<TextView>(R.id.textViewRowDescription)
            val imageViewRowPhoto = rootView.findViewById<ImageView>(R.id.imagePerfilPhoto)



            val storage = Firebase.storage
            val storageRef = storage.reference

            var islandRef = storageRef.child("images/${photos[p0].photo}")

            val ONE_MEGABYTE: Long = 10024 * 1024
            islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                val inputStream = it.inputStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)
                imageViewRowPhoto.setImageBitmap(bitmap)
            }.addOnFailureListener {
                // Handle any errors
            }


            textViewRowDescription.text = photos[p0].description


            val textViewNumber = rootView.findViewById<TextView>(R.id.textViewNumber)
            val imageViewCoelhinho = rootView.findViewById<ImageView>(R.id.imageViewCoelhinho)

            textViewNumber.text = photos[p0].counter.toString()
            imageViewCoelhinho.setOnClickListener {
                textViewNumber.text = (textViewNumber.text.toString().toInt()+1).toString()
                photos[p0].counter=textViewNumber.text.toString().toInt()
            }

            val imageViewComent = rootView.findViewById<ImageView>(R.id.imageViewComent)

            imageViewComent.setOnClickListener {
                findNavController().navigate(R.id.addComentFragment)
            }

         //   val textViewVerComentarios = rootView.findViewById<ImageView>(R.id.textViewVerComentarios)

          // textViewVerComentarios.setOnClickListener {
            //   findNavController().navigate(R.id.action_navigation_home_to_comentFragment)
          //  }









            return rootView
        }

    }


}