package com.example.quanlydanhba;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView tvStats;
    private ListView lvContacts;
    private Button btnAdd;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left + 32, bars.top + 32, bars.right + 32, bars.bottom + 32);
            return insets;
        });

        tvStats = findViewById(R.id.tvStats);
        lvContacts = findViewById(R.id.lvContacts);
        btnAdd = findViewById(R.id.btnAdd);

        // Nạp 3 liên hệ mẫu
        ContactStore.loadSampleData();

        // Gắn Adapter cho ListView
        adapter = new ContactAdapter(this, ContactStore.contacts);
        lvContacts.setAdapter(adapter);

        // Nhấn vào 1 liên hệ -> mở màn hình chi tiết, gửi kèm vị trí
        lvContacts.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, ContactDetailActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        });

        // Nhấn nút Thêm -> mở màn hình thêm liên hệ
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
            startActivity(intent);
        });
    }

    // Chạy mỗi khi quay lại màn hình này (sau khi thêm / xóa / đổi yêu thích)
    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged(); // vẽ lại danh sách
        updateStats();                  // cập nhật thống kê
    }

    private void updateStats() {
        int total = ContactStore.contacts.size();
        int favorites = ContactStore.countFavorites();
        tvStats.setText("Tổng số liên hệ: " + total + "    ★ Yêu thích: " + favorites);
    }
}