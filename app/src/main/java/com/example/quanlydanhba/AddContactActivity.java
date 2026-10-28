package com.example.quanlydanhba;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddContactActivity extends AppCompatActivity {

    private EditText edtName, edtPhone, edtNote;
    private Spinner spGroup;
    private RadioGroup rgGender;
    private CheckBox cbFavorite;
    private Button btnSave;

    private final String[] groups = {"Gia đình", "Bạn bè", "Công việc", "Khác"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left + 16, bars.top + 16, bars.right + 16, bars.bottom + 16);
            return insets;
        });

        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtNote = findViewById(R.id.edtNote);
        spGroup = findViewById(R.id.spGroup);
        rgGender = findViewById(R.id.rgGender);
        cbFavorite = findViewById(R.id.cbFavorite);
        btnSave = findViewById(R.id.btnSave);

        // Đổ danh sách nhóm vào Spinner
        ArrayAdapter<String> groupAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, groups);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGroup.setAdapter(groupAdapter);

        btnSave.setOnClickListener(v -> saveContact());
    }

    private void saveContact() {
        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String note = edtNote.getText().toString().trim();

        // Kiểm tra họ tên
        if (name.isEmpty()) {
            edtName.setError("Vui lòng nhập họ tên");
            edtName.requestFocus();
            return;
        }

        // Kiểm tra số điện thoại: 10 chữ số, bắt đầu bằng 0
        if (!phone.matches("0\\d{9}")) {
            edtPhone.setError("Số điện thoại phải gồm 10 chữ số và bắt đầu bằng 0");
            edtPhone.requestFocus();
            return;
        }

        // Kiểm tra trùng số điện thoại
        if (ContactStore.phoneExists(phone)) {
            edtPhone.setError("Số điện thoại đã tồn tại trong danh bạ");
            edtPhone.requestFocus();
            return;
        }

        // Lấy nhóm, giới tính, yêu thích
        String group = spGroup.getSelectedItem().toString();
        RadioButton rbChecked = findViewById(rgGender.getCheckedRadioButtonId());
        String gender = rbChecked.getText().toString();
        boolean favorite = cbFavorite.isChecked();

        // Tạo đối tượng Contact và thêm vào danh sách
        Contact contact = new Contact(name, phone, group, gender, favorite, note);
        ContactStore.contacts.add(contact);

        Toast.makeText(this, "Đã thêm liên hệ " + name, Toast.LENGTH_SHORT).show();
        finish(); // quay về MainActivity -> onResume() sẽ cập nhật danh sách
    }
}