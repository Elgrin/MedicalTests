package z.medicaltests;


/**
 * Created by Жаров on 20.09.2016.
 */
abstract class TestStructure {
    private String s[];
    private boolean f[];
    private int Type;

    public String getText() {
        return "";
    }

    public int getType() {
        return Type;
    }

    public String[] getOptions() {
        return s;
    }

    public boolean[] getFlags() {
        return f;
    }
}

class TestCheckBox extends TestStructure {
    private String Text;
    private int Type;
    private String Options[];
    private boolean Flags[];

    public String getText() {
        return Text;
    }

    public int getType() {
        return Type;
    }

    public String[] getOptions() {
        return Options;
    }

    public boolean[] getFlags() {
        return Flags;
    }

    public void setText(String Text) {
        this.Text = Text;
    }

    void setType(int Type) {
        this.Type = Type;
    }

    void setOptions(String Options[]) {
        this.Options = Options;
    }

    public void setFlags(boolean Flags[]) {
        this.Flags = Flags;
    }

}
