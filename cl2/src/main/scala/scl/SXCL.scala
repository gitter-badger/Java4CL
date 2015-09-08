package scl

import com.typesafe.scalalogging._
import org.apache.commons.codec.binary.Base64
import org.apache.commons.lang3.{ SerializationUtils, StringEscapeUtils }
import scala.language.{ postfixOps, existentials }
import scala.util.{ Try, Success, Failure }
import scala.xml.{ Elem, Node, NodeSeq }
import SCL._

/**
 * @author taraathan
 */
object XMLHelper {

  implicit class StringCommentXMLHelper(x: StringComment) {
    def toXML: Elem =
      <cl:Comment xmlns:cl={ SCL.URI_XCL2 }>{ StringEscapeUtils escapeXml11((x data) toString) }</cl:Comment>
  }

  implicit class CommentXMLHelper(x: Comment) {
    def label: String = "Comment"
    def toXML: Elem = x match {
      case a: StringComment =>
        (a toXML)
      case _ =>
        <cl:Comment xmlns:cl={ SCL.URI_XCL2 }>{ Base64.encodeBase64String(SerializationUtils.serialize(x data)) }</cl:Comment>
    }
  }

  implicit class StringInterpretableNameXMLHelper(x: StringInterpretableName) {
    def toXML: Elem =
      <cl:Name xmlns:cl={ SCL.URI_XCL2 }>{ StringEscapeUtils.escapeXml11((x symbol) toString) }</cl:Name>
  }

  implicit class InterpretableNameXMLHelper(x: InterpretableName) {
    def toXML: Elem = x match {
      case a: StringInterpretableName =>
        (a toXML)
      case _ =>
        <cl:Name xmlns:cl={ SCL.URI_XCL2 }>{ Base64.encodeBase64String(SerializationUtils.serialize(x symbol)) }</cl:Name>
    }
  }

  implicit class StringIriInterpretedNameXMLHelper(x: StringIriInterpretedName) {
    def toXML: Elem =
      <cl:Data xmlns:cl={ SCL.URI_XCL2 } datatype={ StringEscapeUtils escapeXml11((x datatype) toString) }>{ StringEscapeUtils escapeXml11((x symbol) toString) }</cl:Data>
  }

  // TODO add datatype
  implicit class InterpretedNameXMLHelper(x: InterpretedName) {
    def toXML: Elem = x match {
      case a: StringIriInterpretedName => (a toXML)
      case _ =>
        <cl:Data xmlns:cl={ SCL.URI_XCL2 }>{ Base64.encodeBase64String(SerializationUtils.serialize(x symbol)) }</cl:Data>
    }
  }

  implicit class FunctionalTermXMLHelper(x: FunctionalTerm) {
    def toXML: Elem =
      <cl:Apply xmlns:cl={ SCL.URI_XCL2 }>
        { ((x comments) toXML) }
        { ((x operator) toXML) }
        { ((x args) toXML) }
      </cl:Apply>
  }

  implicit class TermXMLHelper(x: Term) {
    def toXML: Elem = x match {
      case a: InterpretableName => (a toXML)
      case a: InterpretedName   => (a toXML)
      case a: FunctionalTerm    => (a toXML)
    }
  }

  implicit class StringSequenceMarkerXMLHelper(x: StringSequenceMarker) {
    def toXML: Elem =
      <cl:Marker xmlns:cl={ SCL.URI_XCL2 }>{ StringEscapeUtils.escapeXml11((x symbol) toString) }</cl:Marker>
  }

  implicit class SequenceMarkerXMLHelper(x: SequenceMarker) {
    def toXML: Elem = x match {
      case a: StringSequenceMarker =>
        (a toXML)
      case _ =>
        <cl:Marker xmlns:cl={ SCL.URI_XCL2 }>
        { Base64.encodeBase64String(SerializationUtils.serialize(x symbol)) }
        </cl:Marker>
    }
  }

  implicit class TermOrSequenceMarkerXMLHelper(x: TermOrSequenceMarker) {
    def toXML: Elem = x match {
      case a: Term           => (a toXML)
      case a: SequenceMarker => (a toXML)
    }
  }

  implicit class CommentSetXMLHelper(x: CommentSet) {
    def toXML: NodeSeq = {
      val y = (x.toSeq)
      for (comment <- y) yield (comment toXML)
    }
  }

  implicit class TermSetXMLHelper(x: TermSet) {
    def toXML: NodeSeq = {
      val y = (x.toSeq)
      for (term <- y) yield (term toXML)
    }
  }

  implicit class BindingSetXMLHelper(x: BindingSet) {
    def toXML: NodeSeq = {
      val y = (x.toSeq)
      for (name <- y) yield (name toXML)
    }
  }

  implicit class SeqBindingSetXMLHelper(x: SeqBindingSet) {
    def toXML: NodeSeq = {
      val y = (x.toSeq)
      for (seq <- y) yield (seq toXML)
    }
  }

  implicit class TermSequenceXMLHelper(x: TermSequence) {
    def toXML: NodeSeq = {
      for (tosm <- x) yield (tosm toXML)
    }
  }

  implicit class TermOrSequenceMarkerSetXMLHelper(x: TOSMSet) {
    def toXML: NodeSeq = {
      val y = (x.toSeq)
      for (tosm <- y) yield (tosm toXML)
    }
  }

  implicit class AtomicSentenceXMLHelper(x: AtomicSentence) {
    def toXML: Elem =
      <cl:Atom xmlns:cl={ SCL.URI_XCL2 }>
        { ((x comments) toXML) }
        { ((x operator) toXML) }
        { ((x args) toXML) }
      </cl:Atom>
  }

  implicit class EquationXMLHelper(x: Equation) {
    def toXML: Elem = {
      <cl:Equal xmlns:cl={ SCL.URI_XCL2 }>
        { ((x comments) toXML) }
        { ((x terms) toXML) }
        { if (((x terms) size) == 1)((x terms) toXML) }
      </cl:Equal>
    }
  }

  implicit class BiconditionalXMLHelper(x: Biconditional) {
    def toXML: Elem = {
      <cl:Biconditional xmlns:cl={ SCL.URI_XCL2 }>
        { ((x comments) toXML) }
        { ((x sentences) toXML) }
        { if (((x sentences) size) == 1)((x sentences) toXML) }
      </cl:Biconditional>
    }
  }

  implicit class ConjunctionXMLHelper(x: Conjunction) {
    def toXML: Elem = {
      <cl:And xmlns:cl={ SCL.URI_XCL2 }>
        { ((x comments) toXML) }
        { ((x conjuncts) toXML) }
      </cl:And>
    }
  }

  implicit class DisjunctionXMLHelper(x: Disjunction) {
    def toXML: Elem = {
      <cl:Or xmlns:cl={ SCL.URI_XCL2 }>
        { ((x comments) toXML) }
        { ((x disjuncts) toXML) }
      </cl:Or>
    }
  }

  implicit class NegationXMLHelper(x: Negation) {
    def toXML: Elem = {
      <cl:Not xmlns:cl={ SCL.URI_XCL2 }>
        { ((x comments) toXML) }
        { ((x body) toXML) }
      </cl:Not>
    }
  }

  implicit class ImplicationXMLHelper(x: Implication) {
    def toXML: Elem = {
      <cl:Implies xmlns:cl={ SCL.URI_XCL2 }>
        { ((x comments) toXML) }
        { ((x antecedent) toXML) }
        { ((x consequent) toXML) }
      </cl:Implies>
    }
  }

  implicit class ExistentialXMLHelper(x: Existential) {
    def toXML: Elem = {
      <cl:Exists xmlns:cl={ SCL.URI_XCL2 }>
        { ((x comments) toXML) }
        { ((x bindings) toXML) }
        { ((x body) toXML) }
      </cl:Exists>
    }
  }

  implicit class UniversalXMLHelper(x: Universal) {
    def toXML: Elem = {
      <cl:Forall xmlns:cl={ SCL.URI_XCL2 }>
        { ((x comments) toXML) }
        { ((x bindings) toXML) }
        { ((x body) toXML) }
      </cl:Forall>
    }
  }

  implicit class SimpleSentenceXMLHelper(x: SimpleSentence) {
    def toXML: Elem = x match {
      case a: AtomicSentence => (a toXML)
      case a: Equation       => (a toXML)
    }
  }

  implicit class BooleanSentenceXMLHelper(x: BooleanSentence) {
    def toXML: Elem = x match {
      case a: Negation      => (a toXML)
      case a: Conjunction   => (a toXML)
      case a: Disjunction   => (a toXML)
      case a: Biconditional => (a toXML)
      case a: Implication   => (a toXML)
    }
  }

  implicit class QuantifiedSentenceXMLHelper(x: QuantifiedSentence) {
    def toXML: Elem = x match {
      case a: Existential => (a toXML)
      case a: Universal   => (a toXML)
    }
  }

  implicit class SentenceXMLHelper(x: Sentence) {
    def toXML: Elem = x match {
      case a: SimpleSentence     => (a toXML)
      case a: BooleanSentence    => (a toXML)
      case a: QuantifiedSentence => (a toXML)
    }
  }

  implicit class SentenceSetXMLHelper(x: SentenceSet) {
    def toXML: NodeSeq = {
      val y = (x.toSeq)
      for (sentence <- y) yield (sentence toXML)
    }
  }

  implicit class TitlingXMLHelper(x: Titling) {
    def toXML: Elem = {
      <cl:Titling xmlns:cl={ SCL.URI_XCL2 }>
        { ((x comments) toXML) }
        { ((x title) toXML) }
        { ((x body) toXML) }
      </cl:Titling>
    }
  }

  implicit class SchemaXMLHelper(x: Schema) {
    def toXML: Elem = {
      <cl:Schema xmlns:cl={ SCL.URI_XCL2 }>
        { ((x comments) toXML) }
        { ((x seqbindings) toXML) }
        { ((x body) toXML) }
      </cl:Schema>
    }
  }

  implicit class InDiscourseStatementXMLHelper(x: InDiscourseStatement) {
    def toXML: Elem = {
      <cl:In xmlns:cl={ SCL.URI_XCL2 }>
        { ((x comments) toXML) }
        { ((x terms) toXML) }
      </cl:In>
    }
  }

  implicit class OutDiscourseStatementXMLHelper(x: OutDiscourseStatement) {
    def toXML: Elem = {
      <cl:Out xmlns:cl={ SCL.URI_XCL2 }>
        { ((x comments) toXML) }
        { ((x terms) toXML) }
      </cl:Out>
    }
  }

  implicit class DiscourseStatementXMLHelper(x: DiscourseStatement) {
    def toXML: Elem = x match {
      case a: InDiscourseStatement  => (a toXML)
      case a: OutDiscourseStatement => (a toXML)
    }
  }

  implicit class StatementXMLHelper(x: Statement) {
    def toXML: Elem = x match {
      case a: Titling            => (a toXML)
      case a: Schema             => (a toXML)
      case a: DiscourseStatement => (a toXML)
    }
  }

  implicit class TextConstructionXMLHelper(x: TextConstruction) {
    def toXML: Elem = {
      <cl:Construct xmlns:cl={ SCL.URI_XCL2 }>
        { ((x comments) toXML) }
        { ((x expressions) toXML) }
      </cl:Construct>
    }
  }

  implicit class DomainRestrictionXMLHelper(x: DomainRestriction) {
    def toXML: Elem = {
      <cl:Restrict xmlns:cl={ SCL.URI_XCL2 }>
        { ((x comments) toXML) }
        { ((x domain) toXML) }
        { ((x body) toXML) }
      </cl:Restrict>
    }
  }

  implicit class ImportationXMLHelper(x: Importation) {
    def toXML: Elem = {
      <cl:Import xmlns:cl={ SCL.URI_XCL2 }>
        { ((x comments) toXML) }
        { ((x title) toXML) }
      </cl:Import>
    }
  }

  implicit class TextXMLHelper(x: Text) {
    def toXML: Elem = x match {
      case a: TextConstruction  => (a toXML)
      case a: DomainRestriction => (a toXML)
      case a: Importation       => (a toXML)
    }
  }

  implicit class BasicExpressionXMLHelper(x: BasicExpression) {
    def toXML: Elem = x match {
      case a: Text      => (a toXML)
      case a: Statement => (a toXML)
      case a: Sentence  => (a toXML)
    }
  }

  implicit class BasicExpressionSetXMLHelper(x: BasicExpressionSet) {
    def toXML: NodeSeq = {
      val y = (x.toSeq)
      for (basic <- y) yield (basic toXML)
    }
  }
}

object Parser {
  trait XCLParsable[T] {
    def fromXML(x: Elem): Try[T]
  }

  object XCLParsable {
    implicit object XCLParsableComment
        extends XCLParsable[Comment] {
      // TODO parse data datatype
      def fromXML(x: Elem): Try[Comment] =
        Try(
          x match {
            case e: Elem if (isXCLComment(e)) =>
              new StringComment(StringEscapeUtils unescapeXml (x text))
          })
    }

    implicit object XCLParsableInterpretableName
        extends XCLParsable[InterpretableName] {
      // TODO parse symbol datatype
      def fromXML(x: Elem): Try[InterpretableName] =
        Try(
          x match {
            case e: Elem if (isXCLInterpretableName(e)) =>
              new StringInterpretableName(StringEscapeUtils unescapeXml (x text))
          })
    }

    implicit object XCLParsableInterpretedName
        extends XCLParsable[InterpretedName] {
      // TODO parse datatype
      def fromXML(x: Elem): Try[InterpretedName] =
        Try(
          x match {
            case e: Elem if (isXCLInterpretedName(e)) =>
              new StringIriInterpretedName(StringEscapeUtils unescapeXml (x text))
          })
    }

    implicit object XCLParsableSequenceMarker
        extends XCLParsable[SequenceMarker] {
      // TODO parse symbol datatype
      def fromXML(x: Elem): Try[SequenceMarker] =
        Try(
          x match {
            case e: Elem if (isXCLSequenceMarker(e)) =>
              new StringSequenceMarker(StringEscapeUtils unescapeXml (x text))
          })
    }

    implicit object XCLParsableFunctionalTerm
        extends XCLParsable[FunctionalTerm] {
      def fromXML(x: Elem): Try[FunctionalTerm] = Try({
        val comments: CommentSet = commentSetParse(x)
        val argsAll: TermSequence = tosmSeqParse(x)
        val operator: Term = (argsAll head) match {
          case t: Term => t
        }
        val args: TermSequence = (argsAll tail)
        FunctionalTerm(comments, operator, args)
      })
    }

    def isInXCLNamespace(x: Node): Boolean =
      ((x namespace) equals SCL.URI_XCL2)

    def isXCLComment(x: Node): Boolean =
      isInXCLNamespace(x) && ((x label) equals "Comment")

    def isXCLInterpretableName(x: Node): Boolean =
      isInXCLNamespace(x) && ((x label) equals "Name")

    def isXCLInterpretedName(x: Node): Boolean =
      isInXCLNamespace(x) && ((x label) equals "Data")

    def isXCLFunctionalTerm(x: Node): Boolean =
      isInXCLNamespace(x) && ((x label) equals "Apply")

    def isXCLSequenceMarker(x: Node): Boolean =
      isInXCLNamespace(x) && ((x label) equals "Marker")

    def isXCLName(x: Node): Boolean =
      isXCLInterpretableName(x) || isXCLInterpretedName(x)

    def isXCLNOSM(x: Node): Boolean =
      isXCLName(x) || isXCLSequenceMarker(x)

    def isXCLTerm(x: Node): Boolean =
      isXCLName(x) || isXCLFunctionalTerm(x)

    def isXCLTOSM(x: Node): Boolean =
      isXCLTerm(x) || isXCLSequenceMarker(x)

    implicit object XCLParsableName
        extends XCLParsable[Name] {
      def fromXML(x: Elem): Try[Name] =
        Try(
          x match {
            case e: Elem if (isXCLInterpretableName(e)) =>
              XCLParsableInterpretableName.fromXML(e) get
            case e: Elem if (isXCLInterpretedName(e)) =>
              XCLParsableInterpretedName.fromXML(e) get
          })
    }

    implicit object XCLParsableTerm
        extends XCLParsable[Term] {
      def fromXML(x: Elem): Try[Term] =
        Try(
          x match {
            case e: Elem if (isXCLName(e)) =>
              XCLParsableName.fromXML(e) get
            case e: Elem if (isXCLFunctionalTerm(e)) =>
              XCLParsableFunctionalTerm.fromXML(e) get
          })
    }

    implicit object XCLParsableTOSM
        extends XCLParsable[TermOrSequenceMarker] {
      def fromXML(x: Elem): Try[TermOrSequenceMarker] = Try(x match {
        case e: Elem if (isXCLTerm(e)) =>
          XCLParsableTerm.fromXML(e) get
        case e: Elem if (isXCLSequenceMarker(e)) =>
          XCLParsableSequenceMarker.fromXML(e) get
      })
    }

    implicit object XCLParsableNOSM
        extends XCLParsable[NameOrSequenceMarker] {
      def fromXML(x: Elem): Try[NameOrSequenceMarker] = Try(x match {
        case e: Elem if (isXCLName(e)) =>
          XCLParsableName.fromXML(e) get
        case e: Elem if (isXCLSequenceMarker(e)) =>
          XCLParsableSequenceMarker.fromXML(e) get
      })
    }

    implicit object XCLParsableAtomicSentence
        extends XCLParsable[AtomicSentence] {
      def fromXML(x: Elem): Try[AtomicSentence] = Try({
        val comments: CommentSet = commentSetParse(x)
        val argsAll: TermSequence = tosmSeqParse(x)
        val operator: Term = (argsAll head) match {
          case t: Term => t
        }
        val args: TermSequence = argsAll.tail
        AtomicSentence(comments, operator, args)
      })
    }

    implicit object XCLParsableEquation
        extends XCLParsable[Equation] {
      def fromXML(x: Elem): Try[Equation] = Try({
        val comments: CommentSet = commentSetParse(x)
        val args: TermSet = termSetParse(x)
        Equation(comments, args)
      })
    }

    implicit object XCLParsableBiconditional
        extends XCLParsable[Biconditional] {
      def fromXML(x: Elem): Try[Biconditional] = Try({
        val comments: CommentSet = commentSetParse(x)
        val args: SentenceSet = sentenceSetParse(x)
        Biconditional(comments, args)
      })
    }

    implicit object XCLParsableImplication
        extends XCLParsable[Implication] {
      def fromXML(x: Elem): Try[Implication] = Try({
        val comments: CommentSet = commentSetParse(x)
        val args: List[Sentence] = sentenceSeqParse(x)
        val antecedent: Sentence = (args head)
        val consequent: Sentence = ((args tail) head)
        Implication(comments, antecedent, consequent)
      })
    }

    implicit object XCLParsableConjunction
        extends XCLParsable[Conjunction] {
      def fromXML(x: Elem): Try[Conjunction] = Try({
        val comments: CommentSet = commentSetParse(x)
        val args: SentenceSet = sentenceSetParse(x)
        Conjunction(comments, args)
      })
    }

    implicit object XCLParsableDisjunction
        extends XCLParsable[Disjunction] {
      def fromXML(x: Elem): Try[Disjunction] = Try({
        val comments: CommentSet = commentSetParse(x)
        val args: SentenceSet = sentenceSetParse(x)
        Disjunction(comments, args)
      })
    }

    implicit object XCLParsableNegation
        extends XCLParsable[Negation] {
      def fromXML(x: Elem): Try[Negation] = Try({
        val comments: CommentSet = commentSetParse(x)
        val args: List[Sentence] = sentenceSeqParse(x)
        val body: Sentence = (args head)
        Negation(comments, body)
      })
    }

    implicit object XCLParsableExistential
        extends XCLParsable[Existential] {
      def fromXML(x: Elem): Try[Existential] = Try({
        val comments: CommentSet = commentSetParse(x)
        val bindings: BindingSet = bindingSetParse(x)
        val args: List[Sentence] = sentenceSeqParse(x)
        val body: Sentence = (args head)
        Existential(comments, bindings, body)
      })
    }

    implicit object XCLParsableUniversal
        extends XCLParsable[Universal] {
      def fromXML(x: Elem): Try[Universal] = Try({
        val comments: CommentSet = commentSetParse(x)
        val bindings: BindingSet = bindingSetParse(x)
        val args: List[Sentence] = sentenceSeqParse(x)
        val body: Sentence = (args head)
        Universal(comments, bindings, body)
      })
    }

    def isXCLAtomicSentence(x: Node): Boolean =
      isInXCLNamespace(x) && ((x label) equals "Atom")

    def isXCLEquation(x: Node): Boolean =
      isInXCLNamespace(x) && ((x label) equals "Equal")

    def isXCLSimpleSentence(x: Node): Boolean =
      isXCLAtomicSentence(x) ||
        isXCLEquation(x)

    def isXCLBiconditional(x: Node): Boolean =
      isInXCLNamespace(x) && ((x label) equals "Biconditional")

    def isXCLImplication(x: Node): Boolean =
      isInXCLNamespace(x) && ((x label) equals "Implies")

    def isXCLConjunction(x: Node): Boolean =
      isInXCLNamespace(x) && ((x label) equals "And")

    def isXCLDisjunction(x: Node): Boolean =
      isInXCLNamespace(x) && ((x label) equals "Or")

    def isXCLNegation(x: Node): Boolean =
      isInXCLNamespace(x) && ((x label) equals "Not")

    def isXCLBooleanSentence(x: Node): Boolean =
      isXCLBiconditional(x) ||
        isXCLImplication(x) ||
        isXCLConjunction(x) ||
        isXCLDisjunction(x) ||
        isXCLNegation(x)

    def isXCLExistential(x: Node): Boolean =
      isInXCLNamespace(x) && ((x label) equals "Exists")

    def isXCLUniversal(x: Node): Boolean =
      isInXCLNamespace(x) && ((x label) equals "Forall")

    def isXCLQuantifiedSentence(x: Node): Boolean =
      isXCLExistential(x) ||
        isXCLUniversal(x)

    def isXCLSentence(x: Node): Boolean =
      isXCLSimpleSentence(x) ||
        isXCLBooleanSentence(x) ||
        isXCLQuantifiedSentence(x)

    implicit object XCLParsableSimpleSentence
        extends XCLParsable[SimpleSentence] {
      def fromXML(x: Elem): Try[SimpleSentence] =
        Try(
          x match {
            case e: Elem if (isXCLAtomicSentence(e)) =>
              XCLParsableAtomicSentence.fromXML(e) get
            case e: Elem if (isXCLEquation(e)) =>
              XCLParsableEquation.fromXML(e) get
          })
    }

    implicit object XCLParsableBooleanSentence
        extends XCLParsable[BooleanSentence] {
      def fromXML(x: Elem): Try[BooleanSentence] =
        Try(
          x match {
            case e: Elem if (isXCLBiconditional(e)) =>
              XCLParsableBiconditional.fromXML(e) get
            case e: Elem if (isXCLImplication(e)) =>
              XCLParsableImplication.fromXML(e) get
            case e: Elem if (isXCLConjunction(e)) =>
              XCLParsableConjunction.fromXML(e) get
            case e: Elem if (isXCLDisjunction(e)) =>
              XCLParsableDisjunction.fromXML(e) get
            case e: Elem if (isXCLNegation(e)) =>
              XCLParsableNegation.fromXML(e) get
          })
    }

    implicit object XCLParsableQuantifiedSentence
        extends XCLParsable[QuantifiedSentence] {
      def fromXML(x: Elem): Try[QuantifiedSentence] =
        Try(
          x match {
            case e: Elem if (isXCLExistential(e)) =>
              XCLParsableExistential.fromXML(e) get
            case e: Elem if (isXCLUniversal(e)) =>
              XCLParsableUniversal.fromXML(e) get
          })
    }

    // Sentence
    implicit object XCLParsableSentence
        extends XCLParsable[Sentence] {
      def fromXML(x: Elem): Try[Sentence] =
        Try(
          x match {
            case e: Elem if (isXCLSimpleSentence(e)) =>
              XCLParsableSimpleSentence.fromXML(e) get
            case e: Elem if (isXCLBooleanSentence(e)) =>
              XCLParsableBooleanSentence.fromXML(e) get
            case e: Elem if (isXCLQuantifiedSentence(e)) =>
              XCLParsableQuantifiedSentence.fromXML(e) get
          })
    }

    // Titling
    implicit object XCLParsableTitling
        extends XCLParsable[Titling] {
      def fromXML(x: Elem): Try[Titling] = Try({
        val comments: CommentSet = commentSetParse(x)
        val names: List[Name] = nameSeqParse(x)
        val title = names head
        val args: List[Text] = textSeqParse(x)
        val body: Text = (args head)
        Titling(comments, title, body)
      })
    }

    implicit object XCLParsableInDiscourseStatement
        extends XCLParsable[InDiscourseStatement] {
      def fromXML(x: Elem): Try[InDiscourseStatement] = Try({
        val comments: CommentSet = commentSetParse(x)
        val terms: TOSMSet = tosmSetParse(x)
        InDiscourseStatement(comments, terms)
      })
    }

    implicit object XCLParsableOutDiscourseStatement
        extends XCLParsable[OutDiscourseStatement] {
      def fromXML(x: Elem): Try[OutDiscourseStatement] = Try({
        val comments: CommentSet = commentSetParse(x)
        val terms: TOSMSet = tosmSetParse(x)
        OutDiscourseStatement(comments, terms)
      })
    }

    implicit object XCLParsableSchema
        extends XCLParsable[Schema] {
      def fromXML(x: Elem): Try[Schema] = Try({
        val comments: CommentSet = commentSetParse(x)
        val seqbindings: SeqBindingSet = seqBindingSetParse(x)
        val args: List[SentenceOrSchema] = sentenceOrSchemaSeqParse(x)
        val body: SentenceOrSchema = (args head)
        Schema(comments, seqbindings, body)
      })
    }

    def isXCLTitling(x: Node): Boolean =
      isInXCLNamespace(x) && ((x label) equals "Titling")

    def isXCLInDiscourseStatement(x: Node): Boolean =
      isInXCLNamespace(x) && ((x label) equals "In")

    def isXCLOutDiscourseStatement(x: Node): Boolean =
      isInXCLNamespace(x) && ((x label) equals "Out")

    def isXCLSchema(x: Node): Boolean =
      isInXCLNamespace(x) && ((x label) equals "Schema")

    implicit object XCLParsableDiscourseStatement
        extends XCLParsable[DiscourseStatement] {
      def fromXML(x: Elem): Try[DiscourseStatement] =
        Try(
          x match {
            case e: Elem if (isXCLInDiscourseStatement(e)) =>
              XCLParsableInDiscourseStatement.fromXML(e) get
            case e: Elem if (isXCLOutDiscourseStatement(e)) =>
              XCLParsableOutDiscourseStatement.fromXML(e) get
          })
    }

    def isXCLDiscourseStatement(x: Node): Boolean =
      isXCLInDiscourseStatement(x) ||
        isXCLOutDiscourseStatement(x)

    def isXCLStatement(x: Node): Boolean =
      isXCLTitling(x) ||
        isXCLDiscourseStatement(x) ||
        isXCLSchema(x)

    implicit object XCLParsableStatement
        extends XCLParsable[Statement] {
      def fromXML(x: Elem): Try[Statement] =
        Try(
          x match {
            case e: Elem if (isXCLDiscourseStatement(e)) =>
              XCLParsableDiscourseStatement.fromXML(e) get
            case e: Elem if (isXCLTitling(e)) =>
              XCLParsableTitling.fromXML(e) get
            case e: Elem if (isXCLSchema(e)) =>
              XCLParsableSchema.fromXML(e) get
          })
    }

    implicit object XCLParsableSentenceOrSchema
        extends XCLParsable[SentenceOrSchema] {
      def fromXML(x: Elem): Try[SentenceOrSchema] =
        Try(
          x match {
            case e: Elem if (isXCLSentence(e)) =>
              XCLParsableSentence.fromXML(e) get
            case e: Elem if (isXCLSchema(e)) =>
              XCLParsableSchema.fromXML(e) get
          })
    }

    implicit object XCLParsableTextConstruction
        extends XCLParsable[TextConstruction] {
      def fromXML(x: Elem): Try[TextConstruction] = Try({
        val comments: CommentSet = commentSetParse(x)
        val texts: BasicExpressionSet = basicExpressionSetParse(x)
        TextConstruction(comments, texts)
      })
    }

    implicit object XCLParsableDomainRestriction
        extends XCLParsable[DomainRestriction] {
      def fromXML(x: Elem): Try[DomainRestriction] = Try({
        val comments: CommentSet = commentSetParse(x)
        val terms: List[Term] = termSeqParse(x)
        val domain: Term = (terms head)
        val texts: List[Text] = textSeqParse(x)
        val body: Text = (texts head)
        DomainRestriction(comments, domain, body)
      })
    }

    implicit object XCLParsableImportation
        extends XCLParsable[Importation] {
      def fromXML(x: Elem): Try[Importation] = Try({
        val comments: CommentSet = commentSetParse(x)
        val names: List[Name] = nameSeqParse(x)
        val title: Name = (names head)
        Importation(comments, title)
      })
    }

    def isXCLTextConstruction(x: Node): Boolean =
      isInXCLNamespace(x) && ((x label) equals "Construct")

    def isXCLDomainRestriction(x: Node): Boolean =
      isInXCLNamespace(x) && ((x label) equals "Restrict")

    def isXCLImportation(x: Node): Boolean =
      isInXCLNamespace(x) && ((x label) equals "Import")

    def isXCLText(x: Node): Boolean =
      isXCLTextConstruction(x) ||
        isXCLDomainRestriction(x) ||
        isXCLImportation(x)

    implicit object XCLParsableText
        extends XCLParsable[Text] {
      def fromXML(x: Elem): Try[Text] =
        Try(
          x match {
            case e: Elem if (isXCLTextConstruction(e)) =>
              XCLParsableTextConstruction.fromXML(e) get
            case e: Elem if (isXCLDomainRestriction(e)) =>
              XCLParsableDomainRestriction.fromXML(e) get
            case e: Elem if (isXCLImportation(e)) =>
              XCLParsableImportation.fromXML(e) get
          })
    }

    implicit object XCLParsableBasicExpression
        extends XCLParsable[BasicExpression] {
      def fromXML(x: Elem): Try[BasicExpression] =
        Try(
          x match {
            case e: Elem if (isXCLSentence(e)) =>
              XCLParsableSentence.fromXML(e) get
            case e: Elem if (isXCLStatement(e)) =>
              XCLParsableStatement.fromXML(e) get
            case e: Elem if (isXCLText(e)) =>
              XCLParsableText.fromXML(e) get
          })
    }

    val _tryCommentParse: PartialFunction[Node, Try[Comment]] =
      { case x: Elem => (XCLParsableComment fromXML x) }
    val _commentParse: PartialFunction[Node, Comment] =
      _tryCommentParse andThenPartial {
        case s: Success[Comment] => s get
      }

    def commentSetParse(x: Node): CommentSet = (x child) collect _commentParse toSet

    val _tryNameParse: PartialFunction[Node, Try[Name]] =
      { case x: Elem => (XCLParsableName fromXML x) }
    val _nameParse: PartialFunction[Node, Name] =
      _tryNameParse andThenPartial {
        case s: Success[Name] => s get
      }

    def nameSeqParse(x: Node): List[Name] = (x child) collect _nameParse toList

    val _tryTosmParse: PartialFunction[Node, Try[TermOrSequenceMarker]] =
      { case x: Elem => (XCLParsableTOSM fromXML x) }
    val _tosmParse: PartialFunction[Node, TermOrSequenceMarker] =
      _tryTosmParse andThenPartial {
        case s: Success[TermOrSequenceMarker] => s get
      }

    def tosmSeqParse(x: Node): TermSequence = (x child) collect _tosmParse toList

    def tosmSetParse(x: Node): TOSMSet = (x child) collect _tosmParse toSet

    val _tryTermParse: PartialFunction[Node, Try[Term]] =
      { case x: Elem => (XCLParsableTerm fromXML x) }
    val _termParse: PartialFunction[Node, Term] =
      _tryTermParse andThenPartial {
        case s: Success[Term] => s get
      }

    def termSeqParse(x: Node): List[Term] = (x child) collect _termParse toList

    def termSetParse(x: Node): TermSet = (x child) collect _termParse toSet

    val _trySentenceParse: PartialFunction[Node, Try[Sentence]] =
      { case x: Elem => (XCLParsableSentence fromXML x) }
    val _sentenceParse: PartialFunction[Node, Sentence] =
      _trySentenceParse andThenPartial {
        case s: Success[Sentence] => s get
      }

    def sentenceSeqParse(x: Node): List[Sentence] =
      (x child) collect _sentenceParse toList

    def sentenceSetParse(x: Node): SentenceSet =
      (x child) collect _sentenceParse toSet

    val _tryInterpretableNameParse: PartialFunction[Node, Try[InterpretableName]] =
      { case x: Elem => (XCLParsableInterpretableName fromXML x) }
    val _bindingParse: PartialFunction[Node, InterpretableName] =
      _tryInterpretableNameParse andThenPartial {
        case s: Success[InterpretableName] => s get
      }

    def bindingSetParse(x: Node): BindingSet = (x child) collect _bindingParse toSet

    val _trySequenceMarkerParse: PartialFunction[Node, Try[SequenceMarker]] =
      { case x: Elem => (XCLParsableSequenceMarker fromXML x) }
    val _seqBindingParse: PartialFunction[Node, SequenceMarker] =
      _trySequenceMarkerParse andThenPartial {
        case s: Success[SequenceMarker] => s get
      }

    def seqBindingSetParse(x: Node): SeqBindingSet = (x child) collect _seqBindingParse toSet

    val _trySentenceOrSchemaParse: PartialFunction[Node, Try[SentenceOrSchema]] =
      { case x: Elem => (XCLParsableSentenceOrSchema fromXML x) }
    val _sentenceOrSchemaParse: PartialFunction[Node, SentenceOrSchema] =
      _trySentenceOrSchemaParse andThenPartial {
        case s: Success[SentenceOrSchema] => s get
      }

    def sentenceOrSchemaSeqParse(x: Node): List[SentenceOrSchema] =
      (x child) collect _sentenceOrSchemaParse toList

    val _tryTextParse: PartialFunction[Node, Try[Text]] =
      { case x: Elem => (XCLParsableText fromXML x) }
    val _textParse: PartialFunction[Node, Text] = _tryTextParse andThenPartial {
      case s: Success[Text] => s get
    }
    def textSetParse(x: Node): Set[Text] = (x child) collect _textParse toSet

    def textSeqParse(x: Node): List[Text] = (x child) collect _textParse toList

    val _tryBasicExpressionParse: PartialFunction[Node, Try[BasicExpression]] =
      { case x: Elem => (XCLParsableBasicExpression fromXML x) }
    val _basicExpressionParse: PartialFunction[Node, BasicExpression] = _tryBasicExpressionParse andThenPartial {
      case s: Success[BasicExpression] => s get
    }

    def basicExpressionSetParse(x: Node): Set[BasicExpression] = (x child) collect _basicExpressionParse toSet
  }

  implicit class ComposePartial[A, B](pf: PartialFunction[A, B]) {
    def andThenPartial[C](that: PartialFunction[B, C]): PartialFunction[A, C] =
      Function.unlift(pf.lift(_) flatMap that.lift)
  }
}
