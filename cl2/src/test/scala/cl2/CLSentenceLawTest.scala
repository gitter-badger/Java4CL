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

import org.scalatest.FunSuite

import org.scalatest._
import org.scalatest.FunSuite
import org.scalatest.matchers._

import org.typelevel.discipline.scalatest._

import collection.JavaConversions._

import api4kbj.KnowledgeSourceLevel._

import cl2a._

import cl2array._
import scala.language.postfixOps

class CLSentenceLawTest extends FunSuiteLike with Discipline {
  
    checkAll("CLAtomicSentence", CLAtomicSentenceLaws.atom)

    checkAll("CLBiconditional", CLBiconditionalLaws.bicond)

    checkAll("CLSentence", CLSentenceLaws.sentence)

}
