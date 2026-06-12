# Workout App — Final Lab Mobile 2026

Aplikasi Android untuk melihat daftar latihan kebugaran menggunakan **Wger API**.

## Fitur Utama
- Daftar latihan dari Wger API (dengan pagination / infinite scroll)
- Pencarian latihan berdasarkan nama
- Filter latihan berdasarkan kategori (langsung dari server)
- Detail latihan lengkap
- Simpan latihan favorit (offline via SQLite)
- Mode Gelap / Mode Terang
- Tombol retry saat tidak ada koneksi internet

## Spesifikasi Teknis
| Komponen | Implementasi |
|---|---|
| Activity (2) | `MainActivity`, `ExerciseDetailActivity` |
| Intent | `MainActivity` → `ExerciseDetailActivity` (via `ExerciseDetailActivity.start()`) |
| RecyclerView | Daftar exercise di `HomeFragment` & `FavoriteFragment` |
| Fragment (2) | `HomeFragment`, `FavoriteFragment` |
| Navigation Component | `nav_graph.xml` dengan Bottom Navigation |
| Background Thread | `ExecutorService` di `FavoriteDao` |
| Networking (Retrofit) | Wger API `wger.de/api/v2/` |
| SQLite | Tabel `favorites` + `exercises_cache` (`DatabaseHelper`) |
| SharedPreferences | Pengaturan dark/light theme (`ThemeHelper`) |

## API yang Digunakan
- **Wger REST API**: `https://wger.de/api/v2/`
- Endpoint: `/exerciseinfo/` — daftar latihan, mendukung parameter `language`, `limit`, `offset`, dan `category`
- Tidak memerlukan API Key

## Cara Membuka di Android Studio
1. Clone/extract project ini
2. Buka Android Studio → **Open** → pilih folder `WorkoutApp`
3. Tunggu Gradle sync selesai
4. Jalankan di emulator atau device (min API 24)