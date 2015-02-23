import static org.junit.Assert.*;

import org.junit.Test;

import cl2.CL;
import api4kba.AbstractBasicKnowledgeExpression;
import api4kbc.API4KB;
import api4kbc.BasicKnowledgeAssetCanonical;
import api4kbj.BasicKnowledgeExpression;
import api4kbj.KnowledgeExpression;

public class BasicKnowledgeAssetTest {

	@Test
	public void assetShouldConceptualizeItsCanonicalExpression() {
		BasicKnowledgeExpression expression = new AbstractBasicKnowledgeExpression(
				CL.LANG) {
		};
		BasicKnowledgeAssetCanonical asset = new BasicKnowledgeAssetCanonical(
				CL.CL_DEFAULT_ENVIRONMENT, expression);
		assertTrue(API4KB.conceptualizes(asset, expression));
	}

}