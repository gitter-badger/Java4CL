package cl2;

import api4kb.AbstractKnowledgeEncoding;
import api4kb.AbstractCodecSystem;
import api4kb.AbstractKnowledgeItem;

public class CLEncoding<T, S> extends AbstractKnowledgeEncoding<T, S> implements CLKnowledgeResource {

   public CLEncoding(S stream, CLDialectType<T> dialect, AbstractCodecSystem<T, S> system) {
	   super(stream, dialect, system);
	}

	public CLEncoding(CLManifestation<T> manifestation, AbstractCodecSystem<T, S> system) {
	 super(manifestation, system);
}

	// static factory methods
   public static <T, S> CLEncoding<T, S> lazyNewInstance(
			CLManifestation<T> manifestation, AbstractCodecSystem<T, S> system)
			 {
		return new CLEncoding<T, S>(manifestation, system);
	}

  

	@Override
	public <R> AbstractKnowledgeItem<T, S, R> reproduce(R destination) {
		// TODO implement eager lowering to item
		// Case 1. from encoding
		// Case 2. from manifestation
		// Case 3. from expression
		return null;
	}


	@Override
	public CLManifestation<T> decode() {
		return (CLManifestation<T>) super.decode();
	}


}
