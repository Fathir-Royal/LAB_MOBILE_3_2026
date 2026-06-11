package com.example.libraryapp.data;

import com.example.libraryapp.R;
import com.example.libraryapp.model.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class DataManager {
    private static final List<Book> books = new ArrayList<>();
    private static boolean initialized = false;

    public static void initDummyData() {
        if (initialized) return;

        int c = R.drawable.ic_book_placeholder;

        books.add(new Book("Atomic Habits","James Clear",2018,"Panduan kebiasaan kecil berdampak besar.",drawableUri(c),false,4.8f,"Self-Help","Praktis dan memotivasi."));
        books.add(new Book("Clean Code","Robert C. Martin",2008,"Prinsip menulis kode bersih.",drawableUri(c),true,4.7f,"Technology","Wajib untuk developer."));
        books.add(new Book("The Alchemist","Paulo Coelho",1988,"Perjalanan spiritual mencari makna.",drawableUri(c),false,4.5f,"Fiction","Inspiratif."));
        books.add(new Book("Dune","Frank Herbert",1965,"Epik sci-fi tentang Arrakis.",drawableUri(c),true,4.6f,"Sci-Fi","World-building kuat."));
        books.add(new Book("Laut Bercerita","Leila S. Chudori",2017,"Kisah kehilangan dan perjuangan.",drawableUri(c),false,4.9f,"Historical","Emosional."));
        books.add(new Book("Sapiens","Yuval N. Harari",2011,"Sejarah umat manusia.",drawableUri(c),true,4.7f,"Non-Fiction","Membuka perspektif."));
        books.add(new Book("Negeri 5 Menara","Ahmad Fuadi",2009,"Persahabatan dan mimpi besar.",drawableUri(c),false,4.4f,"Drama","Hangat."));
        books.add(new Book("The Hobbit","J.R.R. Tolkien",1937,"Petualangan Bilbo.",drawableUri(c),true,4.8f,"Fantasy","Klasik."));
        books.add(new Book("Educated","Tara Westover",2018,"Memoar perjuangan pendidikan.",drawableUri(c),false,4.6f,"Biography","Kuat."));
        books.add(new Book("Thinking, Fast and Slow","Daniel Kahneman",2011,"Dua sistem berpikir.",drawableUri(c),false,4.5f,"Psychology","Insightful."));
        books.add(new Book("The Psychology of Money","Morgan Housel",2020,"Perilaku keuangan manusia.",drawableUri(c),true,4.7f,"Finance","Relatable."));
        books.add(new Book("Bumi","Tere Liye",2014,"Petualangan dunia paralel.",drawableUri(c),false,4.5f,"Fantasy","Seru."));
        books.add(new Book("Norwegian Wood","Haruki Murakami",1987,"Cinta dan kehilangan.",drawableUri(c),false,4.3f,"Romance","Puitis."));
        books.add(new Book("Project Hail Mary","Andy Weir",2021,"Misi menyelamatkan bumi.",drawableUri(c),true,4.9f,"Sci-Fi","Cerdas dan menegangkan."));
        books.add(new Book("Rich Dad Poor Dad","Robert T. Kiyosaki",1997,"Pola pikir finansial.",drawableUri(c),false,4.2f,"Finance","Mudah dipahami."));

        initialized = true;
        sortByLatest();
    }

    public static List<Book> getAllBooks() {
        sortByLatest();
        return books;
    }

    public static void addBook(Book book) {
        books.add(book);
        sortByLatest();
    }

    public static Book getBookById(String id) {
        for (Book b : books) {
            if (b.getId().equals(id)) return b;
        }
        return null;
    }

    public static List<Book> getFavoriteBooks() {
        List<Book> out = new ArrayList<>();
        for (Book b : books) if (b.isLiked()) out.add(b);
        out.sort((a, b) -> Integer.compare(b.getPublishYear(), a.getPublishYear()));
        return out;
    }

    public static List<Book> filterByGenre(String genre) {
        if (genre == null || genre.equalsIgnoreCase("All")) return new ArrayList<>(getAllBooks());
        List<Book> out = new ArrayList<>();
        for (Book b : books) if (b.getGenre().equalsIgnoreCase(genre)) out.add(b);
        out.sort((a, b) -> Integer.compare(b.getPublishYear(), a.getPublishYear()));
        return out;
    }

    public static List<String> getAllGenres() {
        List<String> genres = new ArrayList<>();
        genres.add("All");
        for (Book b : books) {
            if (!genres.contains(b.getGenre())) genres.add(b.getGenre());
        }
        return genres;
    }

    public static List<Book> searchByTitle(String keyword) {
        String q = keyword == null ? "" : keyword.toLowerCase(Locale.getDefault());
        List<Book> out = new ArrayList<>();
        for (Book b : books) {
            if (b.getTitle().toLowerCase(Locale.getDefault()).contains(q)) out.add(b);
        }
        out.sort((a, b) -> Integer.compare(b.getPublishYear(), a.getPublishYear()));
        return out;
    }

    private static void sortByLatest() {
        Collections.sort(books, (a, b) -> Integer.compare(b.getPublishYear(), a.getPublishYear()));
    }

    private static String drawableUri(int resId) {
        return "android.resource://com.example.libraryapp/" + resId;
    }
}