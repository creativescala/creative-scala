package object creativescala {
  import cats.effect.unsafe.implicits.global
  import java.io.File
  import doodle.java2d.{Drawing, Frame}
  import doodle.algebra.Picture
  import doodle.image.Image

  sealed trait PdfSvg

  implicit class CreativeScalaSaveSyntax(image: Image) {
    def save(filename: String): Unit = {
      val pdfFile = new File(s"book/src/pages/${filename}.pdf")
      val pdfPicture =
        image.compile[doodle.java2d.Algebra]
      val pdf = doodle.java2d.effect.Java2dPdfWriter.write(pdfFile, pdfPicture)

      val svgFile = new File(s"book/src/pages/${filename}.svg")
      val svgPicture = image.compile[doodle.svg.Algebra]
      val svg = doodle.svg.effect.SvgWriter.write(svgFile, svgPicture)

      (pdf.flatMap(_ => svg)).unsafeRunSync()
      ()
    }
  }
}
