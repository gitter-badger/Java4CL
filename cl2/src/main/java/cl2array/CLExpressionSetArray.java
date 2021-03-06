/**
 * 
 */
package cl2array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import cl2a.CLExpressionSet;
import cl2a.CLExpression;

public class CLExpressionSetArray extends CLExpressionSet {

	private final CLExpression[] args;
	
	public CLExpressionSetArray(CLExpression... args) {
		super(args);
		this.args = args;
	}

	public <T extends CLExpression> CLExpressionSetArray(Set<T> args) {
		this(args.toArray(new CLExpression[args.size()]));
	}

	@Override
	public Set<? extends CLExpression> args() {
		return new HashSet<CLExpression>(Arrays.asList(args));
	}

	@Override
	public int length(){
		return args.length;
	}

	@Override
	public CLExpressionSetArray concat(CLExpressionSet inargs) {
		int bLen = inargs.length();
		CLExpression[] b= new CLExpression[bLen];
		int i = 0;
        for (final CLExpression inarg : inargs.args())
        {
            b[i++] = inarg;
        }		
        CLExpression[] c = CLArray.concatExpressions(args, b);
		return new CLExpressionSetArray(c);
	}

	@Override
	public CLExpressionSetArray copy() {
		return new CLExpressionSetArray(CLArray.copyExpressions(args));
	}
}
