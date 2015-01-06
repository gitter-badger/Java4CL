package krconfigured;

import api4kbj.KRRLanguage;
import api4kbj.KnowledgeExpression;

/**
 * A BasicKnowledgeExpressionConfigured is a knowledge resource at the expression
 * abstraction level that is not structured. As a consequence, it has a unique
 * language.
 * 
 * @author taraathan
 *
 */
public interface BasicKnowledgeExpressionConfigured extends KnowledgeExpression,
		BasicKnowledgeResourceConfigured {

	/**
	 * Returns the unique language of the basic knowledge expression.
	 * 
	 * @return the language of the basic knowledge expression.
	 */
	KRRLanguage language();

}