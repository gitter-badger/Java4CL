package cl2;

import cl2i.CLImmutable;
import api4kba.AbstractBasicKnowledgeEncoding;

public class CLEncoding extends AbstractBasicKnowledgeEncoding implements CLImmutable {

	public <T> CLEncoding(T value, CLFormatType<T> formatType) {
		super(value, formatType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] toByteArray() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public CLEncoding copy() {
		// TODO Auto-generated method stub
		return null;
	}

}
