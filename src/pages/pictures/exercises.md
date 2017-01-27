## Exercises


**Exercise: Compilation Target**

Create a line drawing of an archery target with three concentric scoring bands:

![Simple archery target](src/pages/expressions/target1.png)

For bonus credit add a stand so we can place the target on a range:

![Archery target with a stand](src/pages/expressions/target2.png)

<div class="solution">
The simplest solution is to create three concentric circles using the `on` operator:

~~~ scala
(Circle(10) on Circle(20) on Circle(30)).draw
~~~

For the extra credit we can create a stand using two rectangles:

~~~ scala
(
  Circle(10) on
  Circle(20) on
  Circle(30) above
  Rectangle(6, 20) above
  Rectangle(20, 6)
).draw
~~~
</div>

**Exercise: Stay on Target**

Colour your target red and white, the stand in brown (if applicable),
and some ground in green:

![Colour archery target](src/pages/expressions/target3.png)

<div class="solution">
The trick here is using parentheses to control the order of composition.
The `fillColor()`, `lineColor()`, and `lineWidth()` methods
apply to a single image---we need to make sure that image
comprises the correct set of shapes:

~~~ scala
(
  ( Circle(10) fillColor Color.red ) on
  ( Circle(20) fillColor Color.white ) on
  ( Circle(30) fillColor Color.red lineWidth 2 ) above
  ( Rectangle(6, 20) above Rectangle(20, 6) fillColor Color.brown ) above
  ( Rectangle(80, 25) lineWidth 0 fillColor Color.green )
).draw
~~~
</div>
