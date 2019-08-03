package ec.erickmedina.data.utils

import com.google.common.truth.Truth
import ec.erickmedina.data.entity.LastFmResponses

class UtilsAssertion {

    companion object {

        fun assertArtist(artist: LastFmResponses.Artist) {
            with(artist) {
                Truth.assertThat(name).isNotEmpty()
                Truth.assertThat(url).isNotEmpty()
                Truth.assertThat(listeners).isAtLeast(0)
                Truth.assertThat(streamable).isAtLeast(0)
                Truth.assertThat(image).isNotEmpty()
                image.forEach { img ->
                    Truth.assertThat(img).isNotNull()
                    Truth.assertThat(img.text).isNotEmpty()
                    Truth.assertThat(img.size).isNotEmpty()
                }
            }
        }

        fun assertTopAlbum(album: LastFmResponses.TopAlbum) {
            with(album) {
                Truth.assertThat(name).isNotEmpty()
                Truth.assertThat(playcount).isAtLeast(0)
                Truth.assertThat(url).isNotEmpty()
                Truth.assertThat(artist).isNotNull()
                Truth.assertThat(artist.name).isNotEmpty()
                Truth.assertThat(artist.mbid).isNotEmpty()
                Truth.assertThat(artist.url).isNotEmpty()
                image.forEach { image ->
                    Truth.assertThat(image).isNotNull()
                    Truth.assertThat(image.text).isNotNull()
                    Truth.assertThat(image.size).isNotNull()
                }
            }
        }

        fun assertAlbum(album: LastFmResponses.Album) {
            with(album) {
                Truth.assertThat(name).isNotEmpty()
                Truth.assertThat(artist).isNotEmpty()
                Truth.assertThat(mbid).isNotEmpty()
                Truth.assertThat(url).isNotEmpty()
                Truth.assertThat(listeners).isAtLeast(0)
                Truth.assertThat(playcount).isAtLeast(0)
                Truth.assertThat(image).isNotEmpty()
                image.forEach {
                    Truth.assertThat(it).isNotNull()
                    Truth.assertThat(it.text).isNotEmpty()
                    Truth.assertThat(it.size).isNotNull()
                }
                Truth.assertThat(tracks).isNotNull()
                Truth.assertThat(tracks.track).isNotEmpty()
                tracks.track.forEach {
                    Truth.assertThat(it).isNotNull()
                    with(it) {
                        Truth.assertThat(name).isNotEmpty()
                        Truth.assertThat(artist).isNotNull()
                        Truth.assertThat(mbid).isNotEmpty()
                        Truth.assertThat(url).isNotEmpty()
                        Truth.assertThat(attr).isNotEmpty()
                    }
                }
                Truth.assertThat(tags).isNotNull()
                Truth.assertThat(tags.tag).isNotNull()
                tags.tag.forEach {
                    Truth.assertThat(it).isNotNull()
                    with(it) {
                        Truth.assertThat(name).isNotEmpty()
                        Truth.assertThat(url).isNotEmpty()
                    }
                }
                Truth.assertThat(wiki).isNotNull()
                with(wiki) {
                    Truth.assertThat(content).isNotEmpty()
                    Truth.assertThat(published).isNotEmpty()
                    Truth.assertThat(summary).isNotEmpty()
                }
            }
        }

    }



}