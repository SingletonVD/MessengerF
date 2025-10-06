package com.singletonv.messengerf;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

public class ChatActivity extends AppCompatActivity {

    private static final String EXTRA_CURRENT_USER_ID = "current_Id";
    private static final String EXTRA_OTHER_USER_ID = "other_Id";

    private TextView textViewTitle;
    private View viewUserStatus;
    private RecyclerView recyclerViewMessages;
    private EditText editTextMessage;
    private ImageView imageViewSendMessage;

    private MessagesAdapter messagesAdapter;

    private String currentUserId;
    private String otherUserId;

    private ChatViewModel viewModel;
    private ChatViewModelFactory viewModelFactory;

    public static Intent makeIntent(Context context, String currentUserId, String otherUserId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId);
        intent.putExtra(EXTRA_OTHER_USER_ID, otherUserId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initViews();

        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        otherUserId = getIntent().getStringExtra(EXTRA_OTHER_USER_ID);
        viewModelFactory = new ChatViewModelFactory(currentUserId, otherUserId);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(ChatViewModel.class);

        messagesAdapter = new MessagesAdapter(currentUserId);
        recyclerViewMessages.setAdapter(messagesAdapter);

        observeViewModel();
        imageViewSendMessage.setOnClickListener(view -> {
            Message message = new Message(
                    editTextMessage.getText().toString(),
                    currentUserId,
                    otherUserId
            );
            viewModel.sendMessage(message);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.setUserOnline(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.setUserOnline(false);
    }

    private void initViews() {
        textViewTitle = findViewById(R.id.textViewTitle);
        viewUserStatus = findViewById(R.id.viewUserStatus);
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        editTextMessage = findViewById(R.id.editTextMessage);
        imageViewSendMessage = findViewById(R.id.imageViewSendMessage);
    }

    private void observeViewModel() {
        viewModel.getMessages().observe(this, messagesAdapter::setMessages);
        viewModel.getError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getMessageSent().observe(this, messageSent -> {
            if (messageSent) {
                editTextMessage.setText("");
            }
        });
        viewModel.getOtherUser().observe(this, otherUser -> {
            String userInfo = String.format(
                    "%s %s, %s",
                    otherUser.getName(),
                    otherUser.getLastName(),
                    otherUser.getAge()
            );
            textViewTitle.setText(userInfo);
            int backgroundResId;
            if (otherUser.isOnline()) {
                backgroundResId = R.drawable.circle_green;
            } else {
                backgroundResId = R.drawable.circle_red;
            }
            Drawable background = ContextCompat.getDrawable(this, backgroundResId);
            viewUserStatus.setBackground(background);
        });
    }
}