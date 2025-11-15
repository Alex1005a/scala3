def passNotSubtypeExist(b: Option[H] forSome { type H <: CharSequence }) = {
  def go(bb: Option[BH] forSome { type BH <: String }) = ???
  go(b)
}