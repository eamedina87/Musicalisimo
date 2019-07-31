package ec.erickmedina.data.remote

class LastFmApiConstants {
    companion object {
        const val param_method = "method"
        const val param_artist = "artist"
        const val param_id = "mbid"
        const val param_api_key = "api_key"
        const val param_format = "format"
        const val param_limit = "limit"
        const val param_page = "page"
        const val param_lang = "lang"

        const val param_method_artist_search = "artist.search"
        const val param_method_artist_top_albums = "artist.gettopalbums"
        const val param_method_album_info = "album.getinfo"
        const val param_format_json = "json"
        const val param_search_limit_default = "30"
        const val param_albums_limit_default = "50"
        const val param_page_default = "1"
    }
}