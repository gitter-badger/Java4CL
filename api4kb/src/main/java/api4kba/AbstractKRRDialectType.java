package api4kba;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import api4kbj.KRRDialect;
import api4kbj.KRRDialectType;

public class AbstractKRRDialectType<T> implements KRRDialectType<T> {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());

	public AbstractKRRDialectType(final String name, final KRRDialect dialect,
			Class<? extends T> clazz) {
		this.name = name;
		this.dialect = dialect;
		this.clazz = clazz;
	}

	private final String name;
	private final KRRDialect dialect;
	private Class<? extends T> clazz;

	@Override
	public String name() {
		return name;
	}

	@Override
	public KRRDialect dialect() {
		return dialect;
	}

	@Override
	public Class<? extends T> asClass() {
		return clazz;
	}

	@Override
	public String toString() {
		return dialect().toString() + "." + asClass().getName() + "." + name();
	}

}
