package creativescala
package pictures

import cats.implicits._
import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._

object CreatingColor {
  import doodle.core.Color

  (0.until(360, 5))
    .flatMap{ hue =>
      (0.until(100, 10))
        .map{ lightness =>
          val h = hue.degrees
          val l = (lightness.toDouble) / 100.0

          val c = Color.hsl(h, 1.0, l)

          Image.circle(10).noStroke.fillColor(c).at(5.0 * (lightness / 10.0) + 5, h)
        }
    }
    .toList
    .allOn
    .save("pictures/color-wheel")

  val box = Image.square(20).noStroke
  val color = Color.royalBlue

  box.fillColor(color.saturation(0.0.normalized))
    .beside(box.fillColor(color.saturation(0.1.normalized)))
    .beside(box.fillColor(color.saturation(0.2.normalized)))
    .beside(box.fillColor(color.saturation(0.3.normalized)))
    .beside(box.fillColor(color.saturation(0.4.normalized)))
    .beside(box.fillColor(color.saturation(0.5.normalized)))
    .beside(box.fillColor(color.saturation(0.6.normalized)))
    .beside(box.fillColor(color.saturation(0.7.normalized)))
    .beside(box.fillColor(color.saturation(0.8.normalized)))
    .beside(box.fillColor(color.saturation(0.9.normalized)))
    .beside(box.fillColor(color.saturation(1.0.normalized)))
    .save("pictures/saturation")

  Image.triangle(60, 60)
    .fillColor(Color.hsl(0.degrees, 0.8, 0.6))
    .save("pictures/triangle-pastel-red")

  Image.circle(100)
    .fillColor(Color.red)
    .beside(Image.circle(100)
              .fillColor(Color.red.spin(15.degrees)))
    .beside(Image.circle(100)
              .fillColor(Color.red.spin(30.degrees)))
    .strokeWidth(5.0)
    .save("pictures/three-circles-spin")

  Image.circle(40)
     .fillColor(Color.red.darken(0.2.normalized))
     .beside(Image.circle(40).fillColor(Color.red))
     .beside(Image.circle(40).fillColor((Color.red.lighten(0.2.normalized))))
     .above(Image.rectangle(40, 40).fillColor(Color.red.desaturate(0.6.normalized))
                 .beside(Image.rectangle(40,40).fillColor(Color.red.desaturate(0.3.normalized)))
                 .beside(Image.rectangle(40,40).fillColor(Color.red)))
    .save("pictures/saturate-and-lighten")

  Image.circle(40)
    .fillColor(Color.red.alpha(0.5.normalized))
    .beside(Image.circle(40).fillColor(Color.blue.alpha(0.5.normalized)))
    .on(Image.circle(40).fillColor(Color.green.alpha(0.5.normalized)))
    .save("pictures/rgb-alpha")

  Image.triangle(40, 40)
    .strokeWidth(6.0)
    .strokeColor(Color.darkSlateBlue)
    .fillColor(Color.darkSlateBlue
                 .lighten(0.3.normalized)
                 .saturate(0.2.normalized)
                 .spin(10.degrees))
    .above(Image.triangle(40, 40)
             .strokeWidth(6.0)
             .strokeColor(Color.darkSlateBlue.spin(-30.degrees))
             .fillColor(Color.darkSlateBlue
                          .lighten(0.3.normalized)
                          .saturate(0.2.normalized)
                          .spin(-20.degrees))
             .beside(Image.triangle(40, 40)
                       .strokeWidth(6.0)
                       .strokeColor(Color.darkSlateBlue.spin(30.degrees))
                       .fillColor(Color.darkSlateBlue
                                    .lighten(0.3.normalized)
                                    .saturate(0.2.normalized)
                                    .spin(40.degrees))))
    .save("pictures/analogous-triangles")
}
