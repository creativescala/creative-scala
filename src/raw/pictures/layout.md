## Layout

We can seen how to create primitive images. We can combine together images using layouts methods to create more complex images. Try the following code---you should see a circle and a rectangle
displayed beside one another:

~~~ scala
(circle(10) beside rectangle(10, 20)).draw
~~~

![A circle beside a rectangle](src/pages/expressions/circle-beside-rectangle.png)

Doodle contains several layout methods for combining images.
Try them out now to see what they do:

----------------------------------------------------------------------------------------
Operator              Type    Description                Example
--------------------- ------- -------------------------- -------------------------------
`Image beside Image`  `Image` Places images horizontally `circle(10) beside circle(20)`
                              next to one another.

`Image above Image`   `Image` Places images vertically   `circle(10) above circle(20)`
                              next to one another.

`Image below Image`   `Image` Places images vertically   `circle(10) below circle(20)`
                              next to one another.

`Image on Image`      `Image` Places images centered     `circle(10) on circle(20)`
                              on top of one another.

`Image under Image`   `Image` Places images centered     `circle(10) under circle(20)`
                              on top of one another.
----------------------------------------------------------------------------------------

## Exercise

#### Compilation Target

Create a line drawing of an archery target with three concentric scoring bands:

![Simple archery target](src/pages/expressions/target1.png)

For bonus credit add a stand so we can place the target on a range:

![Archery target with a stand](src/pages/expressions/target2.png)

<div class="solution">
The simplest solution is to create three concentric circles using the `on` operator:

~~~ scala
(circle(10) on circle(20) on circle(30)).draw
~~~

For the extra credit we can create a stand using two rectangles:

~~~ scala
(
  circle(10) on
  circle(20) on
  circle(30) above
  rectangle(6, 20) above
  rectangle(20, 6)
).draw
~~~
</div>
