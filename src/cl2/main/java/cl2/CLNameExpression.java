package cl2;

import api4kb.AbstractKRRDialectType;
import api4kb.AbstractKRRLanguage;
import api4kb.GraphImmutableEnvironment;
import api4kb.ImmutableEnvironment;
import api4kb.DialectTypeIncompatibleException;
import api4kb.KnowledgeAssetLI;
import api4kb.LanguageIncompatibleException;
import api4kb.Option;

public abstract class CLNameExpression extends CLExpression implements CLName {


	public CLNameExpression(KnowledgeAssetLI asset, AbstractKRRLanguage lang)
			throws IllegalArgumentException, LanguageIncompatibleException {
		super(asset, lang);
		// TODO Auto-generated constructor stub
	}

	public <T> CLNameExpression(CLManifestationG<T> manifestation) {
		super(manifestation);
		// TODO Auto-generated constructor stub
	}

	public <T> CLNameExpression(AbstractKRRLanguage lang) {
		super(lang);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Option<CLComment> comment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CLPrefixExpression[] prefixes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String symbol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> CLManifestationG<T> manifest(AbstractKRRDialectType<T> dialect)
			throws DialectTypeIncompatibleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearManifest(AbstractKRRDialectType<?> dialect) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public KnowledgeAssetLI conceptualize(GraphImmutableEnvironment e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearConceptualize(ImmutableEnvironment environment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AbstractKRRLanguage getLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearManifest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearAsset() {
		// TODO Auto-generated method stub
		
	}

}
