# Volunteer Connect 🚀

Selamat datang di proyek **Volunteer Connect**, sebuah aplikasi Android elegan yang dibuat untuk memenuhi *Tugas Praktikum 5* dengan implementasi **SharedPreferences** untuk menyimpan profil relawan sosial. Aplikasi ini dirancang ulang dengan tema "Anti-Gravity" yang modern, memberikan pengalaman visual yang memukau bagi pengguna.

---

## 🎨 Fitur Utama & Desain

Aplikasi ini menonjolkan kombinasi estetika modern dan optimasi kode tingkat profesional:

*   **UI**: Menggunakan elemen `MaterialCardView` dengan efek elevasi tinggi (`elevation="24dp"`) sehingga formulir utama terlihat melayang secara dinamis di atas latar belakang (*Glassmorphism concept*).
*   **Material Design 3 (M3)**: Komponen formulir seperti nama, keahlian, dan umur telah diubah menggunakan `TextInputLayout` berjenis *OutlinedBox* dengan sudut yang *extra rounded* untuk kesan yang bersahabat dan halus.
*   **Dynamic Dark Mode**: Dilengkapi dengan *Switch* interaktif yang mengubah tema warna aplikasi (Light/Dark mode) secara seketika (*real-time*) tanpa perlu memuat ulang aplikasi. Skema warna memastikan kontras tetap nyaman di mata.
*   **Animasi Halus & Interaktif**: Kemunculan data hasil simpanan dilengkapi dengan transisi *fade-in* dan *slide-up* yang diinterpolasi dengan mulus. Notifikasi kesuksesan menggunakan desain *Snackbar* melayang.
*   **Robust Input Validation**: Memiliki validasi mendalam (mencegah field kosong dan menjamin validitas angka pada kolom umur) sebelum data dikirim ke `SharedPreferences`.

## 🛠️ Optimasi Kode (Under the Hood)

Di balik desainnya yang memukau, aplikasi ini dioptimalkan dengan standar *Clean Code*:
*   **ViewBinding**: Meninggalkan tradisi `findViewById` yang usang. *ViewBinding* diaktifkan di `build.gradle.kts` untuk meminimalisir risiko *NullPointerException* dan membuat penulisan kode jauh lebih bersih dan terstruktur.
*   **SharedPreferences Engine**: Penyimpanan data lokal diatur rapi menggunakan mode privat. Terdapat fungsi komprehensif untuk *Save* (Simpan), *Load* (Muat otomatis saat aplikasi dibuka), dan *Clear* (Hapus total data cache profil).

---

## 💻 Cara Menjalankan Proyek

1. **Clone / Download** repositori (atau salin folder proyek) ke komputer Anda.
2. Buka proyek ini menggunakan **Android Studio**.
3. Pastikan **ViewBinding** telah tersinkronisasi. Klik **"Sync Project with Gradle Files"** (ikon panah biru/gajah di kanan atas).
4. Klik tombol **Run** (ikon Play hijau) atau gunakan shortcut `Shift + F10` untuk menjalankan aplikasi di *Emulator* Android atau *Physical Device* Anda.

---

### Profil Input yang Disimpan:
*   👤 **Nama Lengkap**
*   🎯 **Bidang Keahlian**
*   ⏳ **Umur** (Angka)
*   🚻 **Jenis Kelamin** (Pria / Wanita)
