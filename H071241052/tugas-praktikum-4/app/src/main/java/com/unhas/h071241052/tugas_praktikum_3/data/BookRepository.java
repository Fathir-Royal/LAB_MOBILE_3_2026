package com.unhas.h071241052.tugas_praktikum_3.data;

import com.unhas.h071241052.tugas_praktikum_3.R;
import com.unhas.h071241052.tugas_praktikum_3.model.Book;
import java.util.ArrayList;

public class BookRepository {
    private static BookRepository instance;
    private ArrayList<Book> books;

    private BookRepository() {
        books = new ArrayList<>();
        generateDummyData();
    }

    public static synchronized BookRepository getInstance() {
        if (instance == null) {
            instance = new BookRepository();
        }
        return instance;
    }

    private void generateDummyData() {
        // Menambahkan 15 dummy buku sesuai daftar di drawable Anda
        books.add(new Book("1", "Twilight", "Stephenie Meyer", "2005", "follows 17-year-old Bella Swan as she moves to rainy Forks, Washington, to live with her father. She becomes captivated by Edward Cullen, a mysterious classmate who is actually a 103-year-old vampire. Their intense, forbidden romance faces danger from a tracker vampire named James", String.valueOf(R.drawable.buku_twilight)));
        books.add(new Book("2", "New Moon", "Stephenie Meyer", "2006", "The second book in Twilight Saga...", String.valueOf(R.drawable.buku_new_moon)));
        books.add(new Book("3", "Eclipse", "Stephenie Meyer", "2007", "The third book in Twilight Saga...", String.valueOf(R.drawable.buku_eclipse)));
        books.add(new Book("4", "Breaking Dawn", "Stephenie Meyer", "2008", "The final book in Twilight Saga...", String.valueOf(R.drawable.buku_breaking_dawn)));
        books.add(new Book("5", "Bumi", "Tere Liye", "2014", "Petualangan Raib di dunia paralel...", String.valueOf(R.drawable.buku_bumi)));
        books.add(new Book("6", "Bulan", "Tere Liye", "2015", "Petualangan di klan Bulan...", String.valueOf(R.drawable.buku_bulan)));
        books.add(new Book("7", "Matahari", "Tere Liye", "2016", "Petualangan di klan Matahari...", String.valueOf(R.drawable.buku_matahari)));
        books.add(new Book("8", "Hujan", "Tere Liye", "2016", "Tentang persahabatan dan perpisahan...", String.valueOf(R.drawable.buku_hujan)));
        books.add(new Book("9", "Pergi", "Tere Liye", "2018", "Lanjutan kisah Bujang...", String.valueOf(R.drawable.buku_pergi)));
        books.add(new Book("10", "Bumi Manusia", "Pramoedya Ananta Toer", "1980", "Kisah Minke dan Annelies...", String.valueOf(R.drawable.buku_bumi_manusia)));
        books.add(new Book("11", "Laut Bercerita", "Leila S. Chudori", "2017", "Kisah perjuangan aktivis 98...", String.valueOf(R.drawable.buku_laut_bercerita)));
        books.add(new Book("12", "Rindu", "Tere Liye", "2014", "Perjalanan kapal Blitar...", String.valueOf(R.drawable.buku_rindu)));
        books.add(new Book("13", "The Little Prince", "Antoine de Saint-Exupéry", "1943", "A young prince visits various planets...", String.valueOf(R.drawable.buku_the_little_prince)));
        books.add(new Book("14", "Dunia Sophie", "Jostein Gaarder", "1991", "Sejarah filsafat dalam novel...", String.valueOf(R.drawable.buku_dunia_sophie)));
        books.add(new Book("15", "Komet Minor", "Tere Liye", "2019", "Pertarungan akhir melawan Si Tanpa Mahkota...", String.valueOf(R.drawable.buku_komet_minor)));
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(0, book);
    }

    public ArrayList<Book> getFavoriteBooks() {
        ArrayList<Book> favorites = new ArrayList<>();
        for (Book book : books) {
            if (book.isFavorite()) {
                favorites.add(book);
            }
        }
        return favorites;
    }
}