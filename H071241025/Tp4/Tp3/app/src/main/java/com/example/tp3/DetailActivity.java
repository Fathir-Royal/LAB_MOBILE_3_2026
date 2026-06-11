package com.example.tp3;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private int bookPosition;
    private boolean isLiked;
    private ImageView ivCover;
    private TextView tvJudul, tvPenulis, tvTahun, tvGenre, tvBlurb, tvRating;
    private Button btnLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivCover = findViewById(R.id.iv_cover_detail);
        tvJudul = findViewById(R.id.tv_judul_detail);
        tvPenulis = findViewById(R.id.tv_penulis_detail);
        tvTahun = findViewById(R.id.tv_tahun_detail);
        tvGenre = findViewById(R.id.tv_genre_detail);
        tvBlurb = findViewById(R.id.tv_blurb_detail);
        tvRating = findViewById(R.id.tv_rating_value);
        btnLike = findViewById(R.id.btn_like_detail);

        bookPosition = getIntent().getIntExtra("position", -1);
        String judul = getIntent().getStringExtra("judul");
        String penulis = getIntent().getStringExtra("penulis");
        int tahun = getIntent().getIntExtra("tahun", 0);
        String blurb = getIntent().getStringExtra("blurb");
        String genre = getIntent().getStringExtra("genre");
        isLiked = getIntent().getBooleanExtra("isLiked", false);
        String gambarUriString = getIntent().getStringExtra("gambarUri");


        tvJudul.setText(judul);
        tvPenulis.setText("Penulis: " + penulis);
        tvTahun.setText("Tahun Terbit: " + tahun);
        tvGenre.setText("Genre: " + genre);
        tvBlurb.setText(blurb);
        tvRating.setText(getRatingByBook(judul));

        setRealisticReviews(judul);

        if (gambarUriString != null && !gambarUriString.isEmpty()) {
            ivCover.setImageURI(Uri.parse(gambarUriString));
        }

        updateLikeButton();

        btnLike.setOnClickListener(v -> {
            isLiked = !isLiked;
            updateLikeButton();

            ArrayList<Book> allBooks = DataDummy.getAllBooks();
            if (bookPosition >= 0 && bookPosition < allBooks.size()) {
                allBooks.get(bookPosition).setLiked(isLiked);
            }
        });
    }

    private void updateLikeButton() {
        if (isLiked) {
            btnLike.setText("Liked");
            btnLike.setBackgroundTintList(getColorStateList(android.R.color.holo_green_dark));
        } else {
            btnLike.setText("Like");
            btnLike.setBackgroundTintList(getColorStateList(android.R.color.holo_orange_dark));
        }
    }

    private String getRatingByBook(String judul) {
        if (judul == null) return "4.0";

        switch (judul) {
            case "7 Prajurit Bapak":
                return "4.8";
            case "Cantik Itu Luka":
                return "4.7";
            case "The Devotion of Suspect X (Catatan Pembunuhan)":
                return "4.9";
            case "Dompet Ayah Sepatu Ibu":
                return "4.6";
            case "Hilmy Milan":
                return "4.5";
            case "Hujan":
                return "4.8";
            case "Welcome to Hyunam-dong Bookshop":
                return "4.7";
            case "Laut Bercerita":
                return "4.9";
            case "The Midnight Library":
                return "4.8";
            case "Keajaiban Toko Kelontong Namiya":
                return "4.7";
            case "Pulang-Pergi":
                return "4.6";
            case "Seporsi Mie Ayam Sebelum Mati":
                return "4.5";
            case "Sisi Tergelap Surga":
                return "4.6";
            case "Di Tanah Lada":
                return "4.7";
            case "Strange House (Teka-Teki Rumah Aneh)":
                return "4.4";
            default:
                return "4.0";
        }
    }

    private String getReviewByBook(String judul) {
        if (judul == null) return "Buku yang menarik untuk dibaca.";

        switch (judul) {
            case "7 Prajurit Bapak":
                return "Cerita yang sangat mengharukan dan menginspirasi! Menggambarkan perjuangan seorang ayah membesarkan 7 anaknya dengan penuh kasih sayang dan disiplin. Buku ini bikin saya terharu dan bersyukur memiliki orang tua yang luar biasa. Wajib baca buat semua anak!";

            case "Cantik Itu Luka":
                return "Eka Kurniawan menghadirkan mahakarya yang brutal namun puitis. Kisah Dewi Ayu yang bangkit dari kematian adalah metafora sempurna tentang sejarah kelam Indonesia. Gaya penulisan yang unik, penuh ironi, dan kritik sosial yang tajam. Buku yang melekat di ingatan!";

            case "The Devotion of Suspect X (Catatan Pembunuhan)":
                return "Keigo Higashino adalah jenius! Plotnya begitu rumit namun terangkai sempurna. Duel intelektual antara matematikawan jenius dan fisikawan detektif bikin saya tidak bisa berhenti membaca. Twist ending-nya benar-benar di luar dugaan!";

            case "Dompet Ayah Sepatu Ibu":
                return "J.S. Khairen berhasil membuat saya menangis. Cerita sederhana tapi menyentuh hati tentang pengorbanan orang tua. Setiap babaknya mengingatkan kita betapa besar cinta mereka yang sering tidak kita sadari. Buku yang hangat dan penuh makna.";

            case "Hilmy Milan":
                return "Cerita romantis yang manis dengan latar keluarga mafia yang unik. Milan dan Hilmy punya chemistry yang bikin gemes. Konfliknya ringan tapi menghibur. Cocok buat yang suka bacaan romantis ringan tapi tetap bermakna.";

            case "Hujan":
                return "Tere Liye kembali with cerita yang puitis and menyayat hati. Dunia pasca bencana yang kelam namun masih ada secercah harapan. Kisah cinta Lail and Esok bikin saya terharu sampai menitikkan air mata. Buku yang sempurna!";

            case "Welcome to Hyunam-dong Bookshop":
                return "Buku healing yang paling recomended! Rasanya seperti dipeluk hangat oleh toko buku kecil yang nyaman. Setiap karakter punya cerita yang relatable. Bikin saya ingin punya toko buku sendiri and menemukan makna hidup yang sebenarnya.";

            case "Laut Bercerita":
                return "Leila S. Chudori berhasil menggambarkan masa kelam reformasi dengan sangat apik. Kisah aktivis yang hilang begitu nyata and menyayat hati. Prosa yang indah namun penuh luka. Buku ini bikin saya merenung tentang arti keadilan and pengorbanan.";

            case "The Midnight Library":
                return "Matt Haig mengajarkan bahwa tidak ada kehidupan yang sempurna. Setiap pilihan punya konsekuensi masing-masing. Buku ini mengubah cara pandang saya tentang penyesalan and kebahagiaan. Bacaan wajib bagi yang sedang kehilangan arah hidup!";

            case "Keajaiban Toko Kelontong Namiya":
                return "Keigo Higashino yang berbeda dari biasanya - lebih hangat and penuh haru. Konsep toko yang bisa mengirim surat ke masa lalu sangat unik. Setiap cerita saling terhubung dengan indah. Bikin saya percaya bahwa semua orang terhubung dengan cara yang misterius.";

            case "Pulang-Pergi":
                return "Tere Liye menghadirkan petualangan seru di dunia paralel! Aksi yang menegangkan, konflik yang kompleks, and plot twist yang tak terduga. Karakternya kuat and penuh kejutan. Bikin ketagihan dari bab pertama sampai terakhir!";

            case "Seporsi Mie Ayam Sebelum Mati":
                return "Brian Khrisna mengajak kita merenung tentang hal-hal sederhana dalam hidup. Mie ayam sebagai metafora kehidupan yang sering kita anggap remeh. Buku pendek tapi penuh makna. Bikin saya ingin lebih menghargai setiap momen kecil dalam hidup.";

            case "Sisi Tergelap Surga":
                return "Mengupas sisi gelap manusia dengan jujur and tanpa basa-basi. Brian Khrisna berhasil membuat saya merenung tentang hal-hal yang sering kita sembunyikan. Cerita yang kelam tapi realistis. Bacaan yang heavy tapi worth it!";

            case "Di Tanah Lada":
                return "Ziggy Zezsyazeoviennazabrizkie (coba deh inget nama penulisnya 😂) menulis dari sudut pandang anak yang unik and menggemaskan meski ceritanya kelam. Gaya penulisan yang khas, absurd tapi menyentuh. Buku yang berbeda dari yang lain!";

            case "Strange House (Teka-Teki Rumah Aneh)":
                return "Uketsu menghadirkan misteri horror yang bikin merinding! Desain rumah yang aneh and rahasia kelamnya perlahan terungkap dengan sempurna. Plot twist-nya bikin saya bergidik. Cocok buat yang suka thriller misterius!";

            default:
                return "Buku yang menarik and layak untuk dibaca. Memberikan banyak pelajaran and pengalaman berharga. Rekomended banget!";
        }
    }

    private void setRealisticReviews(String judul) {
        String review1 = "";
        String review2 = "";
        String reviewer1 = "";
        String reviewer2 = "";
        float rating1 = 5;
        float rating2 = 4;
        String date1 = "Minggu lalu";
        String date2 = "Bulan lalu";

        if (judul != null) {
            switch (judul) {
                case "7 Prajurit Bapak":
                    review1 = "\"Buku ini bikin nangis sepanjang baca! Cerita tentang perjuangan seorang ayah yang luar biasa. Saya jadi ingat sama bapak saya sendiri. Wajib baca buat yang mau belajar menghargai orang tua. 5 bintang!\"";
                    review2 = "\"Karakter anak-anaknya kuat banget. Meskipun banyak konflik, pesan tentang keluarga tetap terasa hangat. Recommended!\"";
                    reviewer1 = "Dewi Lestari";
                    reviewer2 = "Andi Wijaya";
                    rating1 = 5;
                    rating2 = 4;
                    date1 = "3 hari lalu";
                    date2 = "2 minggu lalu";
                    break;

                case "Cantik Itu Luka":
                    review1 = "\"Gila sih buku ini! Brutal tapi puitis. Eka Kurniawan berhasil bikin saya merinding dari awal sampai akhir. Dewi Ayu adalah karakter perempuan paling ikonik yang pernah saya baca.\"";
                    review2 = "\"Awalnya susah masuk, tapi setelah paham gaya bahasanya, jadi candu. Banyak adegan yang nggak bakal saya lupain. 4.5 dari saya!\"";
                    reviewer1 = "Rania F.";
                    reviewer2 = "Bagas P.";
                    rating1 = 5;
                    rating2 = 5;
                    date1 = "1 bulan lalu";
                    date2 = "3 bulan lalu";
                    break;

                case "The Devotion of Suspect X (Catatan Pembunuhan)":
                    review1 = "\"PLOT TWIST NYA GILA! Saya sampe teriak 'ASTAGA' pas baca endingnya. Higashino emang jenius. Rekomendasi buat pecinta thriller!\"";
                    review2 = "\"Duel intelektual antara Ishigami and Yukawa bikin saya gregetan. Nggak nyangka penjahatnya ternyata... (gak boleh spoiler). Pokoknya wajib baca!\"";
                    reviewer1 = "Nadia K.";
                    reviewer2 = "Reza M.";
                    rating1 = 5;
                    rating2 = 5;
                    date1 = "5 hari lalu";
                    date2 = "minggu lalu";
                    break;

                case "Hujan":
                    review1 = "\"Tere Liye selalu berhasil bikin saya nangis. Kisah Lail and Esok di dunia pasca apokalips ini romantis sekaligus heartbreaking. Baca sambil siapin tisu!\"";
                    review2 = "\"Awalnya santai, tapi makin ke tengah makin emosional. Akhir ceritanya... (masih terharu). Layak dapat 4.8!\"";
                    reviewer1 = "Sarah A.";
                    reviewer2 = "Dian P.";
                    rating1 = 5;
                    rating2 = 4;
                    date1 = "2 hari lalu";
                    date2 = "1 bulan lalu";
                    break;

                case "Laut Bercerita":
                    review1 = "\"Membaca ini seperti mengupas luka lama Indonesia. Leila S. Chudori menulis dengan sangat indah tapi menyakitkan. Buku yang membuat saya merenung berhari-hari.\"";
                    review2 = "\"Kisah aktivis 98 yang hilang begitu nyata. Saya nggak bisa berhenti mikirin nasib mereka setelah tutup buku. 5 bintang!\"";
                    reviewer1 = "Amira H.";
                    reviewer2 = "Gilang R.";
                    rating1 = 5;
                    rating2 = 5;
                    date1 = "2 minggu lalu";
                    date2 = "1 bulan lalu";
                    break;

                case "The Midnight Library":
                    review1 = "\"Buku ini mengubah cara pandang saya tentang penyesalan. Setiap bab bikin saya mikir 'oh iya ya, hidup gue nggak seburuk itu'. Healing banget!\"";
                    review2 = "\"Matt Haig paham banget sama orang yang lagi down. Buku ini kayak pelukan hangat buat jiwa yang lelah. Recommended!\"";
                    reviewer1 = "Maya S.";
                    reviewer2 = "Kevin T.";
                    rating1 = 5;
                    rating2 = 4;
                    date1 = "3 hari lalu";
                    date2 = "minggu lalu";
                    break;


                default:
                    review1 = "\"Buku yang keren banget! Ceritanya seru and nggak ngebosenin. Bikin saya pengen baca ulang lagi. Wajib masuk wishlist!\"";
                    review2 = "\"Awalnya biasa aja, tapi makin ke tengah makin greget. Recommended buat yang suka genre ini. 4 bintang dari saya!\"";
                    reviewer1 = "Pembaca Setia";
                    reviewer2 = "Buku Mania";
                    rating1 = 5;
                    rating2 = 4;
                    break;
            }
        }

        TextView tvReviewer1 = findViewById(R.id.tv_reviewer_name_1);
        TextView tvReviewDate1 = findViewById(R.id.tv_review_date_1);
        TextView tvReviewContent1 = findViewById(R.id.tv_review_content_1);
        RatingBar rbReviewRating1 = findViewById(R.id.rb_review_rating_1);

        TextView tvReviewer2 = findViewById(R.id.tv_reviewer_name_2);
        TextView tvReviewDate2 = findViewById(R.id.tv_review_date_2);
        TextView tvReviewContent2 = findViewById(R.id.tv_review_content_2);
        RatingBar rbReviewRating2 = findViewById(R.id.rb_review_rating_2);

        if (tvReviewer1 != null) {
            tvReviewer1.setText(reviewer1);
            tvReviewDate1.setText(date1);
            tvReviewContent1.setText(review1);
            rbReviewRating1.setRating(rating1);

            tvReviewer2.setText(reviewer2);
            tvReviewDate2.setText(date2);
            tvReviewContent2.setText(review2);
            rbReviewRating2.setRating(rating2);
        }
    }
}
