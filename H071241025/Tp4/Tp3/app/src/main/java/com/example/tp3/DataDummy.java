package com.example.tp3;

import android.net.Uri;
import java.util.ArrayList;
import java.util.Collections;

public class DataDummy {

    private static ArrayList<Book> bookList = new ArrayList<>();

    private static Uri getDrawableUri(String drawableName) {
        return Uri.parse("android.resource://com.example.tp3/drawable/" + drawableName);
    }

    public static void initDummyData() {
        bookList.clear();

        Book book1 = new Book(
                "7 Prajurit Bapak",
                "Wulan Nuramalia",
                2022,
                "Kisah tentang tujuh anak yang dibesarkan oleh seorang ayah dengan cara unik dan penuh disiplin. Di balik kerasnya didikan, tersimpan kasih sayang dan harapan besar untuk masa depan mereka.",
                getDrawableUri("prajurit_bapak"),
                "Drama"
        );
        bookList.add(book1);

        Book book2 = new Book(
                "Cantik Itu Luka",
                "Eka Kurniawan",
                2002,
                "Mengisahkan kehidupan Dewi Ayu, seorang perempuan yang bangkit dari kematian, serta tragedi yang menimpa keluarganya. Cerita penuh ironi, sejarah kelam, dan kritik sosial.",
                getDrawableUri("cantikituluka"),
                "Sastra, Realisme Magis"
        );
        bookList.add(book2);

        Book book3 = new Book(
                "The Devotion of Suspect X (Catatan Pembunuhan)",
                "Keigo Higashino",
                2005,
                "Seorang jenius matematika membantu sahabatnya menutupi sebuah pembunuhan. Namun, rencana sempurna itu perlahan terancam oleh penyelidikan yang semakin mendalam.",
                getDrawableUri("catatanpembunuhan"),
                "Misteri, Thriller"
        );
        bookList.add(book3);

        Book book4 = new Book(
                "Dompet Ayah Sepatu Ibu",
                "J.S. Khairen",
                2021,
                "Cerita hangat tentang pengorbanan orang tua demi anak-anaknya, disampaikan dengan cara yang sederhana namun menyentuh hati.",
                getDrawableUri("dompetayah"),
                "Drama, Keluarga"
        );
        bookList.add(book4);

        Book book5 = new Book(
                "Hilmy Milan",
                "Nadia Ristivani",
                2021,
                "Milan, gadis dari keluarga mafia, bertemu dengan Hilmy yang diam-diam mencintainya. Di balik sikap dinginnya, perhatian tulus Hilmy perlahan meluluhkan hati Milan hingga keduanya menjalin hubungan penuh makna.",
                getDrawableUri("hilmymilan"),
                "Romantis, Drama"
        );
        bookList.add(book5);

        Book book6 = new Book(
                "Hujan",
                "Tere Liye",
                2016,
                "Cerita tentang cinta, kehilangan, dan harapan di dunia pasca bencana.",
                getDrawableUri("hujan"),
                "Fiksi, Sci-Fi, Drama"
        );
        bookList.add(book6);

        Book book7 = new Book(
                "Welcome to Hyunam-dong Bookshop",
                "Hwang Bo-reum",
                2022,
                "Toko buku kecil yang menjadi tempat orang-orang menemukan kembali makna hidup.",
                getDrawableUri("hyungnambookshop"),
                "Healing, Slice of Life"
        );
        bookList.add(book7);

        Book book8 = new Book(
                "Laut Bercerita",
                "Leila S.Chudori",
                2017,
                "Kisah aktivis yang hilang di masa kelam Indonesia dan luka yang ditinggalkan.",
                getDrawableUri("lautbercerita"),
                "Sejarah, Drama"
        );
        bookList.add(book8);

        Book book9 = new Book(
                "The Midnight Library",
                "Matt Haig",
                2020,
                "Seorang perempuan menjelajahi kemungkinan hidup lain melalui perpustakaan misterius.",
                getDrawableUri("midnightlibrary"),
                "Fantasi, Filosofi"
        );
        bookList.add(book9);

        Book book10 = new Book(
                "Keajaiban Toko Kelontong Namiya",
                "Keigo Higashino",
                2012,
                "Toko tua yang memungkinkan orang dari masa berbeda saling bertukar surat dan nasihat hidup.",
                getDrawableUri("namiya"),
                "Drama, Misteri"
        );
        bookList.add(book10);

        Book book11 = new Book(
                "Pulang-Pergi",
                "Tere Liye",
                2021,
                "Petualangan penuh konflik dan rahasia dalam dunia paralel yang luas.",
                getDrawableUri("pulangpergi"),
                "Aksi, Petualangan"
        );
        bookList.add(book11);

        Book book12 = new Book(
                "Seporsi Mie Ayam Sebelum Mati",
                "Brian Khrisna",
                2021,
                "Refleksi hidup tentang hal-hal sederhana yang sering terlupakan sebelum semuanya berakhir.",
                getDrawableUri("seporsimieayam"),
                "Drama, Kehidupan"
        );
        bookList.add(book12);

        Book book13 = new Book(
                "Sisi Tergelap Surga",
                "Brian Khrisna",
                2022,
                "Mengungkap sisi gelap manusia di balik kehidupan yang tampak sempurna.",
                getDrawableUri("sisitergelapsurga"),
                "Drama, Psikologi"
        );
        bookList.add(book13);

        Book book14 = new Book(
                "Di Tanah Lada",
                "Ziggy Zezsyazeoviennazabrizkie",
                2015,
                "Cerita dari sudut pandang anak tentang kerasnya kehidupan dan pencarian makna keluarga.",
                getDrawableUri("tanahlada"),
                "Drama, Sosial"
        );
        bookList.add(book14);

        Book book15 = new Book(
                "Strange House (Teka-Teki Rumah Aneh)",
                "Uketsu",
                2020,
                "Rumah dengan desain aneh menyimpan rahasia kelam yang perlahan terungkap.",
                getDrawableUri("tekateki"),
                "Horor, Misteri"
        );
        bookList.add(book15);

        // Urutkan berdasarkan tahun terbit (terbaru di atas)
        sortByYearDesc();
    }

    public static void sortByYearDesc() {
        Collections.sort(
                bookList,
                (b1, b2) -> Integer.compare(b2.getTahunTerbit(), b1.getTahunTerbit())
        );
    }

    public static ArrayList<Book> getAllBooks() {
        return new ArrayList<>(bookList);
    }

    public static void addBook(Book book) {
        bookList.add(0, book);
        sortByYearDesc();
    }

    public static void updateBookLike(int position, boolean isLiked) {
        if (position >= 0 && position < bookList.size()) {
            bookList.get(position).setLiked(isLiked);
        }
    }

    public static void updateBookLikeByPosition(int position, boolean isLiked) {
        if (position >= 0 && position < bookList.size()) {
            bookList.get(position).setLiked(isLiked);
        }
    }

    public static ArrayList<Book> getFavoriteBooks() {
        ArrayList<Book> favorites = new ArrayList<>();
        for (Book b : bookList) {
            if (b.isLiked()) {
                favorites.add(b);
            }
        }
        return favorites;
    }
}