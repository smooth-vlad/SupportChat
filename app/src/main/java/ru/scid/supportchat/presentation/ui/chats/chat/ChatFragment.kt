package ru.scid.supportchat.presentation.ui.chats.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.scid.supportchat.databinding.FragmentChatBinding
import javax.inject.Inject

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private val args: ChatFragmentArgs by navArgs()

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ChatViewModelFactory

    private lateinit var viewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = viewModelFactory.create(args.ticketId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()

        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.ticketComments.observe(viewLifecycleOwner) {
            val adapter = MessagesListAdapter(viewModel.userId)
            binding.messagesRecyclerView.adapter = adapter
            adapter.submitList(it)
        }
        lifecycleScope.launchWhenStarted {
            viewModel.onError.collectLatest {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.onCommentSent.collectLatest {
                binding.editTextMessage.setText("")
            }
        }
    }

    private fun setUpListeners() {
        binding.sendBtn.setOnClickListener {
            viewModel.onSendClick()
        }

        binding.editTextMessage.addTextChangedListener {
            viewModel.onMessageTextChanged(it.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}