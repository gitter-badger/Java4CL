package api4kb;

import java.io.InputStream;

import org.w3c.dom.Element;

public final class API4KB {

	API4KB() {}
	
	public static EncodingSystem<Element, InputStream> EncodingSystemXMLUTF8 =
			new EncodingSystem<Element, InputStream>() {

				@Override
				public InputStream code(Element t) {
					//TODO
					return null;
				}

				@Override
				public Element decode(InputStream s) {
					//TODO
					return null;
				}
		
	};

}
