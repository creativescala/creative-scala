import cats.data._
import cats.implicits._
import laika.ast._
import laika.directive._

object CreativeScalaDirectives extends DirectiveRegistry {

  override val description: String =
    "Directive to work with CreativeScala SVG pictures."

  val divWithId: Blocks.Directive =
    Blocks.create("divWithId") {
      import Blocks.dsl._

      attribute(0)
        .as[String]
        .map { (id) =>
          RawContent(
            NonEmptySet.one("html"),
            s"""<div id="${id}"></div>"""
          )
        }
    }

  // Parameters are id and then JS function to call
  val doodle: Blocks.Directive =
    Blocks.create("doodle") {
      import Blocks.dsl._

      (attribute(0).as[String], attribute(1).as[String], cursor)
        .mapN { (id, js, _) =>
          BlockSequence(
            Seq(
              RawContent(
                NonEmptySet.one("html"),
                s"""<div id="${id}"></div>"""
              ),
              RawContent(
                NonEmptySet.one("html"),
                s"""<script>${js}("${id}")</script>"""
              )
            )
          )
        }
    }

  // Insert a figure (image)
  //
  // Parameters:
  // filename: String. The file name of the image
  // key: String. The name of the reference for this image
  // caption: String. The caption for this image
  val figure: Blocks.Directive =
    Blocks.create("figure") {
      import Blocks.dsl._

      (
        attribute("img").as[String].widen,
        attribute("key").as[String].optional,
        attribute("caption").as[String]
      ).mapN { (img, key, caption) =>
        Paragraph(
          Image(
            Target.parse(img),
            None,
            None,
            Some(caption),
            Some(s"Figure $key: caption")
          )
        )
      }
    }

  val footnote: Blocks.Directive =
    Blocks.create("footnote") {
      import Blocks.dsl._

      (attribute(0).as[String], parsedBody).mapN { (id, body) =>
        Footnote(id, body)
      }
    }

  // Insert a reference to a figure
  //
  // Parameters:
  // key: String. The name of the figure being referred to.
  val fref: Spans.Directive =
    Spans.create("fref") {
      import Spans.dsl._

      (attribute(0).as[String]).map { (key) => Text(s"Figure $key") }
    }
  //
  // Insert a reference to a footnote
  //
  // Parameters:
  // key: String. The name of the footnote being referred to.
  val fnref: Spans.Directive =
    Spans.create("fnref") {
      import Spans.dsl._

      (attribute(0).as[String]).map { (key) => Text(s"Footnote $key") }
    }

  val script: Blocks.Directive =
    Blocks.create("script") {
      import Blocks.dsl._

      (attribute(0).as[String]).map { (js) =>
        RawContent(NonEmptySet.one("html"), s"<script>$js</script>")
      }
    }

  val solution: Blocks.Directive =
    Blocks.create("solution") {
      import Blocks.dsl._

      parsedBody.map { body =>
        Section(Header(5, Text("Solution")), body)
      }
    }

  // Insert a reference to a table
  //
  // Parameters:
  // key: String. The name of the figure being referred to.
  val tref: Spans.Directive =
    Spans.create("tref") {
      import Spans.dsl._

      (attribute(0).as[String]).map { (key) => Text(s"Table $key") }
    }

  val spanDirectives = Seq(fref, fnref, tref)
  val blockDirectives =
    Seq(divWithId, doodle, figure, footnote, script, solution)
  val templateDirectives = Seq()
  val linkDirectives = Seq()
}
