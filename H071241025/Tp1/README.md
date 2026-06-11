# 📱 Aplikasi Profile Sederhana (Android)

## 📌 Deskripsi Aplikasi

Aplikasi ini adalah aplikasi Android sederhana yang dibuat menggunakan **Java** dan **Android Studio** untuk menampilkan dan mengelola data profil pengguna.

Aplikasi ini memiliki konsep seperti halaman profil pada media sosial (contohnya Instagram), dimana pengguna dapat:

* Melihat data profil
* Mengedit data profil
* Menyimpan perubahan secara permanen

Data profil yang diinput oleh pengguna akan disimpan menggunakan **SharedPreferences**, sehingga data tidak akan hilang walaupun aplikasi ditutup atau dijalankan kembali.

---

## 🧩 Fitur Aplikasi

Aplikasi ini memiliki beberapa fitur utama, yaitu:

### 1. Halaman Profil (MainActivity)

* Menampilkan:

  * Nama
  * Username
  * Bio
* Data diambil dari penyimpanan lokal (SharedPreferences)
* Terdapat tombol **Edit Profile** untuk mengubah data

---

### 2. Halaman Edit Profil (EditProfileActivity)

* User dapat mengubah:

  * Nama
  * Username
  * Bio
* Data lama otomatis muncul di form
* Terdapat tombol **Simpan** untuk menyimpan perubahan

---

### 3. Penyimpanan Data Permanen

* Menggunakan **SharedPreferences**
* Data tetap tersimpan meskipun aplikasi ditutup

---

## ⚙️ Cara Kerja Aplikasi

### 🔄 Alur Sistem

1. Aplikasi dibuka
2. Data profil diambil dari SharedPreferences
3. Data ditampilkan di halaman utama
4. User menekan tombol **Edit Profile**
5. Berpindah ke halaman EditProfileActivity
6. Data lama ditampilkan di form
7. User mengubah data
8. Klik tombol **Simpan**
9. Data:

   * Dikirim kembali ke MainActivity menggunakan Intent
   * Disimpan ke SharedPreferences
10. Halaman utama diperbarui dengan data terbaru

---

## ▶️ Cara Menjalankan Aplikasi

### 1. Clone Repository

```
git clone https://github.com/zahraaa09/LAB_MOBILE_3_2026.git
```
### 2. Masuk ke folder kerja H071241025

```
cd H071241025/Tp1
```

### 3. Buka Project

* Buka **Android Studio**
* Klik **Open**
* Pilih folder project

### 4. Jalankan Aplikasi

* Hubungkan perangkat Android / gunakan emulator
* Klik tombol **Run (▶️)**

---

## 🧪 Contoh Penggunaan

1. Jalankan aplikasi
2. Klik tombol **Edit Profile**
3. Ubah nama / username / bio
4. Klik **Simpan**
5. Data akan langsung berubah di halaman profil
6. Tutup aplikasi dan buka kembali → data tetap tersimpan

---

## ⚠️ Catatan Penting

* Aplikasi ini tidak menggunakan database (hanya SharedPreferences)
* Tidak ada sistem login atau autentikasi
* Data hanya tersimpan secara lokal di perangkat

---

## 👨‍💻 Author

* Nama: Zahra Aulia Putri
* NIM: H071241025

---

 
