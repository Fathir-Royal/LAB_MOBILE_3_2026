# 📱 ThreadsApp — Kloning UI Halaman Profil Threads

Proyek Android sederhana yang mereplikasi tampilan dan fungsionalitas halaman profil dari aplikasi **Threads (Meta)**. Dibuat menggunakan Java dan Android SDK sebagai tugas/latihan pemrograman Android.

---

## 📌 Deskripsi Proyek

ThreadsApp adalah aplikasi Android yang menampilkan **halaman profil pengguna** bergaya Threads. Pengguna dapat melihat profil mereka dan mengeditnya secara langsung — mengubah nama, username, bio, serta foto profil — tanpa backend atau database. Semua data dikelola sementara di memori selama sesi aplikasi berjalan.

---

## ✨ Fitur

| Fitur | Keterangan |
|---|---|
| 🖼️ Tampilan Profil | Menampilkan nama, username, bio, foto profil, dan jumlah pengikut |
| ✏️ Edit Profil | Formulir untuk mengubah nama, username, dan bio |
| 📷 Ganti Foto Profil | Memilih foto dari galeri perangkat |
| 🧭 Bottom Navigation Bar | Navigasi bawah bergaya Threads (Home, Cari, Buat, Notifikasi, Profil) |
| 🗂️ Tab Konten | Tab Threads, Balasan, Media, dan Postingan |

---

## 🗂️ Struktur Proyek

```
main/
├── AndroidManifest.xml                         # Konfigurasi & deklarasi Activity
├── java/com/example/threadsapp/
│   ├── MainActivity.java                       # Activity utama (halaman profil)
│   └── EditProfileActivity.java                # Activity edit profil
└── res/
    ├── layout/
    │   ├── activity_main.xml                   # Layout halaman profil
    │   └── activity_edit_profile.xml           # Layout halaman edit profil
    ├── drawable/
    │   ├── circle_bg.xml                       # Shape bulat untuk foto profil
    │   ├── ic_launcher_background.xml
    │   └── ic_launcher_foreground.xml
    ├── values/
    │   ├── strings.xml                         # Resource teks (nama aplikasi)
    │   ├── colors.xml                          # Palet warna
    │   └── themes.xml                          # Tema aplikasi
    └── mipmap-*/                               # Ikon aplikasi berbagai resolusi
```

---

## ⚙️ Cara Kerja

### Alur Aplikasi

```
┌─────────────────────────────────────────┐
│           MainActivity                  │
│  (Halaman Profil)                       │
│                                         │
│  • Tampilkan nama, username, bio        │
│  • Tampilkan foto profil                │
│  • Tombol "Edit profil"                 │
└──────────────┬──────────────────────────┘
               │ Klik "Edit profil"
               │ (kirim data via Intent)
               ▼
┌─────────────────────────────────────────┐
│        EditProfileActivity              │
│  (Halaman Edit Profil)                  │
│                                         │
│  • Form: Nama, Username, Bio            │
│  • Ganti foto dari galeri               │
│  • Tombol "Batal" → kembali tanpa ubah  │
│  • Tombol "Selesai" → simpan & kembali  │
└──────────────┬──────────────────────────┘
               │ Klik "Selesai"
               │ (kirim hasil via Intent)
               ▼
┌─────────────────────────────────────────┐
│    MainActivity (diperbarui)            │
│  • Nama, username, bio diupdate         │
│  • Foto profil diperbarui               │
└─────────────────────────────────────────┘
```

### Penjelasan Teknis

**1. `MainActivity.java`**
- Activity pertama yang dijalankan saat aplikasi dibuka.
- Menampilkan data profil (nama, username, bio, foto) pada layout.
- Saat tombol **"Edit profil"** diklik, data profil saat ini dikirim ke `EditProfileActivity` menggunakan `Intent` dengan `startActivityForResult()`.
- Menerima hasil edit melalui `onActivityResult()` dan langsung memperbarui tampilan.

**2. `EditProfileActivity.java`**
- Menerima data profil lama dari `MainActivity` via `Intent`.
- Menampilkan formulir yang sudah terisi dengan data tersebut.
- Pengguna bisa mengganti foto profil dengan memilih gambar dari galeri (menggunakan `ACTION_OPEN_DOCUMENT`).
- Tombol **"Selesai"**: mengemas data baru ke dalam `Intent` dan memanggil `setResult(RESULT_OK)` sebelum menutup diri.
- Tombol **"Batal"**: memanggil `finish()` tanpa mengembalikan data (profil tidak berubah).

**3. Komunikasi Antar Activity**

Data dipertukarkan menggunakan `Intent` extras dengan key berikut:

| Key | Tipe | Keterangan |
|---|---|---|
| `"nama"` | String | Nama lengkap pengguna |
| `"username"` | String | Username pengguna |
| `"bio"` | String | Bio / deskripsi singkat |
| `"imageUri"` | String | URI foto profil dari galeri |

---

## 🛠️ Persyaratan

- **Android Studio** Hedgehog (2023.1.1) atau lebih baru
- **Minimum SDK**: API 21 (Android 5.0 Lollipop)
- **Target SDK**: API 34 (Android 14)
- **Bahasa**: Java
- **Dependensi**: Material Components for Android (`com.google.android.material`)

---

## 🚀 Cara Menjalankan

### 1. Clone / Import Proyek

Karena proyek ini hanya berisi folder `main/` (source module), ikuti langkah berikut:

1. Buka **Android Studio**.
2. Pilih **New Project** → buat proyek baru dengan nama `ThreadsApp` dan package `com.example.threadsapp`.
3. Setelah proyek terbuat, **ganti seluruh isi folder `app/src/main/`** dengan isi folder `main/` dari proyek ini.

### 2. Sinkronisasi Gradle

Pastikan `build.gradle (app)` sudah menyertakan dependensi Material Components:

```gradle
dependencies {
    implementation 'com.google.android.material:material:1.11.0'
    // ... dependensi lainnya
}
```

Klik **"Sync Now"** jika muncul notifikasi sinkronisasi Gradle.

### 3. Jalankan Aplikasi

- **Emulator**: Buka AVD Manager → buat emulator Android (API 21+) → klik ▶ Run.
- **Perangkat Fisik**: Aktifkan *USB Debugging* di perangkat → sambungkan via USB → klik ▶ Run.

---

## 📸 Tampilan Aplikasi

### Halaman Profil (`MainActivity`)
- Top bar dengan ikon `@`, 🔍, 📤, ☰
- Foto profil bulat, nama tebal, username, bio, dan jumlah pengikut
- Dua tombol: **Edit profil** & **Bagikan profil**
- Tab: Threads · Balasan · Media · Postingan
- Contoh thread kosong di bawah tab
- Bottom navigation bar: 🏠 🔍 ➕ 🔔 👤

### Halaman Edit Profil (`EditProfileActivity`)
- Top bar: tombol **Batal** (kiri) — judul **Edit profil** (tengah) — tombol **Selesai** biru (kanan)
- Foto profil bulat yang bisa diganti dengan label "Ganti foto profil"
- Field input: **Nama**, **Username**, **Bio**

---

## ⚠️ Catatan & Keterbatasan

- **Tidak ada penyimpanan permanen** — data profil akan kembali ke nilai awal saat aplikasi ditutup dan dibuka kembali.
- **Tidak ada autentikasi** — aplikasi tidak memiliki sistem login.
- **Data hardcoded** — nama default (`Fathir Royal`), username (`fathir9090`), dan jumlah pengikut (`123 pengikut`) ditulis langsung di layout XML.
- **Bottom nav & tab tidak berfungsi** — ikon navigasi dan tab (Threads, Balasan, Media, Postingan) hanya sebagai dekorasi UI, belum terhubung ke halaman lain.
- **Foto profil tidak persisten** — URI gambar hanya disimpan di memori, jika aplikasi dimatikan foto akan hilang.

---

## 👨‍💻 Teknologi yang Digunakan

- **Java** — bahasa pemrograman utama
- **Android SDK** — framework aplikasi Android
- **Material Components** — komponen UI (tombol `OutlinedButton`)
- **RelativeLayout & LinearLayout** — sistem tata letak XML
- **Intent & `startActivityForResult()`** — komunikasi antar Activity
- **`ACTION_OPEN_DOCUMENT`** — akses gambar dari galeri perangkat
