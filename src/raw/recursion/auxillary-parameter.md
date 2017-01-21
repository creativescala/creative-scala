## Auxillary Parameters

#### Gradient Boxes {-}

In this exercise we're going to draw a picture like that in [@fig:recursion:gradient-boxes].
We already know how to draw a line of boxes.
The challenge in this exercise is to make the color change at each step.

Hint: make the color a function of `n`, such as `Color.royalBlue.spin((15 * n).degrees)`

![Five boxes filled with changing colors starting from Royal Blue](./src/pages/recursion/gradient-boxes.pdf+svg){#fig:recursion:gradient-boxes}

<div class="solution">

There are two ways to implement a solution. 
The first method, which we are hinting at, is shown below.

```tut:book
def gradientBoxes(n: Int): Image =
  n match {
    case 0 => Image.empty
    case n => aBox.fillColor(Color.royalBlue.spin(15.degrees)) beside gradientBoxes(n-1)
  }
```

An alternative is to add an extra parameter to `gradientBoxes` and pass the `Color` through the structural recursion.

```tut:book
def gradientBoxes(n: Int, color: Color): Image =
  n match {
    case 0 => Image.empty
    case n => aBox.fillColor(color) beside gradientBoxes(n-1, color.spin(15.degrees))
  }
```
</div>

#### Concentric Circles {-}

Now let's try a variation on the theme, drawing concentric circles as shown in [@fig:recursion:concentric-circles]. Here we are changing the size rather than the color of the image at each step. Otherwise the pattern stays the same. Have a go at implementing it.

![Concentric circles, colored Royal Blue](./src/pages/recursion/concentric-circles.pdf+svg){#fig:recursion:concentric-circles}

<div class="solution">
The trick here is that the size of the circle is a function of `n`. There are many possible choices here. We've used the simple choice of `50 + (5 * n)`.

```tut:book
def concentricCircles(count: Int): Image =
  count match {
    case 0 => Image.empty
    case n => Image.circle(50 + (5 * n)) on concentricCircles(n-1)
  }
```
</div>

#### Once More, With Feeling {-}

Now let's combine both techniques to change size and color on each step, giving results like those shown in [@fig:recursion:colorful-circles]. Experiment until you find something you like.

![Concentric circles with interesting color variations](./src/pages/recursion/colorful-circles.pdf+svg){#fig:recursion:colorful-circles}

<div class="solution">
Here's our solution, where we've tried to break the problem into reusable parts and reduce the amount of repeated code. We still have a lot of repitition as we don't yet have the tools to get rid of more. We'll come to that soon.

```tut:book
def singleCircle(n: Int): Image =
  Image.circle(50 + 7 * n).lineWidth(3.0)

def fade(n: Int): Image =
  singleCircle(n).lineColor(Color.red fadeOut (n / 20.0).normalized)

def gradient(n: Int): Image =
  singleCircle(n).lineColor(Color.royalBlue.spin((n * 15).degrees))

def concentricCircles(n: Int): Image =
  n match {
    case 0 => singleCircle(n)
    case n => singleCircle(n) on concentricCircles(n - 1)
  }

def fadeCircles(n: Int): Image =
  n match {
    case 0 => fade(n)
    case n => fade(n) on fadeCircles(n - 1)
  }

def gradientCircles(n: Int): Image =
  n match {
    case 0 => gradient(n)
    case n => gradient(n) on gradientCircles(n - 1)
  }

def image: Image =
  concentricCircles(20).lineColor(Color.royalBlue)

def fade: Image =
  fadeCircles(20)

def gradient: Image =
  gradientCircles(20)
```
</div>

