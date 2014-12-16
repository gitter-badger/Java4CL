package elevation;

import api4kb.AbstractKnowledgeAsset;
import api4kb.AbstractKnowledgeEncoding;
import api4kb.AbstractKnowledgeExpression;
import api4kb.AbstractKnowledgeItem;
import api4kb.AbstractKnowledgeManifestationG;
import api4kb.EnvironmentIncompatibleException;
import api4kb.ImmutableEnvironment;
import api4kb.KnowledgeResource;

public interface Lifter {

	public AbstractKnowledgeAsset conceptualize(KnowledgeResource kr,
			ImmutableEnvironment e) throws EnvironmentIncompatibleException;

	public <T> AbstractKnowledgeAsset conceptualize(
			AbstractKnowledgeManifestationG<T> kr, ImmutableEnvironment e)
			throws EnvironmentIncompatibleException;

	public <T, S> AbstractKnowledgeAsset conceptualize(
			AbstractKnowledgeEncoding<T, S> kr, ImmutableEnvironment e)
			throws EnvironmentIncompatibleException;

	public <T, S, R> AbstractKnowledgeAsset conceptualize(
			AbstractKnowledgeItem<T, S, R> kr, ImmutableEnvironment e)
			throws EnvironmentIncompatibleException;

	public <T> AbstractKnowledgeExpression parse(
			AbstractKnowledgeManifestationG<T> kr);

	public <T, S> AbstractKnowledgeExpression parse(
			AbstractKnowledgeEncoding<T, S> kr);

	public <T, S, R> AbstractKnowledgeExpression parse(
			AbstractKnowledgeItem<T, S, R> kr);

	public <T, S> AbstractKnowledgeManifestationG<T> decode(
			AbstractKnowledgeEncoding<T, S> kr);

	public <T, S, R> AbstractKnowledgeManifestationG<T> decode(
			AbstractKnowledgeItem<T, S, R> kr);

	public <T, S, R> AbstractKnowledgeEncoding<T, S> prototype(
			AbstractKnowledgeItem<T, S, R> kr);

}