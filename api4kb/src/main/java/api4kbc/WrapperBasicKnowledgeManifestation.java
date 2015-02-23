package api4kbc;

import api4kbj.BasicKnowledgeManifestation;
import api4kbj.DialectTypeEnvironment;
import api4kbj.KRRDialect;
import api4kbj.KRRDialectType;

public class WrapperBasicKnowledgeManifestation implements
		BasicKnowledgeManifestation {

	// Wrapper-based constructor
	public WrapperBasicKnowledgeManifestation(KRRDialect dialect,
			KRRDialectType<?> wrappedDialectType, Object wrappedValue,
			DialectTypeEnvironment environment) {
		super();
		this.dialect = dialect;
		this.wrappedDialectType = wrappedDialectType;
		this.wrappedValue = wrappedValue;
		this.environment = environment;
	}

	private final KRRDialect dialect;
	private final KRRDialectType<?> wrappedDialectType;
	protected final Object wrappedValue;
	private final DialectTypeEnvironment environment;

	@Override
	public KRRDialect dialect() {
		return dialect;
	}

	@Override
	public DialectTypeEnvironment environment() {
		return environment;
	}

	@Override
	public <T> T build(KRRDialectType<T> dialectType) {
		if (dialectType.equals(wrappedDialectType)) {
			return (T) wrappedValue;
		}
		if (environment.containsMember(dialectType)) {
			return (T) environment.apply(wrappedValue, dialectType);
		}
		throw new IllegalArgumentException("Requested dialect type"
				+ dialectType + " is not supporte");
	}

}