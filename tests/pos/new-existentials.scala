class Box[T] {
  def push(x: T): Unit = ???
  def pop(): T = ???
}

def boxId[A](x: Box[A]): Box[A] = x

def boxExistId(x: Box[E] forSome { type E }): Box[x.E] = boxId(x)

object BoxTest {
  def eq[T](box1: Box[T], box2: Box[T]): Boolean = ???
  def pushPop(x: Box[G] forSome { type G }): Unit = {
    val iseq = eq(x, identity(x))
    x.push(x.pop())
  }
}

def escape = {
  val b: Box[B] forSome { type B } = ???
  boxId(b)
}

def noPath(xs: List[Box[T] forSome { type T }]) = {
  xs.map(_.pop())
}

def boxExistIdPassIntBox(b: Box[Int]) = {
  boxExistId(b)
}

def boxExistIdPassExistBox(b: Box[H] forSome { type H }) = {
  boxExistId(b)
}

def passExistToIdentity(b: H forSome { type H }) = {
  identity(b)
}

type Tuple2KV[K[_], V[_]] = Tuple2[K[A], V[A]] forSome { type A }
def existAlias[K[_], V[_]](t: Tuple2[K[Int], V[Int]]) = {
  def go(g: Tuple2KV[K, V]) = g._1
  go(t)
}

def existInHK[K[_], V[_]](t: Tuple2[K[Int], V[Int]]) = {
  def go(g: Tuple2[K[A], V[A]] forSome { type A }) = g._1
  go(t)
}

def fieldExists(e: Tuple2[Unit, A] forSome {type A}) = e._2

case class ClassForSome(c: Option[C] forSome {type C})

case class FunForSome(c: () => Option[C] forSome {type C})
def callMethodWithExist(e: FunForSome) = e.c()

def patternMatchExist(x: ((AB, AB => FF) forSome {type AB; type FF})) = {
  x match {
    case (ab, fab) => fab(ab)
  }

  val y: (x.AB, x.AB => x.FF) = x
  y match {
    case (ab, fab) => fab(ab)
  }
}

case class FuncForSome(c: Unit => (Option[C] forSome {type C}))
def passExistsFunc(g: Unit => (Option[HH] forSome {type HH})) = {
  FuncForSome(g)
}

def passUnitFunc(g: Unit => Option[Unit]) = {
  FuncForSome(g)
}

def passSubtypeExist(b: Box[H] forSome { type H <: String }) = {
  def go(bb: Box[BH] forSome { type BH <: CharSequence }) = ???
  go(b)
}