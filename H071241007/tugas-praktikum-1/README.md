## Project: Activity & Intent (Replikasi UI Gojek)

### 1. Implementasi Layout (Kriteria: Semirip Mungkin & Menjaga Struktur)
Saya memilih untuk mereplikasi UI aplikasi Gojek dengan alasan ui nya yang simple jadinya gampang untuk direplikasi.

* **Struktur Header**: Saya menggunakan `View` dengan latar belakang `#00AA13` untuk menciptakan header hijau khas Gojek.
* **Komponen Profil**: Untuk kartu profil, saya menggunakan `MaterialCardView` dengan *elevation* dan *radius* tertentu agar elemen terlihat melayang di atas header.
* **Teknik Foto Profil Bulat**: Alih-alih menggunakan file drawable tambahan, saya menerapkan teknik *inline* pada `MaterialCardView` dengan mengatur `cardCornerRadius` sebesar $32dp$ untuk menghasilkan bentuk lingkaran sempurna.
* **Tipografi**: Saya menggunakan font **Maison Neue Bold** pada setiap `TextView` untuk menjaga visual agar identik dengan referensi asli.
* **Icon**: Untuk icon sendiri saya menggunakan icon bawaan dari android studio agar tidak ada file tambahan yang perlu saya download.

### 2. Navigasi (Kriteria: onClick ke Halaman Edit Profile)
Untuk berpindah halaman saya memberikan aksi `onClick` yang mengarahkan pengguna ke halaman edit.

* **Explicit Intent**: Saya memberikan `setOnClickListener` pada `btn_edit` (ikon pensil). Saat diklik, aplikasi menjalankan **Explicit Intent** menuju `EditProfileActivity`.
* **Data Passing (Forward)**: Saya memastikan form di halaman edit tidak kosong dengan mengirimkan data profil saat ini (Nama, Email, dan Telepon) melalui `intent.putExtra()`.

### 3. Fungsionalitas Edit (Kriteria: Mengubah Nama, Username, Foto, dsb)
sesuai dengan file tugas yang diberikan saya menambahkan fitur pengguna dapat mengubah berbagai informasi profil.

* **Input Form**: Saya mendesain halaman edit dengan `EditText` minimalis tanpa latar belakang, menggunakan `View` garis bawah tipis untuk meniru gaya input modern.
* **Fitur Ganti Foto**: Saya menambahkan fitur ganti foto profil menggunakan `ActivityResultContracts.GetContent()` untuk mengakses galeri. Untuk optimasi memori dan pemotongan gambar bulat secara otomatis, saya menggunakan library **Glide** dengan fungsi `.circleCrop()`.

### 4. Sinkronisasi Data (Kriteria: Perubahan Ditampilkan Kembali)
Saya memastikan bahwa setiap perubahan harus ditampilkan kembali pada halaman utama.

* **ActivityResultLauncher**: Saya meninggalkan metode lama (`startActivityForResult`) dan beralih ke `ActivityResultLauncher` di `MainActivity` sebagai pendekatan yang lebih modern dan aman.
* **Callback Result**: Saat tombol 'SIMPAN' diklik di halaman edit, data baru dikemas dalam `Intent` dan dikirim balik menggunakan `setResult(RESULT_OK, resultIntent)`.
* **Konsistensi State**: Saya menangani risiko hilangnya data foto dengan menyimpan URI gambar ke dalam variabel global `currentImageUri`, sehingga foto profil tetap sinkron setiap kali navigasi dilakukan berulang kali.

---

### Catatan
Sebelum membuka aplikasi, sebaiknnya pastikan menggunakan light mode.
