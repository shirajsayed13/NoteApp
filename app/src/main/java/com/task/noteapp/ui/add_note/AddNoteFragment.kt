package com.task.noteapp.ui.add_note

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.task.noteapp.R
import com.task.noteapp.databinding.FragmentAddNoteBinding
import com.task.noteapp.models.Note
import com.task.noteapp.ui.Validator
import com.task.noteapp.ui.viewmodels.NotesViewModel
import com.task.noteapp.util.TimeUtil
import com.task.noteapp.util.showToastMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteFragment : Fragment() {

    private val notesViewModel: NotesViewModel by viewModels()

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        val view = binding.root

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_save_note) {
            saveNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveNote() {
        val titleText = binding.etTitle.text.toString()
        val contentText = binding.etContent.text.toString()
        val imageUrl = binding.etImageUrl.text.toString()
        if (Validator.validateInput(titleText, contentText)) {
            val note = Note(
                0,
                titleText,
                imageUrl,
                contentText,
                TimeUtil.getCurrentTime(),
                false
            )
            notesViewModel.insertNote(note)
            showToastMessage(R.string.noted_added)
            findNavController().navigate(R.id.to_notesListFragment)
        } else {
            showToastMessage(R.string.fill_all_fields)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}