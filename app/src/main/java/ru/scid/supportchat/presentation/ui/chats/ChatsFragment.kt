package ru.scid.supportchat.presentation.ui.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.scid.supportchat.databinding.FragmentChatsBinding

@AndroidEntryPoint
class ChatsFragment : Fragment() {

    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!

    val viewModel: ChatsViewModel by viewModels()

    private lateinit var listAdapter: ChatsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
        setUpListeners()

        viewModel.listTickets()
    }

    private fun setUpListeners() {
        binding.floatingActionButton.setOnClickListener {
            val action = ChatsFragmentDirections.actionChatsFragmentToCreateChatFragment()
            findNavController().navigate(action)
        }
        binding.srlIndicator.setOnRefreshListener {
            viewModel.listTickets()
        }
    }

    private fun setUpObservers() {
        viewModel.tickets.observe(viewLifecycleOwner) {
            binding.tvEmptyListMessage.isVisible = it.isEmpty()
            binding.chatsRecyclerView.isVisible = it.isNotEmpty()
            if (it.isNotEmpty()) {
                listAdapter = ChatsListAdapter {
                    val action = ChatsFragmentDirections.actionChatsFragmentToChatFragment(it.id)
                    findNavController().navigate(action)
                }
                binding.chatsRecyclerView.adapter = listAdapter
                listAdapter.submitList(it)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.onError.collectLatest {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (!it) {
                binding.srlIndicator.isRefreshing = false
            }
        }
    }
}