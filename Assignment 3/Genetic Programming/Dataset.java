import java.util.HashMap;
import java.util.Map;

public class Dataset {
    Map<String, String> attr = new HashMap<>();

    public Dataset(String Class, String age, String menopause, String tumorSize, String invNodes, String nodeCapes,
            String degMalig, String breast, String breastQuad, String irradiat) {
        attr.put("Class", Class);
        if (age.equals("?") == false) {
            attr.put("age", age);

        } else {
            attr.put("age", "40-49");
        }

        if (menopause.equals("?") == false)
            attr.put("menopause", menopause);
        else
            attr.put("menopause", "premeno");
        if (tumorSize.equals("?") == false)
            attr.put("tumor-size", tumorSize);
        else
            attr.put("tumor-size", "15-19");
        if (invNodes.equals("?") == false)
            attr.put("inv-nodes", invNodes);
        else
            attr.put("inv-nodes", "0-2");
        if (nodeCapes.equals("?") == false)
            attr.put("node-caps", nodeCapes);
        else
            attr.put("node-caps", "no");
        if (degMalig.equals("?") == false)
            attr.put("deg-malig", degMalig);
        else
            attr.put("deg-malig", "2");
        if (breast.equals("?") == false)
            attr.put("breast", breast);
        else
            attr.put("breast", "left");
        if (breastQuad.equals("?") == false)
            attr.put("breast-quad", breastQuad);
        else
            attr.put("breast-quad", "left-up");
        if (irradiat.equals("?") == false)
            attr.put("irradiat", irradiat);
        else
            attr.put("irradiat", "no");
    }

    public int getOutput() {
        if (attr.get("Class").equals("no-recurrence-events"))
            return 1;
        else
            return 0;
    }
}
