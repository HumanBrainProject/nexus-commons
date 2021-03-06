package ch.epfl.bluebrain.nexus.commons.shacl.topquadrant

import ch.epfl.bluebrain.nexus.commons.test.Resources
import io.circe.Json
import org.apache.jena.rdf.model.{ModelFactory, Resource}
import org.apache.jena.riot.system.StreamRDFLib
import org.apache.jena.riot.{Lang, RDFParser}
import org.scalatest._

class ValidationReportSpec
    extends WordSpecLike
    with Matchers
    with Inspectors
    with Resources
    with OptionValues
    with EitherValues {

  private def resource(json: Json): Resource = {
    val m = ModelFactory.createDefaultModel
    RDFParser.create.fromString(json.noSpaces).base("").lang(Lang.JSONLD).parse(StreamRDFLib.graph(m.getGraph))
    m.createResource()
  }

  "A ValidationReport" should {
    val ctx      = jsonContentOf("/shacl-context-resp.json")
    val conforms = jsonContentOf("/conforms.json")
    val failed   = jsonContentOf("/failed.json")
    "be constructed correctly when conforms" in {
      ValidationReport(resource(conforms deepMerge ctx)).value shouldEqual ValidationReport(true, 1, conforms)
    }

    "be constructed correctly when fails" in {
      val report = ValidationReport(resource(failed deepMerge ctx)).value
      report.conforms shouldEqual false
      report.targetedNodes shouldEqual 1
      report.isValid() shouldEqual false
      val array = report.json.hcursor.downField("result").downField("detail").focus.flatMap(_.asArray).value
      array.map(_.hcursor.get[String]("resultMessage").right.value).sorted shouldEqual Vector(
        "Focus node has 2^^http://www.w3.org/2001/XMLSchema#integer of the shapes from the 'exactly one' list",
        "Value does not have shape http://localhost/v0/schemas/nexus/schemaorg/quantitativevalue/v0.1.0/shapes/QuantitativeValueShape"
      ).sorted
    }
  }
}
