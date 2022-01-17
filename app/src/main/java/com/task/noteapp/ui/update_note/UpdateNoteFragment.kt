package com.task.noteapp.ui.update_note

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.task.noteapp.R
import com.task.noteapp.databinding.FragmentUpdateNoteBinding
import com.task.noteapp.models.Note
import com.task.noteapp.ui.viewmodels.NotesViewModel
import com.task.noteapp.util.TimeUtil
import com.task.noteapp.util.showToastMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateNoteFragment : Fragment() {

    private val notesViewModel: NotesViewModel by viewModels()

    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateNoteFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        val view = binding.root

        setHasOptionsMenu(true)

        setUpdateViewsFromArgs()

        return view
    }

    private fun setUpdateViewsFromArgs() {
        binding.etUpdateTitle.setText(args.currentNote.title)
        binding.etUpdateContent.setText(args.currentNote.content)
        binding.etImageUrl.setText(args.currentNote.imageUrl)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_update_note -> {
                updateNote()
            }
            R.id.menu_delete_note -> {
                deleteNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setIcon(R.drawable.ic_delete_forever)
        builder.setTitle(R.string.delete_note)
        builder.setMessage(R.string.delete_confirmation)
        builder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
            notesViewModel.deleteNote(args.currentNote)
            findNavController().navigate(R.id.to_notesListFragment)
            showToastMessage(R.string.deleted_successfully)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.create().show()

    }

    private fun updateNote() {
        val titleText = binding.etUpdateTitle.text.toString()
        val contentText = binding.etUpdateContent.text.toString()
        val imageUrl = binding.etImageUrl.text.toString()
        if (notesViewModel.noteIsValid(titleText, contentText)) {
            val note = Note(
                args.currentNote.id,
                titleText,
                imageUrl,
                contentText,
                TimeUtil.getCurrentTime(),
            )
            notesViewModel.updateNote(note)
            showToastMessage(R.string.updated_successfully)
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