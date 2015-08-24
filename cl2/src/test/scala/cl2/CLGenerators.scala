package cl2

import org.scalacheck.Gen
import org.scalatest._
import org.scalatest.matchers._
import prop.PropertyChecks
import scala.collection.immutable.List
import org.scalacheck.Arbitrary

import collection.JavaConversions._
import java.util.Arrays;

import api4kbj.KnowledgeSourceLevel._
import cl2a._
import cl2array._

object CLGenerators {

  val clstringsymbolgen: Gen[String] = for (
    a <- Gen listOf (
      for {i <- Gen choose (9, Character MAX_CODE_POINT)}
        yield String copyValueOf (Character toChars i))
  ) yield (a mkString) replaceAll ("[\\p{Cc}&&[^\r\n\t]]", "")

  //CLComent
  val clstringcommentgen: Gen[CLStringComment] =
    for {s <- clstringsymbolgen}
      yield new CLStringComment(s)

  //TODO also need other kinds of comments
  val clcommentgen: Gen[CLComment] = clstringcommentgen

  val cllistcommentgen: Gen[List[CLComment]] =
      Gen listOf (clcommentgen)

 //CLCommentSequence
  val clcommentsequencegen: Gen[CLCommentSequence] =
    for {a <- cllistcommentgen}
      yield new CLCommentSequenceArray(a.toArray[CLComment]: _*)

  implicit val arbCLCommentSequence = Arbitrary(clcommentsequencegen)

  //CLInterpretableName
  val clstringinamegen: Gen[CLStringInterpretableName] = for {s <- clstringsymbolgen}
    yield new CLStringInterpretableName(s)

  //TODO also need interpretable names with non-string symbols
  val clinamegen: Gen[CLInterpretableName] = clstringinamegen

  val clliststringinamesymbolgen: Gen[List[String]] =
    Gen listOf (clstringsymbolgen)

  val clliststringinamegen: Gen[List[CLStringInterpretableName]] =
    Gen listOf (clstringinamegen)

  //CLInterpretedName
  val clstringdatagen: Gen[CLStringIriInterpretedName] = for {s <- clstringsymbolgen}
    yield CLStringIriInterpretedName.createCLStringIriInterpretedNameFromString(s)

  //TODO also need other interpreted names from other datatypes
  val cldatagen: Gen[CLInterpretedName] = clstringdatagen

  //CLName
  val clnamegen: Gen[CLName] = Gen.frequency(
    (1, clinamegen),
    (1, cldatagen))

  //CLSequenceMarker
  val clstringsequencemarkergen =
    for { s <- clstringsymbolgen }
      yield new CLStringSequenceMarker(s)

  //TODO also need other sequence markers
  val clsequencemarkergen: Gen[CLSequenceMarker] = clstringsequencemarkergen
      
 //CLFunctionalTerm
  val clfunctionalterm0gen: Gen[CLFunctionalTerm] =
    for {
      comments <- clcommentsequencegen
      operator <- clnamegen
      args <- cltermsequence0gen
    } yield new CLFunctionalTerm(comments, operator, args)

  val clfunctionalterm1gen: Gen[CLFunctionalTerm] =
    for {
      comments <- clcommentsequencegen
      operator <- clterm1gen
      args <- cltermsequence1gen
    } yield new CLFunctionalTerm(comments, operator, args)

    val clfunctionaltermgen: Gen[CLFunctionalTerm] =
    for {
      comments <- clcommentsequencegen
      operator <- cltermgen
      args <- cltermsequencegen
    } yield new CLFunctionalTerm(comments, operator, args)

 //CLTerm
  val clterm1gen: Gen[CLTerm] = Gen.frequency(
    (1, clnamegen),
    (1, clfunctionalterm0gen))

  val clterm2gen: Gen[CLTerm] = Gen.frequency(
    (1, clnamegen),
    (1, clfunctionalterm1gen))

  val cltermgen: Gen[CLTerm] = Gen.frequency(
    (100, clnamegen),
    (1, clfunctionaltermgen))

  implicit val arbCLTerm = Arbitrary(clterm2gen)
    
  val cllisttermgen: Gen[List[CLTerm]] =
    Gen listOf (cltermgen)

 //CLTermOrSequenceMarker
  val cltermorsequencemarker0gen: Gen[CLTermOrSequenceMarker] = Gen.frequency(
    (1, clnamegen),
    (1, clsequencemarkergen))

  val cltermorsequencemarker1gen: Gen[CLTermOrSequenceMarker] = Gen.frequency(
    (1, clterm1gen),
    (1, clsequencemarkergen))

  val cltermorsequencemarkergen: Gen[CLTermOrSequenceMarker] = Gen.frequency(
    (1, cltermgen),
    (1, clsequencemarkergen))

  val cllisttermorsequencemarkergen: Gen[List[CLTermOrSequenceMarker]] =
    Gen listOf (cltermorsequencemarkergen)

 //CLTermSequence
  val cltermsequence0gen: Gen[CLTermSequence] =
    for {a <- Gen listOf (cltermorsequencemarker0gen)}
      yield new CLTermSequenceArray(a.toArray[CLTermOrSequenceMarker]: _*)

  val cltermsequence1gen: Gen[CLTermSequence] =
    for {a <- Gen listOf (cltermorsequencemarker1gen)}
      yield new CLTermSequenceArray(a.toArray[CLTermOrSequenceMarker]: _*)

  val cltermsequencegen: Gen[CLTermSequence] =
    for {a <- cllisttermorsequencemarkergen}
      yield new CLTermSequenceArray(a.toArray[CLTermOrSequenceMarker]: _*)

      
  val clatomgen: Gen[CLAtomicSentence] =
    for {
      comments <- clcommentsequencegen
      operator <- clterm1gen
      args <- cltermsequence1gen
    } yield new CLAtomicSentence(comments, operator, args)

  implicit val arbCLAtomicSentence = Arbitrary(clatomgen)

  //TODO add more types of Sentence
  val clsentencegen: Gen[CLSentence] = clatomgen

  implicit val arbCLSentence = Arbitrary(clsentencegen)

  //TODO make a valid IRI generator by assembling from segments

  //TODO use the IRI generator to make a data generator with arbitrary IRI datatype

  //TODO make generators for other kinds of symbols - Image, objects as XML serializations (JAXB?)

}