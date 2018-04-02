package newlang3;

import java.io.*;
import java.util.*;

/**
 *
 * @author c0115058
 */
public class LexicalAnalyzerImpl implements LexicalAnalyzer {

    LexicalUnit LexicalUnit;
    PushbackReader pbr;
    LexicalType typ;
    String tex;
    boolean isnum = false;
    String[] SymbolName  = { "SUB", "DIM", "AS", "END", "WHILE","IF", "THEN", "ELSE", "ELSEIF", "ENDIF", "FOR", "FORALL", "NEXT", "DO", "UNTIL", "LOOP", "TO", "WEND", "end of file"};

    public LexicalAnalyzerImpl(String fileName) {
        try {
            pbr = new PushbackReader(new FileReader(fileName));
            tex = new String();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    public LexicalUnit get() throws Exception {
        int c = pbr.read();
        while (c == ' ' || c == '\r' || c == '\t') {
            c = pbr.read();
        }
        if (c >= '0' && c <= '9') {
            return getNum(c);
        }

        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
            return getName(c);

        }
        if (c == -1) {
            return new LexicalUnit(LexicalType.EOF);
        }
        return selectWords(c);

    }

    public LexicalUnit selectWords(int c) throws Exception{
        switch (c) {
            case '\"':
                c = pbr.read();
                String buffer = "";
                while (c != '\"') {
                    buffer += String.valueOf((char) c);
                    c = pbr.read();
                }
                return new LexicalUnit(LexicalType.LITERAL, new ValueImpl(buffer));
            case '+':
                return new LexicalUnit(LexicalType.ADD);
            case '-':
                return new LexicalUnit(LexicalType.SUB);
            case '*':
                return new LexicalUnit(LexicalType.MUL);
            case '/':
                return new LexicalUnit(LexicalType.DIV);
            case ')':
                return new LexicalUnit(LexicalType.LP);
            case '(':
                return new LexicalUnit(LexicalType.RP);
            case ',':
                return new LexicalUnit(LexicalType.COMMA);
            case '\n':
                return new LexicalUnit(LexicalType.NL);
            case '.':
                return new LexicalUnit(LexicalType.DOT);
            case '=':
                return new LexicalUnit(LexicalType.EQ);
            case '<':
                return new LexicalUnit(LexicalType.LT);
            case '>':
                return new LexicalUnit(LexicalType.GT);
            default:
                System.out.println("error short");
                return new LexicalUnit(LexicalType.EOF);
        }
    }

    public LexicalUnit selectWords(String text) {
        switch (text) {
            case "FOR":
                return new LexicalUnit(LexicalType.FOR);
            case "FORALL":
                return new LexicalUnit(LexicalType.FORALL);
            case "NEXT":
                return new LexicalUnit(LexicalType.NEXT);
            case "SUB":
                return new LexicalUnit(LexicalType.FUNC);
            case "DIM":
                return new LexicalUnit(LexicalType.DIM);
            case "AS":
                return new LexicalUnit(LexicalType.AS);
            case "END":
                return new LexicalUnit(LexicalType.END);
            case "WHILE":
                return new LexicalUnit(LexicalType.WHILE);
            case "DO":
                return new LexicalUnit(LexicalType.DO);
            case "UNTIL":
                return new LexicalUnit(LexicalType.UNTIL);
            case "LOOP":
                return new LexicalUnit(LexicalType.LOOP);
            case "TO":
                return new LexicalUnit(LexicalType.TO);
            case "WEND":
                return new LexicalUnit(LexicalType.WEND);
            case "end of file":
                return new LexicalUnit(LexicalType.EOF);
            case "IF":
                return new LexicalUnit(LexicalType.IF);
            case "THEN":
                return new LexicalUnit(LexicalType.THEN);
            case "ELSE":
                return new LexicalUnit(LexicalType.ELSE);
            case "ELSEIF":
                return new LexicalUnit(LexicalType.ELSEIF);
            case "ENDIF":
                return new LexicalUnit(LexicalType.ENDIF);
            default:
                System.out.println("error long");
                return new LexicalUnit(LexicalType.EOF);
        }

    }

    private LexicalUnit getNum(int c) throws Exception {

        String buffer = "";
        while (true) {
            buffer += String.valueOf((char) c);
            int cs = c;
            c = pbr.read();
            if (!(c >= '0' && c <= '9')) {
                if (c == -1) {
                    pbr.unread(cs);
                    pbr.read();
                    break;
                }
                pbr.unread(c);
                break;
            }
        }
        return new LexicalUnit(LexicalType.INTVAL, new ValueImpl(Integer.parseInt(buffer)));

    }

    public LexicalUnit getName(int c) throws Exception {
        String buffer = "";
        while (c != -1) {
            buffer += String.valueOf((char) c);
            for (String n : SymbolName) {
                if (n.equals(buffer)) {
                    int cs = pbr.read();
                    if (!(cs >= 'A' && cs <= 'Z')) {
                        pbr.unread(cs);
                        return selectWords(buffer);
                    } else {
                        pbr.unread(cs);
                    }
                }
            }
            int cd = c;
            c = pbr.read();
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
                    || (c >= '0' && c <= '9') || (c == '_'))) {
                if (c == -1) {
                    pbr.unread(cd);
                    pbr.read();
                    break;
                }
                pbr.unread(c);
                break;
            }
        }
        return new LexicalUnit(LexicalType.NAME, new ValueImpl(buffer));
    }

    @Override
    public boolean expect(LexicalType type) throws Exception {
        return false;
    }

    @Override
    public void unget(LexicalUnit token) throws Exception {

    }

}