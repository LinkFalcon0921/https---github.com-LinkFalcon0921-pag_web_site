package PagesDesign;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BF_Translator {

  public static String translateToC(String input) {
    SetterStrings it = new SetterStrings();
    it.set(input);
    return it.get();
  }

  public static void main(String[] args) {
    // String[][] tests = { new String[] { "++++", "*p += 4;\n" },
    //     new String[] { "----", "*p -= 4;\n" },
    //     new String[] { ">>>>", "p += 4;\n" },
    //     new String[] { "<<<<", "p -= 4;\n" },
    //     new String[] { ".", "putchar(*p);\n" },
    //     new String[] { ",", "*p = getchar();\n" },
    //     new String[] { "[[[]]", "Error!" },
    //     new String[] { "[][]", "" },
    //     new String[] { "[.]", "if (*p) do {\n  putchar(*p);\n} while (*p);\n" } };

    // for (String[] t : tests) {
    //   System.out.println(t[0]);
    //   System.out.println(t[1].equals(translateToC(t[0])));
    // }
      String trans = translateToC("+++++[>++++.<-]");
    System.out.println(trans.equals("*p += 5;\n    if (*p) do {\n      p += 1;\n      *p += 4;\n      putchar(*p);\n      p -= 1;\n      *p -= 1;\n    }while(*p);"));
}}

class SetterStrings {
  StringBuilder data = null;
  boolean edit;

  public SetterStrings() {
    data = new StringBuilder();
  }

  public void set(String info) {
    if (data.length() < 1) {
      data.append(info);
      edit = false;
    }

  }

  public String get() {
    if (!edit) {
      Matcher mat = null;
      Pattern pat = null;

      setRule1(mat, pat);

      if (setRule2(mat, pat))
        return "Error!";

      setRule3(mat, pat);
      setRule4(mat, pat);

      CruleA(mat, pat);
      CruleB(mat, pat);
      CruleC(mat, pat);
      CruleD(mat, pat);
      CruleE(mat, pat);
    }

    edit = true;
    return data.toString();
  }

  private void setRule1(Matcher matc, Pattern patt) {
    patt = Pattern.compile("(\\+-)| (-\\+)");
    matc = patt.matcher(data.toString());

    if (matc.find()) {
      data.replace(0, data.length(), data.toString().replaceAll(patt.pattern(), ""));
    }
  }

  private void setRule3(Matcher matc, Pattern patt) {
    patt = Pattern.compile("<>");
    matc = patt.matcher(data.toString());

    if (matc.find()) {
      data.replace(0, data.length(), data.toString().replaceAll(patt.pattern(), ""));
    }
  }

  private boolean setRule2(Matcher matc, Pattern patt) {
    patt = Pattern.compile("[\\[][\\[\\]][\\]]");
    matc = patt.matcher(data.toString());

    return matc.find();
  }

  private void CruleA(Matcher matc, Pattern patt) {
    patt = Pattern.compile("(\\++)|(\\-+)");
    matc = patt.matcher(data.toString());
    String dataInfo = data.toString();

    while (matc.find()) {
      String info = matc.group();
      dataInfo = dataInfo.replace(info, String.format("*p %s= %d;\n", String.valueOf(info.charAt(0)), info.length()));
    }

    data.replace(0, data.length(), dataInfo);
  }

  // MATCHES COUNTS OF <>
  private void CruleB(Matcher matc, Pattern patt) {
    patt = Pattern.compile("(\\<+)|(\\>+)");
    matc = patt.matcher(data.toString());
    String dataInfo = data.toString();

    while (matc.find()) {
      String info = matc.group();

      dataInfo = dataInfo.replaceFirst(info,
          String.format("p %s= %d;\n",
              (info.contains(">") ? "+" : "-"),
              info.length()));
    }

    data.replace(0, data.length(), dataInfo);
  }

  // MATCHES POINTS
  private void CruleC(Matcher matc, Pattern patt) {
    patt = Pattern.compile("\\.");
    matc = patt.matcher(data.toString());

    if (matc.find()) {
      data.replace(0, data.length(), data.toString().replaceAll(patt.pattern(), "putchar(\\*p);\n"));
    }
  }

  // MATCHES COMMA
  private void CruleD(Matcher matc, Pattern patt) {
    patt = Pattern.compile("\\,");
    matc = patt.matcher(data.toString());

    if (matc.find()) {
      data.replace(0, data.length(), data.toString().replaceAll(patt.pattern(), "\\*p = getchar();\n"));
    }
  }

  // MATCHES [] EMPTY
  /** this matches a value of square brackets empty */
  private void setRule4(Matcher matc, Pattern patt) {
    patt = Pattern.compile("[\\[][\\]]");
    matc = patt.matcher(data.toString());

    if (matc.find()) {
      data.replace(0, data.length(), data.toString().replaceAll(patt.pattern(), ""));
    }
  }

  private void CruleE(Matcher matc, Pattern patt) {
    // OPEN [
    patt = Pattern.compile("\\[");
    matc = patt.matcher(data.toString());

    if (matc.find()) {
      data.replace(0, data.length(), data.toString().replaceAll(patt.pattern(), "if (\\*p) do {\n"));
    }
    // CLOSED ]
    patt = Pattern.compile("\\]");
    matc = patt.matcher(data.toString());

    if (matc.find()) {
      data.replace(0, data.length(), data.toString().replaceAll(patt.pattern(), "} while (\\*p);\n"));
    }

    indent();
  }

  // Indentar el codigo
  private void indent() {
    // String info = data.toString();
    // data.replace(0, data.length(), info.replaceAll("(?m)^", "\t"));

    if (!data.toString().contains("{"))
      return;

    String[] builder = data.toString().split("\n");
    int countSpaces = 0;

    for (int t = 0; t < builder.length - 1; t++) {
      String actual = builder[t];

      if (actual.contains("{")) {
        countSpaces += 2;
        continue;
      }

      actual = String.format("%s", "\u0020".repeat(countSpaces)) + actual;
      builder[t] = actual;

      if (builder[t + 1].contains("}")) {
        countSpaces -= 2;
      }
    }

    data.delete(0, data.length());
    for (String info : builder) {
      data.append(info).append("\n");
    }
  }
}
