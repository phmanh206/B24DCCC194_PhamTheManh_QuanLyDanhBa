package com.example.quanlydanhba;

public class Contact {
    private String name;      // họ tên
    private String phone;     // số điện thoại
    private String group;     // nhóm
    private String gender;    // giới tính
    private boolean favorite; // yêu thích
    private String note;      // ghi chú

    public Contact(String name, String phone, String group,
                   String gender, boolean favorite, String note) {
        this.name = name;
        this.phone = phone;
        this.group = group;
        this.gender = gender;
        this.favorite = favorite;
        this.note = note;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getGroup() { return group; }
    public String getGender() { return gender; }
    public boolean isFavorite() { return favorite; }
    public String getNote() { return note; }

    public void setFavorite(boolean favorite) { this.favorite = favorite; }

    // Chữ cái đầu của tên, dùng làm ảnh đại diện
    public String getInitial() {
        String[] parts = name.trim().split(" ");
        return parts[parts.length - 1].substring(0, 1).toUpperCase();
    }
}