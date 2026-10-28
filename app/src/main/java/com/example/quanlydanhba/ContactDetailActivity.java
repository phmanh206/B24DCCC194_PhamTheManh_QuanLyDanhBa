package com.example.quanlydanhba;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ContactDetailActivity extends AppCompatActivity {

    private TextView tvAvatar, tvName, tvPhone, tvGroupGender, tvNote;
    private CheckBox cbFavorite;
    private Button btnCall, btnBack, btnDelete;

    private int position;     // vị trí liên hệ trong ArrayList
    private Contact contact;  // liên hệ đang xem

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left + 32, bars.top + 32, bars.right + 32, bars.bottom + 32);
            return insets;
        });

        tvAvatar = findViewById(R.id.tvAvatar);
        tvName = findViewById(R.id.tvName);
        tvPhone = findViewById(R.id.tvPhone);
        tvGroupGender = findViewById(R.id.tvGroupGender);
        tvNote = findViewById(R.id.tvNote);
        cbFavorite = findViewById(R.id.cbFavorite);
        btnCall = findViewById(R.id.btnCall);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);

        // Nhận vị trí liên hệ được gửi từ MainActivity qua Intent
        position = getIntent().getIntExtra("position", -1);
        if (position < 0 || position >= ContactStore.contacts.size()) {
            finish(); // vị trí không hợp lệ thì đóng luôn, tránh crash
            return;
        }
        contact = ContactStore.contacts.get(position);

        // Hiển thị thông tin
        tvAvatar.setText(contact.getInitial());
        tvName.setText(contact.getName());
        tvPhone.setText("Số điện thoại: " + contact.getPhone());
        tvGroupGender.setText("Nhóm: " + contact.getGroup()
                + "    Giới tính: " + contact.getGender());
        if (contact.getNote().isEmpty()) {
            tvNote.setText("Ghi chú: (không có)");
        } else {
            tvNote.setText("Ghi chú: " + contact.getNote());
        }
        cbFavorite.setChecked(contact.isFavorite());

        // (5) Đổi trạng thái yêu thích
        cbFavorite.setOnCheckedChangeListener((buttonView, isChecked) ->
                contact.setFavorite(isChecked));

        // (6) Gọi điện: Intent ngầm định ACTION_DIAL mở màn hình quay số
        btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contact.getPhone()));
            startActivity(intent);
        });

        // (7) Quay lại danh sách
        btnBack.setOnClickListener(v -> finish());

        // (8) Xóa liên hệ khỏi ArrayList rồi quay lại
        btnDelete.setOnClickListener(v -> {
            ContactStore.contacts.remove(position);
            Toast.makeText(this, "Đã xóa " + contact.getName(), Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}