package krconfigured;

import api4kbj.Decomposable;
import api4kbj.KRRDialect;
import api4kbj.KnowledgeManifestation;

public interface StructuredKnowledgeManifestationConfigured extends
		KnowledgeManifestation, StructuredKnowledgeResourceConfigured,
		Decomposable<KnowledgeManifestation> {

	// getter for dialects
	Iterable<KRRDialect> dialects();

}