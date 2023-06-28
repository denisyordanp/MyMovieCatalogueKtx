package com.denisyordanp.mymoviecatalogue.schemas.ui

import com.denisyordanp.mymoviecatalogue.ui.screens.detail.DetailViewState
import com.denisyordanp.mymoviecatalogue.ui.screens.detail.ReviewsViewState
import com.denisyordanp.mymoviecatalogue.ui.screens.detail.VideosViewState

object Dummy {
    fun getGenres() = listOf(
        Genre(
            id = 1,
            name = "Action"
        ),
        Genre(
            id = 2,
            name = "Thriller"
        ),
        Genre(
            id = 3,
            name = "Drama"
        ),
        Genre(
            id = 4,
            name = "Adventure"
        ),
        Genre(
            id = 5,
            name = "Animation"
        ),
        Genre(
            id = 6,
            name = "Comedy"
        ),
        Genre(
            id = 7,
            name = "Crime"
        ),
        Genre(
            id = 8,
            name = "Documentary"
        ),
    )
    fun getMovies() = listOf(
        Movie(
            id = 1,
            overview = "Over many missions and against impossible odds, Dom Toretto and his family have outsmarted, out-nerved and outdriven every foe in their path. Now, they confront the most lethal opponent they've ever faced: A terrifying threat emerging from the shadows of the past who's fueled by blood revenge, and who is determined to shatter this family and destroy everything—and everyone—that Dom loves, forever.",
            posterPath = "/fiVW06jE7z9YnO4trhaMEdclSiC.jpg",
            releaseDate = "2023-05-17",
            title = "Fast X",
            voteAverage = 7.3,
            voteCount = 1998
        ),
        Movie(
            id = 2,
            overview = "Tasked with extracting a family who is at the mercy of a Georgian gangster, Tyler Rake infiltrates one of the world's deadliest prisons in order to save them. But when the extraction gets hot, and the gangster dies in the heat of battle, his equally ruthless brother tracks down Rake and his team to Sydney, in order to get revenge.",
            posterPath = "/7gKI9hpEMcZUQpNgKrkDzJpbnNS.jpg",
            releaseDate = "2023-06-09",
            title = "Extraction 2",
            voteAverage = 7.7,
            voteCount = 801
        ),
    )

    fun getMovieDetail() = MovieDetail(
        id = 1,
        backdropPath = "/e2Jd0sYMCe6qvMbswGQbM0Mzxt0.jpg",
        budget = 340000000,
        genres = getGenres(),
        overview = "Over many missions and against impossible odds, Dom Toretto and his family have outsmarted, out-nerved and outdriven every foe in their path. Now, they confront the most lethal opponent they've ever faced: A terrifying threat emerging from the shadows of the past who's fueled by blood revenge, and who is determined to shatter this family and destroy everything—and everyone—that Dom loves, forever.",
        posterPath = "/fiVW06jE7z9YnO4trhaMEdclSiC.jpg",
        releaseDate = "2023-05-17",
        revenue = 686700000,
        tagline = "The end of the road begins.",
        title = "Fast X",
        voteAverage = 7.283,
        voteCount = 2040
    )

    fun getDetailViewState() = DetailViewState.idle().copy(
        movieDetail = getMovieDetail(),
        reviewsViewState = getReviewsViewState(),
        videosViewState = getVideoViewState()
    )

    fun getReviewsViewState() = ReviewsViewState.idle().copy(
        reviews = getReviews()
    )

    fun getVideoViewState() = VideosViewState.idle().copy(
        videos = getVideos()
    )

    private fun getReviews() = listOf(
        Review(
            id = "123",
            author = "MSB",
            content = "MORE SPOILER-FREE MINI-REVIEWS @ https://www.msbreviews.com/movie-reviews/mini-reviews-2023-edition\\r\\n\\r\\n\\\"Fast X surprisingly pulls me back into a saga I had practically given up on. Jason Momoa is, by far, the MVP of the whole film, transforming an easily forgettable villain into a truly hilarious explosion of charm and *camp*. The cast still holds that enviable chemistry, even though Daniela Melchior and Portugal deserved more screen time.\\r\\n\\r\\nConsidering the previous entries, the energetic action actually turns out to be quite tolerable when it comes to the expected illogical insanity of the franchise - which will please viewers with less patience for absurdity. Despite an overuse of false backdrops and extremely agitated editing, the entertainment levels are well within the acceptable spectrum.\\r\\n\\r\\nIt's clearly the first film in a final trilogy. Interpret this last sentence as you wish.\\\"\\r\\n\\r\\nRating: B-",
            createdAt = "2023-05-18T08:13:12.147Z"
        ),
        Review(
            id = "234",
            author = "Ritesh Mohapatra",
            content = "A vacous and imbecile action potboiler which does what it's meant to do, \\\"Entertain\\\".\\r\\n\\r\\nFast X is a noisy actioner which pushes the limits of inventive action set pieces but this time stays grounded compared to the space shit they tried last time. As a franchise, fast and furious has always been extremely self aware of what it intends to present , over the top, adrenaline pumping , never seen before action and Fast X delivers in that front yet again. The sheer amount of scale and production value gone into this makes this an insane film. While it entertains us throughout because of its action, having an engaging and appealing premise has always been the weakest aspect of this franchise. The story is yet again dumb and feels like an Indian tv soap where no one dies and pops up again and again. \\r\\n\\r\\nThe cast rather than balancing the count keeps getting bigger with each iteration. The best thing the makers have done this time is to integrate Jason Mamoa as Dante and he is maniacal and psychotic. Probably the best villain in the franchise until now. He brings in the much needed wackyness to the film. Alan Ritchson joins the cast and he is brilliant. While rest of the cast remains same and their chemistry adds the elements of humour , Brie Larson's entry doesn't make any sense. Let's hope she springs in a surprise in the next 2 films. The film finishes on a cliffhanger and promises more madness in the next. However dumb it may be, the franchise will be remembered as the one which defies physics in the most realistic way possible.\\r\\n\\r\\nOverall, if you are a fast and furious fan there's no one stopping you, but if you are not then you are missing the out on the most badass franchise coming to a close. I recommend keep your brains at home and go have a blast.\\r\\n\\r\\nInstagram @streamgenx",
            createdAt = "2023-05-19T04:04:52.963Z"
        ),
        Review(
            id = "567",
            author = "davlaga",
            content = "Great movie",
            createdAt = "2023-05-19T04:04:52.963Z"
        ),
        Review(
            id = "567",
            author = "CinemaSerf",
            content = "Starting off with a bit of nostalgia, this high-octane adventure sees just how \\\"Dom\\\" (Vin Diesel) et al become the targets of \\\"Dante\\\" (Jason Momoa). The most improbable of robberies from a police station followed by an even less plausible chase through the streets of Rio dragging a ten ton safe behind their cars! Now I was thinking, this is going to be rubbish. Well, it isn't actually. Though it is pretty repetitive and the story is rather episodic in delivery, it's still got just enough of a story to underpin the collection of lively set-piece car chases that trash just about every city in the world - especially Rome. The camp but deadly Momoa looks like he is having an whale of a time with his role and though the \\\"family\\\" sentiment of the whole thing starts to grate after a while, the production is high standard, the pace is relentless and the visual effects are put to good use for most of it. The rest of the cast deliver adequately enough - I could have been doing with a bit more from \\\"Cipher\\\" (Charlize Theron) and a lot less from the double act of \\\"Tej\\\" (Ludacris) and \\\"Roman\\\" (Tyrese Gibson) that got on my nerves a bit after a while. From a plot perspective, it's an introductory offer - they are setting the scene for the next one (or two) that will doubtlessly be a spectacular denouement, tinged with tragedy, that probably only leaves Nepal as a country untouched by their wrecking! It's harmless fun. It won't challenge you one little bit, the acting and writing are largely forgettable and it will slot into the library of the rest of this franchise easily enough. If you don't know what you are going to get from the series by now, then you ought not to bother.",
            createdAt = "2023-05-22T09:20:59.008Z"
        ),
        Review(
            id = "567",
            author = "MovieGuys",
            content = "The very definition of moronic. \\r\\n\\r\\nLoud, obnoxious, cheesy characterisations, idiotic back story  and enough bling to make you wince.  \\r\\n\\r\\nThe only vaguely redeeming quality is the polish and money spent on the over the top, at times novel, action scenes but that's not really saying much, in the wider context of awfulness, this represents.\\r\\n\\r\\nI'm not sure who the demographic for this flick is? I would have thought people were more evolved, as a species but well.....\\r\\n\\r\\nIn summary, just dumb, that's really all needs be said.....",
            createdAt = "2023-06-01T22:53:16.005Z"
        )
    )

    private fun getVideos() = listOf(
        Video(
            id = "12",
            key = "xZd6Kxg_MDQ",
            name = "The Scene that Started the Road to Revenge",
            publishedAt = "2023-06-23T17:51:17.000Z"
        ),
        Video(
            id = "13",
            key = "SP_h-m2vmv0",
            name = "Every Fast & Furious Film Explained | Movies 1-9 Recap | Watch Before Fast X",
            publishedAt = "2023-06-13T14:20:10.000Z"
        ),
        Video(
            id = "14",
            key = "A3GBDEE3zxY",
            name = "Time Moves Fast",
            publishedAt = "2023-05-26T22:50:56.000Z"
        ),
    )
}
