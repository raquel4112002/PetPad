package ipca.utility.petpad.ui.Coment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import ipca.utility.petpad.R
import ipca.utility.petpad.databinding.FragmentCommentBinding


class CommentFragment : Fragment() {

    var comment = arrayListOf<Comment>()
    val db = Firebase.firestore
    private var _binding: FragmentCommentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PhotoAdapter()
        binding.listViewComent.adapter = adapter

        db.collection("comente")
            .addSnapshotListener { value, e ->
                if (e != null) {

                    return@addSnapshotListener
                }

                comment.clear()
                for (doc in value!!) {
                    val item = Comment.fromDoc(doc)
                    comment.add(item)
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
            return comment.size
        }

        override fun getItem(p0: Int): Any {
            return comment[p0]
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }

        @SuppressLint("SetTextI18n", "MissingInflatedId")
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_coment, p2, false)

            val textViewComente =
                rootView.findViewById<TextView>(R.id.textViewComente)



            val storage = Firebase.storage



            textViewComente.text = comment[p0].description

            return rootView

        }
    }
}