// Copyright (C) 2015 Athan Services.
// See the LICENCE.txt file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package cl2

import org.scalatest._
import scala.language.postfixOps

@Ignore
class CLExpressionLawTest extends FunSuiteLike with Discipline {
  implicit override val generatorDrivenConfig =
    PropertyCheckConfig(
      minSuccessful = MIN_SUCCESSFUL,
      maxDiscarded = MAX_DISCARDED,
      minSize = MIN_SIZE,
      maxSize = MAX_SIZE,
      workers = WORKERS)

  checkAll("CLExpression", CLExpressionLaws.expression)

  checkAll("CLSentence", CLSentenceLaws.sentence)

  checkAll("CLAtomicSentence", CLAtomicSentenceLaws.atom)

  checkAll("CLBooleanSentence", CLBooleanSentenceLaws.bool)

  checkAll("CLBiconditional", CLBiconditionalLaws.bicond)

  checkAll("CLConjunction", CLConjunctionLaws.and)

  checkAll("CLDisjunction", CLDisjunctionLaws.or)

  checkAll("CLQuantifiedSentence", CLQuantifiedSentenceLaws.quant)

  checkAll("CLExistential", CLExistentialLaws.exists)
}