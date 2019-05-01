package io.github.ovso.globaltrend.api.model

import com.google.gson.JsonElement

data class CustomSearch(
  var context: Context,
  var items: List<Item>,
  var kind: String,
  var queries: Queries,
  var searchInformation: SearchInformation,
  var url: Url
)

data class Url(
  var template: String,
  var type: String
)

data class SearchInformation(
  var formattedSearchTime: String,
  var formattedTotalResults: String,
  var searchTime: Double,
  var totalResults: String
)

data class Queries(
  var nextPage: List<NextPage>,
  var request: List<Request>
)

data class NextPage(
  var count: Int,
  var cx: String,
  var inputEncoding: String,
  var outputEncoding: String,
  var safe: String,
  var searchTerms: String,
  var startIndex: Int,
  var title: String,
  var totalResults: String
)

data class Context(
  var title: String
)

data class Request(
  var count: Int,
  var cx: String,
  var inputEncoding: String,
  var outputEncoding: String,
  var safe: String,
  var searchTerms: String,
  var startIndex: Int,
  var title: String,
  var totalResults: String
)

data class Pagemap(
  var cse_image: List<CseImage>,
  var cse_thumbnail: List<CseThumbnail>?,
  var metatags: JsonElement
)

data class Item(
  var displayLink: String,
  var formattedUrl: String,
  var htmlFormattedUrl: String,
  var htmlSnippet: String,
  var htmlTitle: String,
  var kind: String,
  var link: String,
  var pagemap: Pagemap?,
  var snippet: String,
  var title: String
)

data class CseThumbnail(
  var height: String,
  var src: String,
  var width: String
)

data class CseImage(
  var src: String
)