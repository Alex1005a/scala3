package dotty.tools
package backend
package jvm

import java.io.PrintWriter

object Primitives {
  /** This class represents a primitive operation. */
  class Primitive {
  }

  /** This class represents a test operation. */
  sealed abstract class TestOp {

    /** Returns the negation of this operation. */
    def negate(): TestOp

    /** Returns a string representation of this operation. */
    override def toString(): String

    /** used only from GenASM */
    def opcodeIF(): Int

    /** used only from GenASM */
    def opcodeIFICMP(): Int

  }

  /** An equality test */
  case object EQ extends TestOp {
    def negate() = NE
    override def toString() = "EQ"
    override def opcodeIF()     = scala.tools.asm.Opcodes.IFEQ
    override def opcodeIFICMP() = scala.tools.asm.Opcodes.IF_ICMPEQ
  }

  /** A non-equality test */
  case object NE extends TestOp {
    def negate() = EQ
    override def toString() = "NE"
    override def opcodeIF()     = scala.tools.asm.Opcodes.IFNE
    override def opcodeIFICMP() = scala.tools.asm.Opcodes.IF_ICMPNE
  }

  /** A less-than test */
  case object LT extends TestOp {
    def negate() = GE
    override def toString() = "LT"
    override def opcodeIF()     = scala.tools.asm.Opcodes.IFLT
    override def opcodeIFICMP() = scala.tools.asm.Opcodes.IF_ICMPLT
  }

  /** A greater-than-or-equal test */
  case object GE extends TestOp {
    def negate() = LT
    override def toString() = "GE"
    override def opcodeIF()     = scala.tools.asm.Opcodes.IFGE
    override def opcodeIFICMP() = scala.tools.asm.Opcodes.IF_ICMPGE
  }

  /** A less-than-or-equal test */
  case object LE extends TestOp {
    def negate() = GT
    override def toString() = "LE"
    override def opcodeIF()     = scala.tools.asm.Opcodes.IFLE
    override def opcodeIFICMP() = scala.tools.asm.Opcodes.IF_ICMPLE
  }

  /** A greater-than test */
  case object GT extends TestOp {
    def negate() = LE
    override def toString() = "GT"
    override def opcodeIF()     = scala.tools.asm.Opcodes.IFGT
    override def opcodeIFICMP() = scala.tools.asm.Opcodes.IF_ICMPGT
  }

  /** This class represents an arithmetic operation. */
  class ArithmeticOp {

    /** Returns a string representation of this operation. */
    override def toString(): String = this match {
      case ADD => "ADD"
      case SUB => "SUB"
      case MUL => "MUL"
      case DIV => "DIV"
      case REM => "REM"
      case NOT => "NOT"
      case _   => throw new RuntimeException("ArithmeticOp unknown case")
    }
  }

  /** An arithmetic addition operation */
  case object ADD extends ArithmeticOp

  /** An arithmetic subtraction operation */
  case object SUB extends ArithmeticOp

  /** An arithmetic multiplication operation */
  case object MUL extends ArithmeticOp

  /** An arithmetic division operation */
  case object DIV extends ArithmeticOp

  /** An arithmetic remainder operation */
  case object REM extends ArithmeticOp

  /** Bitwise negation. */
  case object NOT extends ArithmeticOp

  /** This class represents a shift operation. */
  class ShiftOp {

    /** Returns a string representation of this operation. */
    override def toString(): String = this match {
      case LSL =>  "LSL"
      case ASR =>  "ASR"
      case LSR =>  "LSR"
      case _  => throw new RuntimeException("ShitOp unknown case")
    }
  }

  /** A logical shift to the left */
  case object LSL extends ShiftOp

  /** An arithmetic shift to the right */
  case object ASR extends ShiftOp

  /** A logical shift to the right */
  case object LSR extends ShiftOp

  /** This class represents a logical operation. */
  class LogicalOp {

    /** Returns a string representation of this operation. */
    override def toString(): String = this match {
      case AND => "AND"
      case OR  => "OR"
      case XOR => "XOR"
      case _  => throw new RuntimeException("LogicalOp unknown case")
    }
  }

  /** A bitwise AND operation */
  case object AND extends LogicalOp

  /** A bitwise OR operation */
  case object OR extends LogicalOp

  /** A bitwise XOR operation */
  case object XOR extends LogicalOp

  /** Signals the beginning of a series of concatenations.
   *  On the JVM platform, it should create a new StringBuilder.
   */
  case object StartConcat extends Primitive

  /** type: (buf) => STR
   *  jvm : It should turn the StringBuilder into a String.
   */
  case object EndConcat extends Primitive

  /** Pretty printer for primitives */
  class PrimitivePrinter(out: PrintWriter) {
    def print(s: String): PrimitivePrinter = {
      out.print(s)
      this
    }
  }

  /** This class represents a comparison operation. */
  class ComparisonOp {

    /** Returns a string representation of this operation. */
    override def toString(): String = this match {
      case CMPL => "CMPL"
      case CMP  => "CMP"
      case CMPG => "CMPG"
      case _ => throw new RuntimeException("ComparisonOp unknown case")
    }
  }

  /** A comparison operation with -1 default for NaNs */
  case object CMPL extends ComparisonOp

  /** A comparison operation with no default for NaNs */
  case object CMP extends ComparisonOp

  /** A comparison operation with +1 default for NaNs */
  case object CMPG extends ComparisonOp
}
