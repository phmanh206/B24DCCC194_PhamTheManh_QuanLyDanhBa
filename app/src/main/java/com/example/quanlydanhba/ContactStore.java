package com.example.quanlydanhba;

import java.util.ArrayList;

public class ContactStore {
    public static ArrayList<Contact> contacts = new ArrayList<>();

    // Nạp 3 liên hệ mẫu (chỉ nạp 1 lần)
    public static void loadSampleData() {
        if (!contacts.isEmpty()) return;
        contacts.add(new Contact("Nguyễn Văn An", "0901234567", "Bạn bè", "Nam", false, ""));
        contacts.add(new Contact("Trần Thị Bình", "0912345678", "Gia đình", "Nữ", true, ""));
        contacts.add(new Contact("Lê Minh Châu", "0987654321", "Công việc", "Nam", false, ""));
    }

    // Kiểm tra số điện thoại đã tồn tại chưa
    public static boolean phoneExists(String phone) {
        for (Contact c : contacts) {
            if (c.getPhone().equals(phone)) return true;
        }
        return false;
    }

    // Đếm số liên hệ yêu thích
    public static int countFavorites() {
        int count = 0;
        for (Contact c : contacts) {
            if (c.isFavorite()) count++;
        }
        return count;
    }
}