package ch.epfl.bluebrain.nexus.commons.es.client

import ch.epfl.bluebrain.nexus.commons.test.Resources
import ch.epfl.bluebrain.nexus.commons.types.search.QueryResult.ScoredQueryResult
import ch.epfl.bluebrain.nexus.commons.types.search.QueryResults
import ch.epfl.bluebrain.nexus.commons.types.search.QueryResults.{ScoredQueryResults, UnscoredQueryResults}
import io.circe.{Decoder, Json}
import org.scalatest.{Matchers, WordSpecLike}
class ElasticDecoderSpec extends WordSpecLike with Matchers with Resources {

  "A ElasticDecoder" should {
    implicit val D: Decoder[QueryResults[Json]] = ElasticDecoder[Json]

    "decode ElasticSearch response " in {
      val response = jsonContentOf("/elastic_response.json")
      val json1    = Json.obj("key" -> Json.fromString("a"), "key2" -> Json.fromString("b"))
      val json2    = Json.obj("key" -> Json.fromString("c"), "key2" -> Json.fromString("d"))

      response.as[QueryResults[Json]].toOption.get shouldEqual ScoredQueryResults(2L,
                                                                                  1F,
                                                                                  List(ScoredQueryResult(0.5F, json1),
                                                                                       ScoredQueryResult(0.8F, json2)))
    }

    "decode ElasticSearch empty response" in {
      val response = jsonContentOf("/elastic_response_0.json")
      response.as[QueryResults[Json]].toOption.get shouldEqual UnscoredQueryResults(0L, List.empty)
    }
  }
}
