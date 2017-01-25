package z.medicaltests;


abstract class TestStructure {
    private String s[];
    private boolean f[];
    private int Type;
    private boolean R[];

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
    public String[] getParents(){return s;}
    public String[] getChildren(){return s;}
    public boolean[] getRelations(){return R;}
    public TestStructure getThis(){return this;}
    public int getID() {
        return 0;
    }
}

class TestCheckBox extends TestStructure {
    private String Text;
    private int Type;
    private String Options[];
    private boolean Flags[];
    private int ID;

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
    public TestCheckBox getThis(){return this;}
    public int getID() {
        return ID;
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
    void setID(int ID) {this.ID = ID;
    }
}

class MultipleChoices extends TestStructure{
    private String Text;
    private int Type;
    private String Parents[];
    private String Children[];
    private boolean Relations[];
    private int ID;

    public String getText() {return Text;}
    public int getType(){return Type;}
    public String[] getParents(){return Parents;}
    public String[] getChildren(){return Children;}
    public boolean[] getRelations(){return Relations;}
    public MultipleChoices getThis(){return this;}
    public int getID() {
        return ID;
    }

    public void setText(String Text) {this.Text=Text;}
    void setType(int Type){this.Type=Type;}
    void setParents(String Parents[]){this.Parents=Parents;}
    void setChildren(String Children[]){this.Children=Children;}
    void setRelations(boolean Relations[]){this.Relations=Relations;}
    void setID(int ID) {this.ID  = ID;}
}
