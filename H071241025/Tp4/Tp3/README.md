# Tugas Praktikum 4 - Library App dengan Background Thread

> **Nama:** Zahra Aulia Putri 
> **NIM:** H071241025  

---

## 📱 Deskripsi Aplikasi

**Library App** adalah aplikasi manajemen buku berbasis Android yang ditingkatkan dengan penerapan **Background Thread** menggunakan **AsyncTask** agar aplikasi tetap responsif saat melakukan proses pencarian dan pemuatan data.

### Halaman Utama
1. **Home** → Daftar buku + search, filter, sorting (background thread)  
2. **Favorites** → Buku yang di-like (background thread)  
3. **Add Book** → Form tambah buku  

---

## ✅ Fitur Aplikasi

### 📌 Home (Daftar Buku)
- RecyclerView vertical  
- 15+ data buku dummy  
- SearchView (background thread + ProgressBar)  
- Filter genre (background thread)  
- Sorting:
  - Tahun terbaru  
  - Tahun terlama  
  - Judul A-Z  
  - Judul Z-A  
- ProgressBar saat loading  
- Klik buku → DetailActivity  

### 📌 Favorites
- Menampilkan buku yang di-like  
- Load data menggunakan background thread  
- ProgressBar saat loading  
- Auto refresh (onResume)  

### 📌 Add Book
- Input:
  - Judul  
  - Penulis  
  - Tahun  
  - Genre  
  - Sinopsis  
- Ambil cover dari galeri  
- Preview gambar  

### 📌 Detail Buku
- Info lengkap buku  
- Rating + review  
- Like / Unlike  

---

## 🧵 Implementasi Background Thread

### Mengapa Digunakan?

| Tanpa Background Thread | Dengan Background Thread |
|------------------------|--------------------------|
| Proses di UI thread    | Proses di background     |
| Bisa lag / ANR         | Tetap responsif          |
| Tidak ada loading      | Ada ProgressBar          |

### Teknologi
- AsyncTask  
- ProgressBar  

---

## 📊 Alur Background Thread

### 1. Filter & Sorting (Home)

```text
User melakukan search / filter / sort
        ↓
ProgressBar muncul
        ↓
AsyncTask (doInBackground)
        ↓
onPostExecute (update RecyclerView)
        ↓
ProgressBar hilang
```

### 2. Load Favorites
```text
User membuka Favorites
        ↓
ProgressBar muncul
        ↓
AsyncTask (load data)
        ↓
onPostExecute (update RecyclerView)
        ↓
ProgressBar hilang
```

## 💻 Kode Implementasi

### AsyncTask - Home
```java
private class FilterBooksTask extends AsyncTask<Void, Void, ArrayList<Book>> {

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
        rvBooks.setVisibility(View.GONE);
    }

    @Override
    protected ArrayList<Book> doInBackground(Void... voids) {
        ArrayList<Book> filteredList = new ArrayList<>();
        // logika filter & sorting
        return filteredList;
    }

    @Override
    protected void onPostExecute(ArrayList<Book> filteredList) {
        progressBar.setVisibility(View.GONE);
        bookAdapter.updateList(filteredList);
        rvBooks.setVisibility(View.VISIBLE);
    }
}
```

### AsyncTask - Favorites
```java
private class LoadFavoritesTask extends AsyncTask<Void, Void, ArrayList<Book>> {

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected ArrayList<Book> doInBackground(Void... voids) {
        return DataDummy.getFavoriteBooks();
    }

    @Override
    protected void onPostExecute(ArrayList<Book> books) {
        progressBar.setVisibility(View.GONE);
        favoriteAdapter.updateList(books);
    }
}
```

## 🔄 Perbandingan

| Aspek             | Sebelum   | Sesudah           |
| ----------------- | --------- | ----------------- |
| Filter & Sort     | UI thread | Background thread |
| Load Favorites    | UI thread | Background thread |
| Loading Indicator | Tidak ada | ProgressBar       |
| Responsivitas     | Bisa lag  | Responsif         |

## 🛠️ Cara Menjalankan
1. Buka project di Android Studio
2. Minimum SDK: API 21
3. Sync Gradle
4. Jalankan di emulator / device
5. Izinkan akses galeri

## Video Demo Aplikasi
Klik link di bawah ini untuk menonton demo aplikasi:

[![Video Demo Aplikasi](https://img.shields.io/badge/Google_Drive-Video_Demo-blue?logo=googledrive)](https://drive.google.com/file/d/1gX3ebUg-KdHPT2KHkTGbOaxICHPZqeSF/view?usp=drivesdk)


## 📁 Catatan Struktur Folder
Tugas Praktikum ini merupakan lanjutan dari Tugas Praktikum sebelumnya (TP3). Saya menggunakan project yang sama (Tp3) dan tidak membuat project baru dari awal.

### Perubahan dari TP3
- FragmentHome.java → AsyncTask (filter & sorting)
- FavoritesFragment.java → AsyncTask (load data)
- fragment_home.xml → ProgressBar
- fragment_favorites.xml → ProgressBar

### Cara Menjalankan Project
1. Buka folder Tp4/Tp3/ di Android Studio
2. Jalankan aplikasi seperti biasa