# Library App (Android - Java & XML)

Aplikasi **Library App** sederhana berbasis Android yang dibuat untuk memenuhi tugas praktikum:
- Bottom Navigation (Home, Favorites, Add Book)
- Data buku in-memory (tanpa database/SharedPreferences)
- Home dengan RecyclerView + SearchView
- DetailActivity dengan tombol Like
- Favorites auto-refresh
- Add Book dengan ambil cover dari galeri
- Minimal 15 dummy buku

---

## link demo aplikasi

- gdrive: https://drive.google.com/drive/folders/10m3qZvL0DHqrZW19bNOTAz6I8Hn-vhsU

---

## Teknologi

- **Bahasa**: Java
- **UI**: XML
- **Arsitektur sederhana**: Activity + Fragment + Adapter + DataManager
- **Image loading**: Glide
- **Penyimpanan data**: In-memory (`static List<Book>` di `DataManager`)

---

## Struktur Fitur

### 1) Bottom Navigation
Terdiri dari 3 menu:
- **Home** → menampilkan semua buku
- **Add Book** → form input buku baru
- **Favorites** → menampilkan buku yang di-like

Implementasi:
- `MainActivity` sebagai host fragment
- `BottomNavigationView` untuk perpindahan fragment

---

### 2) Data Buku
Model `Book` memuat:
- Judul
- Penulis
- Tahun Terbit
- Blurb
- Cover (URI/Resource)
- Status Like (boolean)

Tambahan nilai plus:
- Rating
- Genre
- Review

Data dummy:
- Minimal **15 buku**, dengan variasi genre

---

### 3) Halaman Home
- Menampilkan daftar buku via **RecyclerView**
- **SearchView** untuk cari berdasarkan judul
- Klik item buku membuka **DetailActivity**
- Urutan buku terbaru di atas (berdasarkan tahun terbit descending)
- Tambahan: filter genre (nilai plus)

---

### 4) Halaman Favorites
- Menampilkan hanya buku yang `liked = true`
- Data diperbarui saat fragment tampil (`onResume`), sehingga selalu sinkron setelah toggle like dari detail

---

### 5) Halaman Add Book
- Form input data buku
- Cover dipilih dari galeri HP (`ActivityResultContracts.GetContent`)
- Data baru langsung masuk ke `DataManager` (in-memory), lalu muncul di Home

---

## Ketentuan Teknis (Kesesuaian Tugas)

### ✅ Sudah sesuai:
- Data disimpan di memori selama aplikasi berjalan
- Memiliki data tambahan (rating, genre, review)
- Ada filter berdasarkan genre (nilai plus)

---

## Alur Data (In-Memory)

Semua data tersimpan di:
- `DataManager.books` (static list)

Konsekuensi:
- Data bertahan selama app process aktif
- Data akan reset jika app force close/restart (sesuai ketentuan tugas karena tanpa DB)

---

## Struktur Folder Utama

```text
java/com/example/libraryapp/
├─ MainActivity.java
├─ DetailActivity.java
├─ adapter/
│  └─ BookAdapter.java
├─ data/
│  └─ DataManager.java
├─ model/
│  └─ Book.java
└─ ui/
   ├─ HomeFragment.java
   ├─ FavoritesFragment.java
   └─ AddBookFragment.java

res/
├─ layout/
├─ menu/
├─ color/
├─ drawable/
├─ values/
└─ values-night/
```

---

## Cara Menjalankan

1. Buka project di Android Studio
2. Sync Gradle
3. Pastikan dependency Glide terpasang
4. Run ke emulator/device
