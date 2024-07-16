package ai.swarmsolver.backend.app.prompt;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class PromptTemplateTest extends TestCase {

    @Test
    public void test_compile_one_param_one_occurrence()  throws PromptException {
        // given a template
        PromptTemplate promptTemplate = new PromptTemplate("template with ${param1} param");

        // and a parameter map
        Map<String, String> params = Map.of("param1", "val1");

        // when the prompt is compiled
        String compiled = promptTemplate.compile(params);

        // then the params are replaced
        Assert.assertEquals("template with val1 param", compiled);

    }

    public void test_compile_one_param_multiple_occurrences()  throws PromptException  {
        // given a template
        PromptTemplate promptTemplate = new PromptTemplate("template with ${param1} ${param1} (2x)");

        // and a parameter map
        Map<String, String> params = Map.of("param1", "val1");

        // when the prompt is compiled
        String compiled = promptTemplate.compile(params);

        // then the params are replaced
        Assert.assertEquals("template with val1 val1 (2x)", compiled);

    }

    public void test_compile_missing_param() {


        // given a template
        PromptTemplate promptTemplate = new PromptTemplate("template with ${param1} and ${param2}");

        // and a parameter map
        Map<String, String> params = Map.of("param1", "val1");

       PromptException promptException = Assert.assertThrows(PromptException.class, () -> {

            // when the prompt is compiled
            String compiled = promptTemplate.compile(params);


        });

       // then I expect an exception
        Assert.assertNotNull(promptException);
        Assert.assertEquals("no value for placeholder 'param2'", promptException.getMessage());

    }


}