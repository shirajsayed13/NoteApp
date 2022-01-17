package com.task.noteapp.ui.notes_list

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.task.noteapp.R
import com.task.noteapp.databinding.FragmentNotesListBinding
import com.task.noteapp.models.Note
import com.task.noteapp.ui.adapters.NotesAdapter
import com.task.noteapp.ui.viewmodels.NotesViewModel
import com.task.noteapp.util.showToastMessage
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.ScaleInAnimator


@AndroidEntryPoint
class NotesListFragment : Fragment() {

    private lateinit var notesAdapter: NotesAdapter

    private val notesViewModel: NotesViewModel by viewModels()

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        hideKeyboard()

        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        val view = binding.root

        setupRecyclerView()

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.to_addNoteFragment)
        }

        notesViewModel.allNotes.observe(viewLifecycleOwner, { notesList ->
            showEmptyNotesView(notesList.isEmpty())
            setNotesList(notesList = notesList)
        })

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> {
                deleteAllNotes()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setNotesList(notesList: List<Note>) {
        notesAdapter.setNotesList(notesList)
    }

    private fun deleteAllNotes() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setIcon(R.drawable.ic_delete_forever)
        builder.setTitle(R.string.delete_everything)
        builder.setMessage(R.string.delete_confirmation)
        builder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
            notesViewModel.deleteAllNotes()
            showToastMessage(R.string.deleted_successfully)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.create().show()
    }

    private fun setupRecyclerView() {
        notesAdapter = NotesAdapter()
        binding.rvNotes.apply {
            adapter = notesAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            swipeToDelete(this)
            this.itemAnimator = ScaleInAnimator().apply {
                addDuration = 250
                removeDuration = 250
                moveDuration = 250
                changeDuration = 250
            }
        }
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val noteToDelete = notesAdapter.listOfNotes[viewHolder.adapterPosition]
                    notesViewModel.deleteNote(noteToDelete)
                    showUndoDeleteSnackbar(viewHolder.itemView, noteToDelete)
                }
            }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun showUndoDeleteSnackbar(itemView: View, noteToDelete: Note) {
        val snackbar = Snackbar.make(itemView, R.string.delete_note, Snackbar.LENGTH_LONG)
        snackbar.setAction("Undo") {
            notesViewModel.insertNote(noteToDelete)
        }.show()
    }

    private fun showEmptyNotesView(isEmpty: Boolean) {
        if (isEmpty) {
            binding.lottieAnimationView.visibility = View.VISIBLE
            binding.tvEmptyNotes.visibility = View.VISIBLE
        } else {
            binding.lottieAnimationView.visibility = View.INVISIBLE
            binding.tvEmptyNotes.visibility = View.INVISIBLE
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        requireActivity().currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(
                requireActivity().currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}