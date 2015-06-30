package cl2a;



public abstract class CLTermSequence extends CLExpressionLike {

	public CLTermSequence() {
		super();
	}

	public abstract Iterable<CLTermOrSequenceMarker> args();

	public abstract int length();

	public abstract CLTermSequence concat(CLTermSequence inargs);

}