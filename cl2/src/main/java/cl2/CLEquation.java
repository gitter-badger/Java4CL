package cl2;

import cl2a.CLCommentSet;
import cl2a.CLSimpleSentence;
import cl2a.CLTerm;


/**
 * @author ralph
 *
 */
public class CLEquation extends CLSimpleSentence {

	private final CLTerm left;
	private final CLTerm right;

	public CLEquation(CLCommentSet comments,
			CLTerm left, CLTerm right) {
		super(comments);
		this.left = left;
		this.right = right;
	}
	
	public CLTerm left() {
		return left;
	}

	public CLTerm right() {
		return right;
	}
	
	@Override
	public CLEquation insertComments(CLCommentSet incomments) {
		return new CLEquation(comments().concat(incomments), 
				left(), right());
	}

	@Override
	public CLEquation copy() {
		return new CLEquation(comments().copy(), left().copy(), right().copy());
	}

	/**
     * Returns the XCL2 sour syntax for the equation sentence, as a string,
     * using the prefix cl: to indicate the XCL2 namespace.
     */
	@Override
	public String toString() {
		return "<cl:Equal>" + 
	            comments().toString() +
	            left().toString() +
	            right().toString() + "</cl:Equal>";
	}

	
}
