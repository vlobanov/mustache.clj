package me.vlobanov.mustache;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import clojure.lang.Keyword;

public class Mustache {

    public static final String BEGIN = "{{";
    public static final String END = "}}";

    String fileName;
    List<Token> tokens;

    public static final ConcurrentMap<String, Mustache> CACHE = new ConcurrentHashMap<String, Mustache>();

    private List<Token> nestedToken(List<Token> input) throws ParserException {
        List<Token> output = new ArrayList<Token>();
        Deque<Token> sections = new LinkedList<Token>(); // stack
        Token section;
        List<Token> collector = output;

        for (Token token : input) {
            switch (token.type) {
            case Token.INVERTED:
            case Token.TRUE:
            case Token.SECTION:
                token.tokens = new ArrayList<Token>();
                sections.add(token);
                collector.add(token);
                collector = token.tokens;
                break;
            case '/':
                if (sections.isEmpty()) {
                    throw new ParserException("Unopened section: " + token.getDescription(), token);
                }
                section = sections.removeLast();
                if (!section.value.equals(token.value)) {
                    throw new ParserException("Unclosed: " + token.getDescription()
                                                           + "; in section"
                                                           + section.value, token);
                }

                if (sections.size() > 0) {
                    collector = sections.peekLast().tokens;
                } else {
                    collector = output;
                }
                break;
            default:
                collector.add(token);
                break;
            }
        }
        if (sections.size() > 0) {
            Token lastSection = sections.peek();
            throw new ParserException("Unclosed section: " + lastSection.getDescription(), lastSection);
        }
        return output;
    }

    public static Mustache preprocess(String template) throws ParserException {
        return preprocess(template, "<unknown>");
    }

    public static Mustache preprocess(String template, String fileName) throws ParserException {
        Mustache m = CACHE.get(template);
        if (m == null) {
            m = new Mustache(template, fileName);
            CACHE.put(template, m);
        }
        return m;
    }

    private Mustache(String template, String fileName) throws ParserException {
        List<Token> tokens = new LinkedList<Token>();
        Scanner scanner = new Scanner(template);
        this.fileName = fileName;
        while (!scanner.eos()) {
            String value = scanner.scanUtil(BEGIN);
            if (value != null)
                tokens.add(new Token(Token.TEXT, value));
            char type = scanner.nextType();
            scanner.skipeWhiteSpace();
            switch (type) {
            case '{': // not escape
                value = scanner.scanUtil("}}}");
                tokens.add(new Token(type, value, scanner.getCurrentLine(), fileName));
                break;
            default:
                value = scanner.scanUtil(END);
                tokens.add(new Token(type, value, scanner.getCurrentLine(), fileName));
            }

        }
        this.tokens = nestedToken(tokens);
    }

    public String render(Context ctx) {
        try {
            return Token.renderTokens(tokens, ctx, null);
        } catch (ParserException e) {
            return ""; // can not happen
        }
    }

    public String render(Context ctx, Map<Keyword, String> partials) throws ParserException {
        return Token.renderTokens(tokens, ctx, partials);
    }
}
