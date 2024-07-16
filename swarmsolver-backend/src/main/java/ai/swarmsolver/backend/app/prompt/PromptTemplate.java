package ai.swarmsolver.backend.app.prompt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PromptTemplate {

    private String template;

    public PromptTemplate(String template) {
        this.template = template;
        initPlaceHolders();
    }


    private List<String> placeHolders;

    private void initPlaceHolders() {
        placeHolders = extractUniquePlaceholders(template);
    }


    public String compile(Map<String, String> params) throws PromptException {

        for (String placeHolder : placeHolders) {
            if (!params.containsKey(placeHolder))
                throw new PromptException(String.format("no value for placeholder '%s'", placeHolder));
        }

        String compiled = template;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            compiled = compiled.replaceAll(String.format("\\$\\{%s\\}", key), value);
        }

        return compiled;

    }


    private static List<String> extractUniquePlaceholders(String input) {
        List<String> uniquePlaceholders = new ArrayList<>();

        // Define the regular expression for the placeholder
        String regex = "\\$\\{.*?\\}";

        // Create a Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Create a Matcher object
        Matcher matcher = pattern.matcher(input);

        // Find and add unique placeholders to the list
        while (matcher.find()) {
            String placeholder = matcher.group();
            if (!uniquePlaceholders.contains(placeholder)) {
                uniquePlaceholders.add(placeholder);
            }
        }

        return uniquePlaceholders.stream()
                .map(PromptTemplate::removePlaceholderBraces)
                .collect(Collectors.toList());
    }

    private static String removePlaceholderBraces(String placeholder) {
           return placeholder.substring(2, placeholder.length() - 1);
     }

}
