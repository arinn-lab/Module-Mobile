package com.example.songpediacompose.data

import android.content.Context
import com.example.songpediacompose.R
import com.example.songpediacompose.Song

object SongData {
    fun getSongs(context: Context): List<Song> {
        return listOf(
            Song(
                id = 1,
                title = "Should Have Known Better",
                artist = "Sufjan Stevens",
                album = "Carrie & Lowell",
                year = 2015,
                genre = "Folk",
                descriptionEn = "A deeply personal song about grief and guilt following the death of his estranged mother. It shifts from sorrow into quiet hope.",
                descriptionId = "Lagu yang sangat personal tentang duka dan rasa bersalah setelah kematian ibunya yang telah lama terpisah. Perlahan bergerak dari kesedihan menuju harapan yang tenang.",
                imageRes = R.drawable.albumcarrieandlowell,
                spotifyUrl = "https://open.spotify.com/track/1iSI5NeLqGoTsvTk7PA5pY"
            ),
            Song(
                id = 2,
                title = "How to Disappear",
                artist = "Lana Del Rey",
                album = "Norman Fucking Rockwell!",
                year = 2019,
                genre = "Dream Pop",
                descriptionEn = "A reflective track about emotionally unavailable past lovers and learning to let go, ending with a sense of peace and self-assurance.",
                descriptionId = "Lagu reflektif tentang mantan kekasih yang tidak hadir secara emosional, dan proses belajar melepaskan, diakhiri dengan rasa damai pada diri sendiri.",
                imageRes = R.drawable.albumnormanfuckingrockwell,
                spotifyUrl = "https://open.spotify.com/track/4qUtC2BwFC154Ha8YQRrkk"
            ),
            Song(
                id = 3,
                title = "Like Real People Do",
                artist = "Hozier",
                album = "From Eden EP",
                year = 2014,
                genre = "Indie Folk",
                descriptionEn = "Inspired by Irish poet Seamus Heaney, the song is about two imperfect people choosing simple, honest intimacy over their complicated pasts.",
                descriptionId = "Terinspirasi dari puisi Seamus Heaney, lagu ini tentang dua orang yang tidak sempurna memilih keintiman sederhana dan jujur daripada masa lalu mereka yang rumit.",
                imageRes = R.drawable.albumfromeden,
                spotifyUrl = "https://open.spotify.com/track/4LGJ2pLDvTRnul3EcZoYkX"
            ),
            Song(
                id = 4,
                title = "The Moon Will Sing",
                artist = "The Crane Wives",
                album = "Coyote Stories",
                year = 2015,
                genre = "Indie Folk",
                descriptionEn = "A bittersweet song about losing one's identity in a relationship, using the moon and sun as a metaphor for emotional dependency.",
                descriptionId = "Lagu bittersweet tentang kehilangan jati diri dalam sebuah hubungan, menggunakan bulan dan matahari sebagai metafora untuk ketergantungan emosional.",
                imageRes = R.drawable.albumherons,
                spotifyUrl = "https://open.spotify.com/track/4v0Zqmuhlbt5nCj712Y26n"
            ),
            Song(
                id = 5,
                title = "Duvet",
                artist = "Bôa",
                album = "Twilight",
                year = 2001,
                genre = "Alternative Rock",
                descriptionEn = "Originally about communication and relying on others, the song became iconic as the opening theme of the anime Serial Experiments Lain.",
                descriptionId = "Awalnya tentang komunikasi dan ketergantungan pada orang lain, lagu ini menjadi ikonik sebagai lagu pembuka anime Serial Experiments Lain.",
                imageRes = R.drawable.albumtwilight,
                spotifyUrl = "https://open.spotify.com/track/42qNWdLKCI41S4uzfamhFM"
            ),
            Song(
                id = 6,
                title = "Please Be Rude",
                artist = "Gigi Perez",
                album = "Please Be Rude",
                year = 2024,
                genre = "Indie Pop",
                descriptionEn = "A soft and vulnerable song about the longing for genuine intimacy and the desire to be seen honestly in a romantic relationship.",
                descriptionId = "Lagu yang lembut dan penuh kerentanan tentang kerinduan akan keintiman sejati dan keinginan untuk dilihat apa adanya dalam sebuah hubungan romantis.",
                imageRes = R.drawable.albumpleaseberude,
                spotifyUrl = "https://open.spotify.com/track/2ORD5RHyZI9LGibgqcwr3v"
            ),
            Song(
                id = 7,
                title = "Buzzcut Season",
                artist = "Lorde",
                album = "Pure Heroine",
                year = 2013,
                genre = "Electropop",
                descriptionEn = "A deceptively calm song about teenage escapism and blissful ignorance, while quietly acknowledging the darkness of the outside world.",
                descriptionId = "Lagu yang terdengar tenang namun menyimpan makna dalam tentang pelarian remaja dan kebahagiaan yang naif, sambil diam-diam mengakui kegelapan dunia luar.",
                imageRes = R.drawable.albumpureheroine,
                spotifyUrl = "https://open.spotify.com/track/51QEyJI5M7uyd8DOh9tqQY"
            )
        )
    }
}