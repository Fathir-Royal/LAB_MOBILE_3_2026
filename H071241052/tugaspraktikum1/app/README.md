# Tugas Praktikum 1: Activity & Intent - Instagram Profile Clone

---

## Deskripsi Proyek
Proyek ini merupakan implementasi tugas praktikum pertama mata kuliah Pemrograman Mobile. Aplikasi ini mensimulasikan fitur profil pengguna pada aplikasi Instagram menggunakan konsep **Explicit Intent** dan **Implicit Intent**.

Aplikasi dirancang dengan antarmuka *Dark Mode* yang mencakup elemen-elemen utama seperti:
* **Header**: Username, tombol tambah, dan menu burger.
* **Statistik**: Jumlah postingan, pengikut, dan mengikuti.
* **Biodata**: Nama lengkap dan deskripsi bio yang dapat diperbarui.
* **Fitur Utama**: Tombol "Edit Profil" untuk memperbarui data secara dinamis.

---

## Fitur dan Cara Kerja
Sesuai dengan instruksi tugas, aplikasi ini memiliki alur kerja sebagai berikut:

### 1. Navigasi & Transfer Data (Explicit Intent)
Aplikasi menggunakan **Explicit Intent** untuk berpindah dari `MainActivity` ke `EditProfileActivity`. Data dikirim dan diterima kembali menggunakan pasangan *Key-Value* melalui `putExtra` dan ditangkap di `onActivityResult` untuk memastikan perubahan data ditampilkan kembali di halaman profil.

### 2. Integrasi Galeri (Implicit Intent)
Fitur pembaruan foto profil diimplementasikan menggunakan **Implicit Intent** dengan aksi `ACTION_PICK`. Aplikasi meminta sistem Android untuk membuka galeri perangkat, kemudian menangkap alamat gambar (`Uri`) yang dipilih untuk ditampilkan pada profil.

### 3. UI Kustom (CardView)
Foto profil diimplementasikan menggunakan `CardView` dengan *corner radius* maksimal untuk menciptakan bentuk lingkaran sempurna layaknya aplikasi Instagram asli.

---

## Cara Menjalankan Aplikasi
1. **Clone/Download** repositori ini.
2. Buka folder proyek melalui **Android Studio**.
3. Pastikan perangkat Android (fisik) atau emulator terhubung.
4. Klik tombol **Run** (Segitiga Hijau) pada toolbar Android Studio.
5. Pada halaman profil, klik **Edit Profil**, ubah data atau foto, lalu tekan **Simpan**.

---

## Struktur Direktori
Proyek ini disusun dengan mengikuti standar kerapian repositori yang diinstruksikan:

```text
LAB_MOBILE_3_2026/
└── H071241052/
    └── tugas-praktikum-1/
        ├── app/
        │   ├── src/main/java/ (Kode Sumber Java)
        │   └── src/main/res/ (Layout XML & Resources)
        ├── build.gradle
        └── README.md