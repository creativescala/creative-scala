package creativescala
package programs

import cats.implicits.*
import doodle.core.*
import doodle.syntax.all.*
import doodle.image.*
import doodle.image.syntax.all.*
import doodle.java2d.*

object Street {
  val roof = Image.triangle(50, 30).fillColor(Color.brown)

  val frontDoor =
    Image
      .rectangle(50, 15)
      .fillColor(Color.red)
      .above(
        Image
          .rectangle(10, 25)
          .fillColor(Color.black)
          .on(Image.rectangle(50, 25).fillColor(Color.red))
      )

  val house = roof.above(frontDoor)

  val tree =
    Image
      .circle(25)
      .fillColor(Color.green)
      .above(Image.rectangle(10, 20).fillColor(Color.brown))

  val streetSegment =
    Image
      .rectangle(30, 3)
      .fillColor(Color.yellow)
      .beside(Image.rectangle(15, 3).fillColor(Color.black))
      .above(Image.rectangle(45, 7).fillColor(Color.black))

  val street = streetSegment.beside(streetSegment).beside(streetSegment)

  val houseAndGarden =
    house.beside(tree).above(street)

  val image =
    houseAndGarden.beside(houseAndGarden).beside(houseAndGarden).noStroke

  image.save("programs/street")
}
